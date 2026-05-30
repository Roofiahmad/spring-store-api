FROM maven:3.9.12-eclipse-temurin-25-alpine AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:resolve-plugins dependency:resolve -B

COPY src ./src
RUN mvn package -DskipTests -B

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

RUN apk update && apk upgrade --no-cache && \
    addgroup -S springgroup && adduser -S springuser -G springgroup

COPY --from=build /app/target/*.jar app.jar

RUN chown springuser:springgroup app.jar

USER springuser

ENTRYPOINT ["java", "-jar", "app.jar"]