FROM maven:alpine
RUN apk --update add postgresql-client
WORKDIR /code
COPY . /code
RUN ["mvn","clean","package"]
EXPOSE 8080
