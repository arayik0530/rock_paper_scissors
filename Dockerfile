# Stage 1: Build the Java backend
FROM maven:3.8.4-openjdk-17 AS backend-build
WORKDIR /app
COPY . .
RUN mvn clean compile package

EXPOSE 8090

CMD ["java", "-jar", "target/ROCK_PAPER_SCISSORS-0.0.1-SNAPSHOT.jar"]
