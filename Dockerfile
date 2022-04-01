FROM openjdk:17
ADD target/Docker-Comment.jar Docker-Comment.jar
EXPOSE 3015
ENTRYPOINT ["java","-jar","Docker-Comment.jar"]