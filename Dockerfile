FROM ubuntu:latest
LABEL authors="hajrudin.imamovic"

# Use OpenJDK 23 (match your local Java version)
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Build the app
RUN ./mvnw clean package -DskipTests

# Expose the port that Render assigns
EXPOSE 8080

# Set the start command
CMD ["java", "-jar", "target/spotlight-sarajevo-0.0.1-SNAPSHOT.jar"]