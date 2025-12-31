FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -Dmaven.wagon.http.retryHandler.count=3

COPY src ./src

RUN mvn clean package -DskipTests

#FROM maven:3.9.6-eclipse-temurin-17
FROM gcr.io/distroless/java17-debian12

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

#ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8000}"]

ENTRYPOINT ["java", "-jar", "app.jar --server.port=${PORT:-8000}"]