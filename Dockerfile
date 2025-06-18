FROM openjdk:23-jdk-slim

WORKDIR /app

COPY target/main-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8765

CMD ["java", "-jar", "app.jar"]