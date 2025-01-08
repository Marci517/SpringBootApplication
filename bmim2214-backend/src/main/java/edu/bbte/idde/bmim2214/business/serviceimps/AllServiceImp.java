package edu.bbte.idde.bmim2214.business.serviceimps;

import java.time.LocalDate;
import java.util.List;

import edu.bbte.idde.bmim2214.business.AllService;
import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.dao.AllDao;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.factory.AbstractDaoFactory;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllServiceImp implements AllService {
    private final AllDao carDao;
    private static final Logger log = LoggerFactory.getLogger(AllServiceImp.class);

    public AllServiceImp() {
        AbstractDaoFactory abstractDaoFactory = AbstractDaoFactory.getInstance();
        this.carDao = abstractDaoFactory.getCarDao();
    }

    public AllServiceImp(AllDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public void addCar(CarModel car) throws CarExceptionDates, CarExceptionDatabase {
        log.info("add car");
        if (isOk(car)) {
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();

            if (car.getYear() < 1900 || car.getYear() > currentYear) {
                throw new CarExceptionDates("The year should be between 1900 and the current year.");
            } else {
                carDao.createCar(car);
            }
        } else {
            throw new IllegalArgumentException("Wrong parameters");
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
        if (isOk(car)) {
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();

            if (car.getYear() < 1900 || car.getYear() > currentYear) {
                throw new CarExceptionDates("The year should be between 1900 and the current year.");
            } else {
                carDao.updateCar(car);
            }
        } else {
            throw new IllegalArgumentException("Wrong parameters");
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

    private boolean isOk(CarModel car) {
        return car.getPrice() > 0 && car.getBrand() != null && car.getName() != null
                && !car.getBrand().isEmpty() && !car.getName().isEmpty();
    }

    @Override
    public List<CarExtra> getAllExtras(long carId) throws CarExceptionDatabase {
        log.info("get all extras for a car");
        return carDao.getAllExtras(carId);
    }

    @Override
    public void addCarExtra(long carId, CarExtra carExtra) throws CarExceptionDatabase {
        log.info("add an extra");
        carDao.addCarExtra(carId, carExtra);
    }

    @Override
    public void deleteCarExtra(long carId, long extraId) throws CarExceptionDatabase {
        log.info("delete an extra from a car");
        carDao.deleteCarExtra(carId, extraId);
    }
}
