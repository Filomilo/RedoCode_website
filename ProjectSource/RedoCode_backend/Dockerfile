# build stage
FROM maven  as build-stage
WORKDIR /backend/
COPY . ./
RUN ls
RUN mvn clean install -DskipTests 
# EXPOSE 9090
# RUN ls
# ENTRYPOINT [ "ls" ]
##ENTRYPOINT [ "java","-jar","target/backend-0.0.1-SNAPSHOT.jar" ]
#ENTRYPOINT ["tail", "-f", "/dev/null"]



# production stage
FROM eclipse-temurin:17-jdk-alpine  as production-stage
COPY --from=build-stage /backend/target/backend-0.0.1-SNAPSHOT.jar /backend/backend.jar
EXPOSE 9090
CMD ["java","-XX:TieredStopAtLevel=1", "-jar", "/backend/backend.jar"]

