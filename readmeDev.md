# SpringBootApplication notes

## env settings:
spring:
export SPRING_PROFILES_ACTIVE=memory
export SPRING_PROFILES_ACTIVE=jpa

idea/terminal without spring:
export profile=memory
export profile=jdbc

## commands
gradle deploy
./catalina.sh run

gradle run
gradle bootrun
build, clean, check

## body
body for extras:
{

"description": 10
}

body a for car:
{
"name": "kocsi",
"brand": "mercedes",
"year": 1920,
"price": 100000
}
body for update without spring:
{
"id": 1,
"name": "kocsi",
"brand": "mercedes",
"year": 2021,
"price": 10
}

## urls:
http://localhost:8080/bmim2214-web/carModels
http://localhost:8080/bmim2214-web/carModelsThyme, admin admin
http://localhost:8081/cars

