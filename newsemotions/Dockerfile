FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} news-emotions.jar
ENTRYPOINT ["java",\
"-Djava.security.egd=file:/dev/./urandom",\
"-jar",\
"/news-emotions.jar"]
