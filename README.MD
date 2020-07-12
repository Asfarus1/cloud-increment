<pre>Тестовое приложение счетчик запросов для запуска в docker.
Операции:
1. curl http://localhost:8080 - текущее значение счетчика
2. curl -X POST http://localhost:8080 - инкремент и возврат значения счетчика
3. curl -X DELETE http://localhost:8080 - обнуление значения счетчика

Запуск из корня:
./mvnw clean package -DskipTests 
docker-compose up</pre>
