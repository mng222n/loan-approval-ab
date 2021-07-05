# Stage 1: maven build
FROM maven:3-openjdk-8 as build
# Compile, test, and package
WORKDIR build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package

# Stage 2, run jar file using openjdk
FROM openjdk:8-jdk-alpine

WORKDIR app

ARG JAR_FILE=cs-systems-0.0.1-SNAPSHOT.jar

COPY --from=build build/target/${JAR_FILE} app.jar

ENTRYPOINT java -jar /app/app.jar
