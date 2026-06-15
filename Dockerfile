FROM maven:3.9.12-amazoncorretto-25-alpine AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:resolve-plugins dependency:resolve -B

COPY src ./src
RUN mvn package -DskipTests -B

FROM amazoncorretto:25-alpine
WORKDIR /app

RUN apk update && apk upgrade --no-cache && \
    addgroup -S springgroup && adduser -S springuser -G springgroup

ENV TZ=Asia/Jakarta

COPY --from=build /app/target/*.jar app.jar

RUN chown springuser:springgroup app.jar

USER springuser

ENTRYPOINT ["java","--enable-native-access=ALL-UNNAMED", "-jar", "app.jar"]