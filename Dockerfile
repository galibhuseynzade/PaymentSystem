FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/PaymentSystem-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8181

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
