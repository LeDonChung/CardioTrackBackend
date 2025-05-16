FROM openjdk:17-jdk-slim

# Cài đặt Python và pip
RUN apt-get update && apt-get install -y python3 python3-pip && \
    ln -sf /usr/bin/python3 /usr/bin/python

# Cài đặt các thư viện Python cần thiết
RUN pip3 install mysql-connector pandas scikit-learn

WORKDIR /app

COPY build/libs/*.jar app.jar
COPY python/ ./python/

EXPOSE 9091

ENTRYPOINT ["java", "-jar", "app.jar"]
