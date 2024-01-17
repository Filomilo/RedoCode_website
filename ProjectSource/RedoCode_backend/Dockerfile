FROM eclipse-temurin:17-jdk-jammy
WORKDIR /backend/
COPY . ./
RUN ./gradlew build


ENV PORT=9090
EXPOSE 9090

CMD [ "java","-jar","build/libs/Coding_exercise_backend-0.0.1-SNAPSHOT.jar" ]