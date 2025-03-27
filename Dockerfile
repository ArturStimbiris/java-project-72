FROM eclipse-temurin:17-jdk-jammy

WORKDIR /workspace

COPY . .

WORKDIR /workspace/app

RUN chmod +x gradlew

RUN ./gradlew shadowJar

CMD ["java", "-jar", "/workspace/app/build/libs/app.jar"]