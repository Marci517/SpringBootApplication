package edu.bbte.idde.bmim2214.business.memjdbcservices;

import edu.bbte.idde.bmim2214.business.CarService;
import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.dao.CarDao;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Profile("!jpa")
public class CarServiceImp implements CarService {

    private final CarDao carDao;

    @Autowired
    public CarServiceImp(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public CarModel createCar(CarModel car) throws CarExceptionDatabase, CarExceptionDates {
        log.info("add car");
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        if (car.getYear() > currentYear) {
            throw new CarExceptionDates("The year should be between 1900 and the current year.");
        } else {
            carDao.createCar(car);
            return car;
        }

    }

    @Override
    public void deleteById(long id) throws CarExceptionDatabase {
        log.info("delete car");
        CarModel car = carDao.findById(id);
        if (car == null) {
            throw new CarExceptionDatabase("Car not found with id: " + id);
        }
        carDao.deleteById(id);

    }

    @Override
    public CarModel updateCar(CarModel car) throws CarExceptionDatabase, CarExceptionDates {
        log.info("update car");
        CarModel carToCheck = carDao.findById(car.getId());
        if (carToCheck == null) {
            throw new CarExceptionDatabase("Car not found with id: " + car.getId());
        }
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        if (car.getYear() > currentYear) {
            throw new CarExceptionDates("The year should be between 1900 and the current year.");
        } else {
            carDao.updateCar(car);
            return car;
        }
    }

    @Override
    public CarModel findById(long id) throws CarExceptionDatabase {
        log.info("get car");
        CarModel car = carDao.findById(id);
        if (car == null) {
            throw new CarExceptionDatabase("Car not found with id: " + id);
        }
        return car;
    }

    @Override
    public List<CarModel> findAll() throws CarExceptionDatabase {
        log.info("get all cars");
        return carDao.findAll();
    }

    @Override
    public List<CarModel> getAllCarsFromSpecYear(int year) throws CarExceptionDatabase {
        log.info("get all cars from a specific year");
        return carDao.getAllCarsFromSpecYear(year);
    }


}
