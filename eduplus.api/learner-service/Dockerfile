FROM openjdk:22
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} learner-service.jar
ENTRYPOINT ["java","-jar","/learner-service.jar"]
EXPOSE 8093