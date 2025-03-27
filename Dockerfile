FROM gradle:8.7-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle shadowJar

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/app.jar .
CMD ["java", "-jar", "app.jar"]