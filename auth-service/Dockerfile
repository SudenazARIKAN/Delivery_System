FROM eclipse-temurin:21-jdk

ARG JAR_FILE=target/auth-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8084

ENTRYPOINT ["java","-jar","/app.jar"]