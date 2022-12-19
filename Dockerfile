# Import parent image
FROM gradle:4.7.0-jdk8-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon



FROM openjdk:8-jre-slim
ARG buildPort=4567
EXPOSE ${buildPort}

ENV portNo=${buildPort}

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spark-java-application.jar

ENTRYPOINT java -jar /app/spark-java-application.jar -p=${portNo}