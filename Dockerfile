FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN apk update && apk upgrade && \
    addgroup -S springgroup && adduser -S springuser -G springgroup

COPY --from=build /app/target/*.jar app.jar
USER springuser
ENTRYPOINT ["java", "-jar", "app.jar"]