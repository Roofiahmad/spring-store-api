FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -Dmaven.wagon.http.retryHandler.count=3

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8000}"]



#FROM maven:3.9.6-eclipse-temurin-17 AS build
#WORKDIR /app
#
#COPY pom.xml .
#
#RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B
#
#COPY src ./src
#
#RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests -B
#
#FROM eclipse-temurin:17-jre-jammy
#WORKDIR /app
#
#RUN useradd -ms /bin/sh springuser
#USER springuser
#
#COPY --from=build /app/target/*.jar app.jar
#
#ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8000}"]