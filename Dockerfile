FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
COPY . .

RUN ./gradlew -p app shadowJar

CMD ["java", "-jar", "/app/app/build/libs/app.jar"]