package edu.bbte.idde.bmim2214.business;

import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.CarDao;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionNoId;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;

import java.time.LocalDate;
import java.util.List;

public class CarServiceImp implements CarService {
    private CarDao carDao;

    public CarServiceImp(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public void addCar(CarModel car) throws CarExceptionDates {
        if (car.getPrice() > 0 && !car.getBrand().isEmpty()
                && !car.getName().isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();

            if (car.getYear() < 1900 || car.getYear() > currentYear) {
                throw new CarExceptionDates("The year should be between 1900 and the current year.");
            } else {
                carDao.createCar(car);
            }
        } else {
            throw new IllegalArgumentException("Wrong paramaters");
        }
    }

    @Override
    public void deleteCar(int id) throws CarExceptionNoId {
        carDao.deleteCar(id);

    }

    @Override
    public void updateCar(CarModel car) throws CarExceptionNoId, CarExceptionDates {
        if (car.getPrice() > 0 && !car.getBrand().isEmpty()
                && !car.getName().isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();

            if (car.getYear() < 1900 || car.getYear() > currentYear) {
                throw new CarExceptionDates("The year should be between 1900 and the current year.");
            } else {
                carDao.updateCar(car);
            }
        } else {
            throw new IllegalArgumentException("Wrong paramaters");
        }
    }

    @Override
    public CarModel getCar(int id) throws CarExceptionNoId {
        return carDao.readCar(id);
    }

    @Override
    public List<CarModel> getAllCars() {
        return carDao.getAllCars();
    }
}
