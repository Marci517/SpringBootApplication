package edu.bbte.idde.bmim2214.business;

import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;

import java.util.List;

public interface CarService {
    CarModel createCar(CarModel car) throws CarExceptionDatabase, CarExceptionDates;

    void deleteById(long id) throws CarExceptionDatabase;

    CarModel updateCar(CarModel car) throws CarExceptionDatabase, CarExceptionDates;

    CarModel findById(long id) throws CarExceptionDatabase;

    List<CarModel> findAll() throws CarExceptionDatabase;

    List<CarModel> getAllCarsFromSpecYear(int year) throws CarExceptionDatabase;

}
