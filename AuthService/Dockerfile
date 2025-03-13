# ======================= #
# 1️⃣ Giai đoạn Build App #
# ======================= #
FROM gradle:8.4-jdk17 AS builder

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép các file cấu hình trước để cache dependencies
COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./

# Cấp quyền thực thi cho `gradlew`
RUN chmod +x gradlew

# Tải dependencies trước để tối ưu cache
RUN ./gradlew dependencies --no-daemon

# Sao chép toàn bộ source code
COPY . .

# Build ứng dụng (tạo file JAR)
RUN ./gradlew bootJar --no-daemon

# ======================= #
# 2️⃣ Giai đoạn Run App   #
# ======================= #
FROM openjdk:17-jdk-slim

# Đặt biến môi trường


# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR từ giai đoạn build
COPY --from=builder /app/build/libs/*.jar app.jar

# Cấu hình cổng chạy ứng dụng
EXPOSE 9090

# Chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]
