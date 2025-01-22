FROM maven:3.8.3-openjdk-17  as build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

FROM openjdk:17-jdk
COPY --from=build /app/target/*.jar /app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=stack", "-jar","/app.jar"]