# Stage 1: Build the application
FROM gradle:jdk17-alpine AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY build.gradle.kts /workspace
COPY src /workspace/src
RUN gradle clean build

# Stage 2: Create the image
FROM openjdk:17-alpine
COPY --from=build /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]