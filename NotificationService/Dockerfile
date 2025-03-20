FROM openjdk:17-jdk-slim


WORKDIR /app

COPY build/libs/NotificationService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9093

ENTRYPOINT ["java", "-jar", "app.jar"]
