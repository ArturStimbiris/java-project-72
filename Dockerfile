FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY app/gradlew .
COPY app/gradle gradle
COPY app/build.gradle .
COPY app/settings.gradle .
COPY app/src src

RUN ./gradlew shadowJar

CMD ["java", "-jar", "/app/build/libs/app.jar"]