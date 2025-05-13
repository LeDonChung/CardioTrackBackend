# Sử dụng image Python chính thức
FROM python:3.10-slim

# Cài đặt các thư viện cần thiết
WORKDIR /app
COPY . /app
RUN pip install --no-cache-dir -r requirements.txt

# Mở cổng 5000 cho Flask app
EXPOSE 5000

# Lệnh chạy ứng dụng Python
CMD ["python", "inventory_focast.py"]
