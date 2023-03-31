Запуск в Docker:
1. gradle build (gradle bootJar)
2. docker-compose up -d --build
3. POST source-connector 8083
4. POST sink-connector 8083