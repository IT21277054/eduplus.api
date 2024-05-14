FROM openjdk:22
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} user-management-service.jar
ENTRYPOINT ["java","-jar","/course-management-service.jar"]
EXPOSE 9090