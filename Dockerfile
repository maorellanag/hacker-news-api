FROM maven:3.9.9-eclipse-temurin-17 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar java-articles-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/java-articles-api.jar"]