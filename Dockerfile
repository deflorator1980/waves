FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/waves-1.jar /app/
#CMD java -jar target/waves-1.jar