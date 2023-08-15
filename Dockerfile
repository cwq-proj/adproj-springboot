FROM eclipse-temurin:20-jdk-alpine
WORKDIR /
EXPOSE 8000
ADD target/ad-project-backend-0.0.1-SNAPSHOT.jar springboot.jar
ENTRYPOINT ["java", "-jar", "springboot.jar"]