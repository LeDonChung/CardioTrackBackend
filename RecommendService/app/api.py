from flask import Flask, request, jsonify
import app.erureka_client
import services

app = Flask(__name__)

services.train_model()


@app.route('/api/v1/recommend/<int:product_id>', methods=['GET'])
def recommend(product_id):
    recommendations = services.recommend_knn(product_id, 12)
    return jsonify({"data": recommendations})


@app.route('/api/v1/recommend/order', methods=['GET'])
def recommend_order():
    # Lấy danh sách sản phẩm đã đặt hàng từ query parameters
    ordered_product_ids = request.args.getlist('product_ids', type=int)

    if not ordered_product_ids:
        return jsonify({"error": "No product IDs provided"}), 400

    # Gọi hàm gợi ý sản phẩm từ services
    recommendations = services.recommend_products(ordered_product_ids)

    return jsonify({"data": recommendations})


if __name__ == '__main__':
    services.schedule_training()  # Bắt đầu lên lịch huấn luyện
    app.run(port=9016, debug=True)
