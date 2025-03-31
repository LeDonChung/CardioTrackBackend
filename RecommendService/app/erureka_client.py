import py_eureka_client.eureka_client as eureka_client
import os
EUREKA_SERVER = os.getenv("EUREKA_CLIENT_SERVICEURL_DEFAULTZONE")
SERVICE_NAME = os.getenv("SERVICE_NAME")
SERVICE_PORT = int(os.getenv("SERVICE_PORT"))
EUREKA_INSTANCE_HOSTNAME = os.getenv("EUREKA_INSTANCE_HOSTNAME")
try:
    eureka_client.init(
        eureka_server=EUREKA_SERVER,
        app_name=SERVICE_NAME,
        instance_port=SERVICE_PORT,
        instance_ip=EUREKA_INSTANCE_HOSTNAME,
        renewal_interval_in_secs=30,
        duration_in_secs=90
    )
    print("✅ Đăng ký Eureka thành công!")
except Exception as e:
    print(f"❌ Lỗi kết nối đến Eureka: {e}")
