CREATE DATABASE idee;
USE idee;

CREATE TABLE CarModel (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          car_name VARCHAR(255) NOT NULL,
                          brand VARCHAR(255) NOT NULL,
                          car_year INT NOT NULL,
                          price DOUBLE NOT NULL,
                          uploadDate DATE NOT NULL
);
CREATE TABLE CarExtra (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          description VARCHAR(255) NOT NULL,
                          car_id BIGINT NOT NULL,
                          FOREIGN KEY (car_id) REFERENCES CarModel(id) ON DELETE CASCADE
);
