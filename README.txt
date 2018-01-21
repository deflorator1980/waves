Задание: task.txt

Коллекция для тестирования Postman'ом: waves.postman_collection.json

Запуск:
        docker-compose pull
        docker-compose up

Создаёт контейнер с Postges'ом, который бежит на 0.0.0.0:5422.
                                                user/passwd: postgres/postgres;
                                                dbname: waves
База сама создаётся, сама заполняется.
