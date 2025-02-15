Описание проекта
Проект представляет собой сервис для оценки компаний на основе правил DMN (Decision Model and Notation). Сервис использует Camunda DMN Engine для выполнения правил
(TODO: необходимо добавить Elasticsearch. проверить возможность создания Docker-образа для удобства развертывания.)

Требования
Для работы с проектом необходимы следующие компоненты:

Java 17 или выше
Maven для сборки проекта
Docker для запуска сервисов (Elasticsearch и приложение)
Postman или любой другой инструмент для отправки HTTP-запросов

Инструкция по сборке
1. Склонируйте репозиторий 
    git clone <URL_РЕПОЗИТОРИЯ>
    cd project
2. Соберите проект с помощью Maven 
    mvn clean install


Инструкция по локальному запуску
Отправьте запрос к API
Отправьте POST-запрос к эндпоинту api/scoring с данными компании:

curl -X POST http://localhost:8080/scoring 
-H "Content-Type: application/json" 
-d '{
"inn": "990956789012",
"region_code": 24,
"capital": 400,
}'


Пример ответа:
{
"success": true,
"results": [
"Компания находится в запрещенном регионе (Красноярский край)",
"Капитал компании меньше 5 000 000"
]
}

DMN-модель
DMN-модель расположена в файле src/main/resources/dmn/scoring-decision.dmn. Она содержит правила для оценки компаний на основе следующих параметров:

Inn : Идентификационный номер налогоплательщика (ИНН).
RegionCode : Код региона.
Capital : Капитал компании.
Правила модели:
Запрещенный регион : Если RegionCode = 24.
Капитал меньше 5 млн : Если Capital < 5000000.
Не является резидентом РФ : Если Inn начинается с "9909".
Является ИП : Если длина Inn равна 12 символам.


