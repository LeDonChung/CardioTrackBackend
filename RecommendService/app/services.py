import requests
import pandas as pd
from flask import jsonify
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.neighbors import NearestNeighbors
import redis
import pickle
import numpy as np
from apscheduler.schedulers.background import BackgroundScheduler
from mlxtend.frequent_patterns import fpgrowth, association_rules

from app.config import REDIS_HOST, REDIS_PORT

redis_client = redis.StrictRedis(host=REDIS_HOST, port=REDIS_PORT, db=0)


# Hàm để lên lịch gọi train_model mỗi 24 giờ
def schedule_training():
    scheduler = BackgroundScheduler()
    scheduler.add_job(train_model, 'interval', hours=24)
    scheduler.add_job(train_model_order, 'interval', hours=24)
    scheduler.start()


def fetch_products():
    response = requests.get("http://api-gateway:8888/api/v1/medicine" + "/get-all")

    return response.json() if response.status_code == 200 else []


# Biến toàn cục cho mô hình KNN và TF-IDF
knn_model = None
tfidf_vectorizer = None


def combine_text(row):
    tags = " ".join([tag['title'] for tag in row['tags']])
    categories = " ".join([category['title'] for category in row['categories']])
    brand = row['brand']['title']
    return f"{row['des']} {tags} {categories} {brand}"


def train_model():
    global knn_model, tfidf_vectorizer

    # Lấy dữ liệu sản phẩm từ API
    products = fetch_products()["data"]
    if not products:
        raise ValueError("No products fetched from the API.")

    # Chuyển đổi dữ liệu thành DataFrame
    df = pd.DataFrame(products)

    # Kết hợp các thông tin thành một chuỗi văn bản duy nhất
    df['combined_text'] = df.apply(combine_text, axis=1)
    # Tiền xử lý văn bản
    tfidf_vectorizer = TfidfVectorizer(stop_words='english')
    tfidf_matrix = tfidf_vectorizer.fit_transform(df['combined_text'])

    # Xây dựng mô hình KNN
    knn_model = NearestNeighbors(n_neighbors=3, metric='cosine')
    knn_model.fit(tfidf_matrix)

    # Lưu mô hình và vectorizer vào Redis
    redis_client.set('tfidf_vectorizer', pickle.dumps(tfidf_vectorizer))
    redis_client.set('knn_model', pickle.dumps(knn_model))


def recommend_knn(product_id, num_recommendations=3):
    global knn_model, tfidf_vectorizer

    # Lấy dữ liệu sản phẩm từ API
    products = fetch_products()["data"]
    df = pd.DataFrame(products)

    # Kết hợp các thông tin thành một chuỗi văn bản duy nhất
    df['combined_text'] = df.apply(combine_text, axis=1)

    # Tải mô hình và vectorizer từ Redis
    knn_model = pickle.loads(redis_client.get('knn_model'))
    tfidf_vectorizer = pickle.loads(redis_client.get('tfidf_vectorizer'))

    # Tìm sản phẩm theo ID
    product_index = df[df['id'] == product_id].index
    if product_index.empty:
        return jsonify({"error": "Product not found"}), 404

    # Gợi ý sản phẩm
    distances, indices = knn_model.kneighbors(tfidf_vectorizer.transform([df['combined_text'][product_index[0]]]),
                                              n_neighbors=num_recommendations)

    # Tạo danh sách sản phẩm gợi ý
    recommendations = []
    for i in range(len(indices.flatten())):
        recommended_product = df.iloc[indices.flatten()[i]]
        recommendations.append({
            "id": int(recommended_product['id']) if 'id' in recommended_product else 0,
            "name": recommended_product['name'] if 'name' in recommended_product else "",
            "price": recommended_product['price'] if 'price' in recommended_product else 0,
            "tags": recommended_product['tags'] if 'tags' in recommended_product else [],
            "categories": recommended_product['categories'] if 'categories' in recommended_product else [],
            "brand": recommended_product['brand'] if 'brand' in recommended_product else {},
            "primaryImage": recommended_product['primaryImage'] if 'primaryImage' in recommended_product else "",
            "sku": recommended_product['sku'] if 'sku' in recommended_product else "",
            "specifications": recommended_product['specifications'] if 'specifications' in recommended_product else [],
            "images": recommended_product['images'] if 'images' in recommended_product else [],
            "des": recommended_product['des'] if 'des' in recommended_product else "",
            "discount": int(recommended_product['discount']) if 'discount' in recommended_product else 0,
            "distance": distances.flatten()[i]
        })

    return recommendations


def fetch_orders():
    response = requests.get("http://api-gateway:8888/api/v1/order" + "/recommend")
    return response.json() if response.status_code == 200 else []


def prepare_data_order(orders):
    # Chuyển đổi dữ liệu đơn hàng thành DataFrame
    data = []
    for order in orders:
        order_id = order['id']
        for detail in order['orderDetails']:
            data.append({'order_id': order_id, 'product_id': detail['medicine'], 'quantity': detail['quantity']})

    df = pd.DataFrame(data)
    return df


def train_model_order():
    orders = fetch_orders()["data"]

    df = prepare_data_order(orders)

    # Tạo bảng giỏ hàng
    basket = df.groupby(['order_id', 'product_id'])['quantity'].sum().unstack().reset_index().fillna(0).set_index(
        'order_id')
    basket = basket.applymap(lambda x: 1 if x > 0 else 0)

    # Áp dụng FP-Growth
    frequent_itemsets = fpgrowth(basket, min_support=0.1, use_colnames=True)
    rules = association_rules(frequent_itemsets, metric="lift", min_threshold=1)
    # Lưu quy tắc vào Redis
    redis_client.set('recommendation_rules', pickle.dumps(rules))


def get_rules_from_redis():
    rules_serialized = redis_client.get('recommendation_rules')
    if rules_serialized:
        return pickle.loads(rules_serialized)
    return None


def recommend_products(ordered_product_ids, num_recommend=5):
    rules = get_rules_from_redis()
    if rules is None:
        print("No rules found in Redis.")
        return []

    # Lọc các sản phẩm gợi ý
    recommendations = rules[rules['antecedents'].apply(lambda x: any(item in x for item in ordered_product_ids))]
    recommendations = recommendations.sort_values(by='lift', ascending=False)

    # Lấy danh sách sản phẩm gợi ý và độ tin cậy
    suggested_products = []
    for _, row in recommendations.iterrows():
        for product in row['consequents']:
            suggested_products.append(product)

    recommend = suggested_products[:num_recommend]

    products = fetch_products()["data"]

    result = [product for product in products if product['id'] in recommend]

    return result
