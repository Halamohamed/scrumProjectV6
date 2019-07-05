FROM openjdk:8
ADD target/it-project-scrum.jar it-project-scrum.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar","it-project-scrum.jar"]