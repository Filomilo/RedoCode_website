# build stage
FROM maven  as build-stage
WORKDIR /backend/
COPY . ./
RUN mvn clean install -DskipTests 


# production stage
FROM eclipse-temurin:21  as production-stage
RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install -y curl 
COPY --from=build-stage /backend/target/backend-0.0.1-SNAPSHOT.jar /backend/backend.jar
EXPOSE 9090
CMD ["java","-XX:TieredStopAtLevel=1", "-jar", "/backend/backend.jar"]

# FROM maven  as build-stage
# WORKDIR /backend/
# COPY . ./
# RUN mvn clean install -DskipTests 
# CMD ["tail", "-f", "/dev/null"]