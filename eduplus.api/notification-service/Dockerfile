FROM openjdk:22
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} notificatoin-service.jar
ENTRYPOINT ["java","-jar","/notificatoin.jar"]
EXPOSE 8081