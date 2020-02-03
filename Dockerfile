FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} metrics-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/metrics-service.jar"]