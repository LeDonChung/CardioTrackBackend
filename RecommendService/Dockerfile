FROM python:3.9-slim

RUN apt-get update && apt-get install -y \
    build-essential \
    libpq-dev \
    && rm -rf /var/lib/apt/lists/*

RUN pip install --upgrade pip

# Chuyển đến thư mục /app trong container
WORKDIR /app

# Sao chép file requirements.txt vào thư mục /app trong container
COPY requirements.txt /app/

# Cài đặt các thư viện từ requirements.txt
RUN pip install -r requirements.txt

# Sao chép toàn bộ mã nguồn trong thư mục app/ vào thư mục /app trong container
COPY . /app/

# Mở cổng 9016 để chạy ứng dụng
EXPOSE 9016

# Cập nhật PYTHONPATH để bao gồm thư mục 'app'
ENV PYTHONPATH=/app/app:$PYTHONPATH

CMD ["python", "-m", "app.api"]

