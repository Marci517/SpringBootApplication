package edu.bbte.idde.bmim2214.dataaccess.dao;

import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;

import java.util.List;

public interface CarDao {

    void createCar(CarModel car) throws CarExceptionDatabase;

    void deleteCar(long id) throws CarExceptionDatabase;

    void updateCar(CarModel car) throws CarExceptionDatabase;

    CarModel readCar(long id) throws CarExceptionDatabase;

    List<CarModel> getAllCars() throws CarExceptionDatabase;


}
