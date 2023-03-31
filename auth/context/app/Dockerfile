FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8082
RUN mkdir /app
COPY auth/build/libs/*.jar /app/spring-boot-application.jar

# Add wait-for-it
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "auth_db:5434", "--", "java", "-jar", "/app/spring-boot-application.jar"]