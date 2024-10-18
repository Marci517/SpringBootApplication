package edu.bbte.idde.bmim2214.business;

import edu.bbte.idde.bmim2214.business.Exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.Exceptions.CarExceptionNoId;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;

import java.util.List;

public interface CarService {
    void addCar(CarModel car) throws CarExceptionDates, IllegalArgumentException;

    void deleteCar(int id) throws CarExceptionNoId;

    void updateCar(CarModel car) throws CarExceptionNoId, CarExceptionDates, IllegalArgumentException;

    CarModel getCar(int id) throws CarExceptionNoId;

    List<CarModel> getAllCars();

}
