# Use an official Java runtime as base image
FROM eclipse-temurin:17-jdk

# Set work directory in container
WORKDIR /app

# Copy the JAR from the correct relative path
COPY build/libs/car-rental-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
