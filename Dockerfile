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

# Create the deployment directory and Extract the fat JAR into layers (dependencies, application, loader)
RUN mkdir extracted && \
    cd extracted && \
    java -Djarmode=tools -jar ../app.jar extract

# 2. Run a training cycle to generate the Class Data Sharing (CDS) archive file
RUN java -XX:ArchiveClassesAtExit=extracted/app.jsa \
         -Dspring.context.exit=onRefresh \
         -cp "extracted/dependencies/*:extracted/application/*" \
         org.springframework.boot.loader.launch.JarLauncher

# Launch using the optimized classpath loader with the CDS archive mapped into memory
ENTRYPOINT ["java", \
            "-XX:MaxRAMPercentage=75.0", \
            "-XX:SharedArchiveFile=extracted/app.jsa", \
            "-cp", "extracted/dependencies/*:extracted/application/*", \
            "org.springframework.boot.loader.launch.JarLauncher"]