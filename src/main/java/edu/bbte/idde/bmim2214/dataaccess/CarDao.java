package edu.bbte.idde.bmim2214.dataaccess;

import edu.bbte.idde.bmim2214.dataaccess.Exceptions.CarExceptionNoId;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;

import java.util.List;

public interface CarDao {

    void createCar(CarModel car);

    void deleteCar(long id) throws CarExceptionNoId;

    void updateCar(CarModel car) throws CarExceptionNoId;

    CarModel readCar(long id) throws CarExceptionNoId;

    List<CarModel> getAllCars();


}
