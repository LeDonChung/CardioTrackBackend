import mysql.connector
import pandas as pd
from flask import Flask, request, jsonify
from flask_cors import CORS
from sklearn.ensemble import RandomForestRegressor  # Sử dụng Random Forest
from sklearn.model_selection import train_test_split

app = Flask(__name__)
CORS(app)

# Kết nối đến cơ sở dữ liệu MySQL
def connect_to_db():
    return mysql.connector.connect(
        host="localhost",
        port=3306,
        user="root",
        password="sapassword",
        database="tcinventory",
        charset='utf8mb4',               
        collation='utf8mb4_general_ci'  
    )

# Truy vấn dữ liệu từ bảng inventories
def fetch_inventory_data():
    db_connection = connect_to_db()
    query = "SELECT inventory_id, total_product, capacity FROM inventories"  # Sử dụng inventory_id thay vì product_id
    df = pd.read_sql(query, con=db_connection)
    db_connection.close()
    return df

# Truy vấn dữ liệu từ bảng inventory_imports và inventory_details
def fetch_inventory_imports():
    db_connection = connect_to_db()
    query = """
        SELECT ii.inventory_id, id.quantity, ii.import_date
        FROM inventory_imports ii
        JOIN inventory_details id ON ii.inventory_id = id.inventory_id
    """  # Lấy số lượng từ inventory_details
    df = pd.read_sql(query, con=db_connection)
    db_connection.close()
    return df

# Truy vấn dữ liệu từ bảng shelfs
def fetch_shelf_data():
    db_connection = connect_to_db()
    query = "SELECT inventory_id, total_product, capacity FROM shelfs"  # Sử dụng inventory_id cho việc kết hợp
    df = pd.read_sql(query, con=db_connection)
    db_connection.close()
    return df

# Tích hợp dữ liệu từ kho, nhập hàng và kệ vào mô hình
# Tích hợp dữ liệu từ kho, nhập hàng và kệ vào mô hình
def integrate_data_for_model():
    # Lấy dữ liệu từ bảng inventories, inventory_imports và shelfs
    inventory_data = fetch_inventory_data()
    inventory_imports = fetch_inventory_imports()
    shelf_data = fetch_shelf_data()

    # Kết hợp dữ liệu từ các bảng để tạo bộ dữ liệu huấn luyện
    data = pd.merge(inventory_data, inventory_imports, on="inventory_id", how="inner")
    data = pd.merge(data, shelf_data, on="inventory_id", how="inner")

    # In ra các cột có trong data sau khi merge
    print(data.columns)  # Kiểm tra các cột có trong DataFrame

    # Kiểm tra và sử dụng các cột đúng từ bảng inventories và shelfs
    if 'total_product_x' not in data.columns or 'capacity_x' not in data.columns:
        raise KeyError("Các cột 'total_product_x' hoặc 'capacity_x' không tồn tại trong dữ liệu")

    # Mô hình dự báo với Random Forest
    model = RandomForestRegressor(n_estimators=100, random_state=42)

    # Chọn cột đúng từ inventory và shelf data
    X = data[['total_product_x', 'capacity_x']]  # Dữ liệu từ bảng inventories
    y = data['total_product_x']  # Dự báo nhu cầu bổ sung (có thể thay đổi tùy vào mục tiêu)

    # Chia dữ liệu thành tập huấn luyện và kiểm tra
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    # Huấn luyện mô hình
    model.fit(X_train, y_train)
    
    return model

# Tích hợp mô hình dự báo
model = integrate_data_for_model()

# Dự báo tổng nhu cầu
def forecast_total_demand(current_inventory, current_capacity, import_quantity):
    forecast = model.predict([[current_inventory, current_capacity]])[0]
    return forecast

@app.route('/api/forecast_inventory', methods=['POST'])
def forecast_inventory():
    try:
        # Lấy dữ liệu từ request
        data = request.get_json()
        current_inventory = data.get('current_inventory', 0)
        current_capacity = data.get('current_capacity', 100)
        import_quantity = data.get('import_quantity', 0)  # Số lượng nhập vào từ phiếu nhập hàng

        # Dự báo nhu cầu sản phẩm từ tổng số sản phẩm và dung tích
        forecast = forecast_total_demand(current_inventory, current_capacity, import_quantity)

        return jsonify({
            'forecasted_demand': forecast,
            'suggestions': f'Replenish {forecast - current_inventory} units of stock.'
        })
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/api/forecast_shelf', methods=['POST'])
def forecast_shelf():
    try:
        # Lấy dữ liệu từ request
        data = request.get_json()
        current_shelf_inventory = data.get('current_shelf_inventory', 0)
        current_shelf_capacity = data.get('current_shelf_capacity', 50)  # Mặc định là 50 nếu không cung cấp

        # Dự báo nhu cầu cho kệ
        forecast = forecast_total_demand(current_shelf_inventory, current_shelf_capacity, import_quantity)

        return jsonify({
            'forecasted_demand_for_shelf': forecast,
            'suggestions_for_shelf': f'Replenish {forecast - current_shelf_inventory} units on the shelf.'
        })
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, port=5000)
