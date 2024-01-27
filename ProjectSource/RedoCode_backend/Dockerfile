# build stage
FROM eclipse-temurin:17-jdk-alpine  as build-stage
WORKDIR /backend/
COPY . ./
RUN ./mvnw clean install


# production stage
FROM eclipse-temurin:17-jdk-alpine  as production-stage
COPY --from=build-stage /backend/target/backend-0.0.1-SNAPSHOT.jar /backend/backend.jar
EXPOSE 9090
CMD ["java", "-jar", "/backend/backend.jar"]

