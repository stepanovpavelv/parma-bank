FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8081
ARG JAR_FILE=reporting/target/reporting.jar
ADD ${JAR_FILE} app.jar

# Add wait-for-it
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "report_db:5433", "--", "java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]