package edu.bbte.idde.bmim2214.dataaccess.dao;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Profile("jdbc")
@Repository
@Slf4j
public class CarDaoJdbc implements CarDao {

    private final HikariDataSource dataSource;

    @Autowired
    public CarDaoJdbc(HikariDataSource dataSource) {
        log.info("hikari dataSource setup");
        this.dataSource = dataSource;
    }

    @Override
    public void createCar(CarModel car) throws CarExceptionDatabase {
        log.info("create car");
        String sqlString = "INSERT INTO CarModel (car_name, brand, car_year, price, uploadDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlQuery = connection.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS)) {
            sqlQuery.setString(1, car.getName());
            sqlQuery.setString(2, car.getBrand());
            sqlQuery.setInt(3, car.getYear());
            sqlQuery.setDouble(4, car.getPrice());
            sqlQuery.setDate(5, car.getUploadDate());

            int affectedRows = sqlQuery.executeUpdate();

            if (affectedRows == 0) {
                log.info("Failed to insert the car.");
                throw new CarExceptionDatabase("Failed to insert the car.");
            }

            ResultSet generatedKeys = sqlQuery.getGeneratedKeys();
            if (generatedKeys.next()) {
                car.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            log.info("Failed to insert the car.");
            throw new CarExceptionDatabase("Failed to insert the car.", e);
        }
    }


    @Override
    public void deleteCar(long id) throws CarExceptionDatabase {
        log.info("delete car");
        String sqlString = "DELETE FROM CarModel WHERE CarModel.id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlQuery = connection.prepareStatement(sqlString)) {
            sqlQuery.setLong(1, id);
            int affectedRows = sqlQuery.executeUpdate();
            if (affectedRows == 0) {
                log.info("There is no such an id");
                throw new CarExceptionDatabase("There is no such an id");
            }

        } catch (SQLException e) {
            log.info("There is no such an id");
            throw new CarExceptionDatabase("There is no such an id", e);
        }
    }

    @Override
    public void updateCar(CarModel car) throws CarExceptionDatabase {
        log.info("update car");
        String sqlString = "UPDATE CarModel"
                + " SET car_name = ?, brand = ?, car_year = ?, price = ?, uploadDate = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlQuery = connection.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS)) {
            sqlQuery.setString(1, car.getName());
            sqlQuery.setString(2, car.getBrand());
            sqlQuery.setInt(3, car.getYear());
            sqlQuery.setDouble(4, car.getPrice());
            sqlQuery.setDate(5, car.getUploadDate());
            sqlQuery.setLong(6, car.getId());

            int affectedRows = sqlQuery.executeUpdate();

            if (affectedRows == 0) {
                log.info("There is no such an id");
                throw new CarExceptionDatabase("There is no such an id");
            }

            ResultSet generatedKeys = sqlQuery.getGeneratedKeys();
            if (generatedKeys.next()) {
                car.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            log.info("There is no such an id");
            throw new CarExceptionDatabase("There is no such an id", e);
        }
    }

    @Override
    public CarModel readCar(long id) throws CarExceptionDatabase {
        log.info("read car");
        String sqlString = "SELECT * FROM CarModel WHERE CarModel.id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlQuery = connection.prepareStatement(sqlString)) {
            sqlQuery.setLong(1, id);

            try (ResultSet resultSet = sqlQuery.executeQuery()) {
                if (resultSet.next()) {
                    return extractCarModel(resultSet);
                } else {
                    log.info("There is no such an id");
                    throw new CarExceptionDatabase("There is no such an id");
                }
            }

        } catch (SQLException e) {
            log.info("There is no such an id");
            throw new CarExceptionDatabase("There is no such an id", e);
        }
    }


    @Override
    public List<CarModel> getAllCars() throws CarExceptionDatabase {
        log.info("get all cars");
        List<CarModel> carList = new ArrayList<>();
        String sqlString = "SELECT * FROM CarModel";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlQuery = connection.prepareStatement(sqlString);
             ResultSet resultSet = sqlQuery.executeQuery()) {
            while (resultSet.next()) {
                carList.add(extractCarModel(resultSet));
            }
        } catch (SQLException e) {
            log.info("Failed to get all cars.");
            throw new CarExceptionDatabase("Failed to get all cars.", e);
        }

        return carList;
    }

    @Override
    public List<CarModel> getAllCarsFromSpecYear(int year) throws CarExceptionDatabase {
        log.info("Get all cars from a specific year");
        List<CarModel> carList = new ArrayList<>();
        String sqlString = "SELECT * FROM CarModel WHERE car_year = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlQuery = connection.prepareStatement(sqlString)) {
            sqlQuery.setInt(1, year);

            try (ResultSet resultSet = sqlQuery.executeQuery()) {
                while (resultSet.next()) {
                    carList.add(extractCarModel(resultSet));
                }
            }
        } catch (SQLException e) {
            log.info("Failed to get all cars from a specific year.");
            throw new CarExceptionDatabase("Failed to get all cars from a specific year.", e);
        }

        return carList;
    }


    private CarModel extractCarModel(ResultSet resultSet) throws SQLException {
        CarModel car = new CarModel();
        car.setId(resultSet.getLong("id"));
        car.setName(resultSet.getString("car_name"));
        car.setBrand(resultSet.getString("brand"));
        car.setYear(resultSet.getInt("car_year"));
        car.setPrice(resultSet.getDouble("price"));
        car.setUploadDate(resultSet.getDate("uploadDate"));
        return car;
    }

}
