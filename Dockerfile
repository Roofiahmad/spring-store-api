#FROM maven:3.9.12-eclipse-temurin-25-alpine AS build
#WORKDIR /app
#
#COPY pom.xml .
#RUN mvn dependency:go-offline -B
#
#COPY src ./src
#RUN mvn clean package -DskipTests -B
#
#FROM eclipse-temurin:25-jre-alpine
#WORKDIR /app
#
#RUN apk update && apk upgrade --no-cache && \
#    addgroup -S springgroup && adduser -S springuser -G springgroup
#
#COPY --from=build /app/target/*.jar app.jar
#
#RUN chown springuser:springgroup app.jar
#
#USER springuser
#
#ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]


FROM maven:3.9.12-eclipse-temurin-25-alpine AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests -B


FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

RUN apk update && apk upgrade --no-cache && \
    addgroup -S springgroup && adduser -S springuser -G springgroup

COPY --from=build /app/target/*.jar app.jar

RUN chown -R springuser:springgroup /app

USER springuser

RUN mkdir extracted && \
    cd extracted && \
    java -Djarmode=tools -jar ../app.jar extract


RUN java -XX:ArchiveClassesAtExit=extracted/app.jsa \
         -Dspring.context.exit=onRefresh \
         -cp "extracted/dependencies/*:extracted/spring-boot-loader/*:extracted/snapshot-dependencies/*:extracted/application/*" \
         org.springframework.boot.loader.launch.JarLauncher || \
    java -XX:ArchiveClassesAtExit=extracted/app.jsa \
         -Dspring.context.exit=onRefresh \
         -cp "extracted/dependencies/*:extracted/spring-boot-loader/*:extracted/snapshot-dependencies/*:extracted/application/*" \
         org.springframework.boot.loader.JarLauncher

ENTRYPOINT ["java", \
            "-XX:MaxRAMPercentage=75.0", \
            "-XX:SharedArchiveFile=extracted/app.jsa", \
            "-cp", "extracted/dependencies/*:extracted/spring-boot-loader/*:extracted/snapshot-dependencies/*:extracted/application/*", \
            "org.springframework.boot.loader.launch.JarLauncher"]