FROM adoptopenjdk/openjdk11:latest
EXPOSE 8080
ADD target/mapping-mind-back-0.0.1-SNAPSHOT.jar mapping-mind-back.jar
ENTRYPOINT ["java","-jar","/mapping-mind-back.jar"]