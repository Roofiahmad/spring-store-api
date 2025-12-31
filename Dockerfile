#FROM maven:3.9.6-eclipse-temurin-17 AS build
#WORKDIR /app
#
#COPY pom.xml .
#
#RUN mvn dependency:go-offline -Dmaven.wagon.http.retryHandler.count=3
#
#COPY src ./src
#RUN mvn clean package -DskipTests
#
#FROM eclipse-temurin:17-jre-jammy
#
#WORKDIR /app
#
#RUN apt-get update && \
#    apt-get upgrade -y && \
#    apt-get clean && \
#    rm -rf /var/lib/apt/lists/*
#
#COPY --from=build /app/target/*.jar app.jar
#
#ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8000}"]



FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

COPY pom.xml .

RUN --mount=type=cache,id=m2_cache,target=/root/.m2 \
    mvn dependency:go-offline -B -Dmaven.wagon.http.retryHandler.count=3

COPY src ./src

RUN --mount=type=cache,id=m2_cache,target=/root/.m2 \
    mvn clean package -DskipTests -B

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN apk update && apk upgrade && \
    addgroup -S springgroup && adduser -S springuser -G springgroup

COPY --from=build /app/target/*.jar app.jar
RUN chown springuser:springgroup app.jar

USER springuser
ENTRYPOINT ["java", "-jar", "app.jar"]