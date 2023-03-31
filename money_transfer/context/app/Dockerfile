FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8080
RUN mkdir /app
COPY money_transfer/build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]