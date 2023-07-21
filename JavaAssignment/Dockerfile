FROM maven:3.8.5-openjdk-11 AS build
COPY ..
RUN mvn clean package -DskipsTests

FROM openjdk:11.0.1-jdk-slim
COPY --from=build /target/JavaAssignment-0.0.1-SNAPSHOT.jar JavaAssignment.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","JavaAssignment.jar"]

