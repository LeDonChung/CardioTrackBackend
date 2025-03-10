import py_eureka_client.eureka_client as eureka_client

EUREKA_SERVER = "http://localhost:8761/eureka/"
SERVICE_NAME = "recommend-service"
SERVICE_PORT = 9016

try:
    eureka_client.init(
        eureka_server=EUREKA_SERVER,
        app_name=SERVICE_NAME,
        instance_port=SERVICE_PORT,
        instance_ip="127.0.0.1",
        renewal_interval_in_secs=30,
        duration_in_secs=90
    )
    print("✅ Đăng ký Eureka thành công!")
except Exception as e:
    print(f"❌ Lỗi kết nối đến Eureka: {e}")
