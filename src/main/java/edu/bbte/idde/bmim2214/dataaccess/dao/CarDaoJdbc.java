package edu.bbte.idde.bmim2214.dataaccess.dao;

import edu.bbte.idde.bmim2214.dataaccess.dao.CarDao;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionNoId;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarDaoJdbc implements CarDao {

    private final HikariDataSource dataSource;
    private static final Logger LOG = LoggerFactory.getLogger(CarDaoJdbc.class);

    public CarDaoJdbc() {
        LOG.info("hikari dataSource setup");
        dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/idee?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("semm i");
        dataSource.setMaximumPoolSize(4);
    }

    @Override
    public void createCar(CarModel car) {
        LOG.info("create car");
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
                throw new RuntimeException("Failed to insert the car.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteCar(long id) throws CarExceptionNoId {
        LOG.info("delete car");
        String sqlString = "DELETE FROM CarModel WHERE CarModel.id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlQuery = connection.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS)) {
            sqlQuery.setLong(1, id);
            int affectedRows = sqlQuery.executeUpdate();
            if (affectedRows == 0) {
                throw new CarExceptionNoId("There is no such an id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCar(CarModel car) throws CarExceptionNoId {
        LOG.info("update car");
        String sqlString = "UPDATE CarModel SET car_name = ?, brand = ?, car_year = ?, price = ?, uploadDate = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlQuery = connection.prepareStatement(sqlString)) {
            sqlQuery.setString(1, car.getName());
            sqlQuery.setString(2, car.getBrand());
            sqlQuery.setInt(3, car.getYear());
            sqlQuery.setDouble(4, car.getPrice());
            sqlQuery.setDate(5, car.getUploadDate());
            sqlQuery.setLong(6, car.getId());

            int affectedRows = sqlQuery.executeUpdate();

            if (affectedRows == 0) {
                throw new CarExceptionNoId("There is no such an id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public CarModel readCar(long id) throws CarExceptionNoId {
        LOG.info("read car");
        String sqlString = "SELECT * FROM CarModel WHERE CarModel.id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlQuery = connection.prepareStatement(sqlString)) {
            sqlQuery.setLong(1, id);

            try (ResultSet resultSet = sqlQuery.executeQuery()) {
                if (resultSet.next()) {
                    CarModel car = new CarModel();
                    car.setId(resultSet.getLong("id"));
                    car.setName(resultSet.getString("car_name"));
                    car.setBrand(resultSet.getString("brand"));
                    car.setYear(resultSet.getInt("car_year"));
                    car.setPrice(resultSet.getDouble("price"));
                    car.setUploadDate(resultSet.getDate("uploadDate"));

                    return car;
                } else {
                    throw new CarExceptionNoId("There is no such an id");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<CarModel> getAllCars() {
        LOG.info("get all cars");
        List<CarModel> carList = new ArrayList<>();
        String sqlString = "SELECT * FROM CarModel";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlQuery = connection.prepareStatement(sqlString);
             ResultSet resultSet = sqlQuery.executeQuery()) {
            while (resultSet.next()) {
                CarModel car = new CarModel();
                car.setId(resultSet.getLong("id"));
                car.setName(resultSet.getString("car_name"));
                car.setBrand(resultSet.getString("brand"));
                car.setYear(resultSet.getInt("car_year"));
                car.setPrice(resultSet.getDouble("price"));
                car.setUploadDate(resultSet.getDate("uploadDate"));

                carList.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carList;
    }

}
