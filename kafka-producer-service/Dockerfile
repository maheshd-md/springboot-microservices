FROM openjdk:8-jdk-alpine
MAINTAINER dhavalshankh.md@gmail.com
VOLUME /tmp
RUN addgroup -S "springboot-microservices"
RUN adduser -S maheshd -G "springboot-microservices"
ARG APP_NAME=kafka-producer-service
RUN mkdir -p /home/mahesh/app
ARG JAR_FILE=target/${APP_NAME}-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /home/mahesh/app/app.jar
ENTRYPOINT ["java","-jar","/home/mahesh/app/app.jar"]
