FROM openjdk:19-jdk
WORKDIR /app
COPY applications/target/applications-1.0-SNAPSHOT.jar /app/app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar"]