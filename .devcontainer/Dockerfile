# Use the official maven/Java image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.8.4-openjdk-11-slim as builder

# Copy local code to the container image.
WORKDIR /app
COPY . .

# Build a release artifact.
RUN mvn package -DskipTests

# Use OpenJDK for runtime
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /app/target/codebase-0.0.1-SNAPSHOT.jar /app/codebase.jar

# Run the web service on container startup.
CMD ["java", "-jar", "/app/codebase.jar"]
