FROM openjdk:17-jdk-slim

WORKDIR /app

COPY /build/libs/*.jar app.jar

EXPOSE 9101

ENTRYPOINT ["java", "-jar", "app.jar"]