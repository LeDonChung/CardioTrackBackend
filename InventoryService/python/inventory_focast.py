import mysql.connector
import pandas as pd
import json
from sklearn.ensemble import RandomForestRegressor
from sklearn.model_selection import train_test_split

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

def fetch_inventory_data():
    db = connect_to_db()
    df = pd.read_sql("SELECT inventory_id, total_product, capacity FROM inventories", con=db)
    db.close()
    return df

def fetch_inventory_imports():
    db = connect_to_db()
    query = """
        SELECT ii.inventory_id, id.quantity, ii.import_date
        FROM inventory_imports ii
        JOIN inventory_details id ON ii.inventory_id = id.inventory_id
    """
    df = pd.read_sql(query, con=db)
    db.close()
    return df

def fetch_shelf_data():
    db = connect_to_db()
    df = pd.read_sql("SELECT inventory_id, total_product, capacity FROM shelfs", con=db)
    db.close()
    return df

def integrate_and_forecast():
    inv = fetch_inventory_data()
    imp = fetch_inventory_imports()
    shelf = fetch_shelf_data()

    data = pd.merge(inv, imp, on="inventory_id", how="inner")
    data = pd.merge(data, shelf, on="inventory_id", how="inner")

    if 'total_product_x' not in data.columns or 'capacity_x' not in data.columns:
        raise KeyError("Missing expected columns")

    model = RandomForestRegressor(n_estimators=100, random_state=42)
    X = data[['total_product_x', 'capacity_x']]
    y = data['total_product_x']

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    model.fit(X_train, y_train)

    # Dự báo nhu cầu nhập hàng trong tháng tới (giả sử lấy giá trị dự báo từ X_test)
    forecasted_demand = model.predict([X_test.iloc[0]])  # Dự báo cho một mẫu đầu tiên (hoặc có thể là mẫu khác)

    return forecasted_demand[0]  # Trả về một con số duy nhất

if __name__ == "__main__":
    forecast = integrate_and_forecast()
    print(f"Forecasted demand for the next month: {forecast}")
