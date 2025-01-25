# IDDE notes

## env settings:
springes:
export SPRING_PROFILES_ACTIVE=memory
export SPRING_PROFILES_ACTIVE=jpa

idea/terminal a spring nelkulinek:
export profile=memory
export profile=jdbc

## commands
gradle deploy
./catalina.sh run

gradle run
gradle bootrun
build, clean, check

## body
body az extrahoz:
{

"description": 10
}

body a carhoz:
{
"name": "kocsi",
"brand": "mercedes",
"year": 1920,
"price": 100000
}
updatehez springnelkuliben:
{
"id": 1,
"name": "kocsi",
"brand": "mercedes",
"year": 2021,
"price": 10
}

## urlk:
http://localhost:8080/bmim2214-web/carModels
http://localhost:8080/bmim2214-web/carModelsThyme, admin admin
http://localhost:8081/cars

## mysql parancsok:
DROP DATABASE idee;
DROP TABLE CarModel;

ALTER TABLE CarModel ADD COLUMN fuel_type VARCHAR(50);
ALTER TABLE CarModel DROP COLUMN fuel_type;

INSERT INTO CarModel (car_name, brand, car_year, price, uploadDate)
VALUES ('Model S', 'Tesla', 2022, 799, '2025-01-24');

UPDATE CarModel SET price = 75000 WHERE car_name = 'Model S';
DELETE FROM CarModel WHERE id = 1;


