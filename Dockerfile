FROM maven:alpine
WORKDIR /code
COPY . /code
RUN ["mvn","clean","package"]
EXPOSE 8080
