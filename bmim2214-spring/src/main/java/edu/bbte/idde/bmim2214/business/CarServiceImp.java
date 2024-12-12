package edu.bbte.idde.bmim2214.business;

import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.dao.CarDao;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class CarServiceImp implements CarService {

    private final CarDao carDao;

    @Autowired
    public CarServiceImp(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public void addCar(CarModel car) throws CarExceptionDatabase, CarExceptionDates {
        log.info("add car");
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        if (car.getYear() > currentYear) {
            throw new CarExceptionDates("The year should be between 1900 and the current year.");
        } else {
            carDao.createCar(car);
        }
    }

    @Override
    public void deleteCar(int id) throws CarExceptionDatabase {
        log.info("delete car");
        carDao.deleteCar(id);

    }

    @Override
    public void updateCar(CarModel car) throws CarExceptionDatabase, CarExceptionDates {
        log.info("update car");
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        if (car.getYear() > currentYear) {
            throw new CarExceptionDates("The year should be between 1900 and the current year.");
        } else {
            carDao.updateCar(car);
        }

    }

    @Override
    public CarModel getCar(int id) throws CarExceptionDatabase {
        log.info("get car");
        return carDao.readCar(id);
    }

    @Override
    public List<CarModel> getAllCars() throws CarExceptionDatabase {
        log.info("get all cars");
        return carDao.getAllCars();
    }

    @Override
    public List<CarModel> getAllCarsFromSpecYear(int year) throws CarExceptionDatabase {
        log.info("get all cars from a specific year");
        return carDao.getAllCarsFromSpecYear(year);
    }


}
