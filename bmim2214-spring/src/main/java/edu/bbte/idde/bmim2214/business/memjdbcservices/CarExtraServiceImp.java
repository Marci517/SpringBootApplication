package edu.bbte.idde.bmim2214.business.memjdbcservices;

import edu.bbte.idde.bmim2214.business.CarExtraService;
import edu.bbte.idde.bmim2214.dataaccess.dao.CarDao;
import edu.bbte.idde.bmim2214.dataaccess.dao.CarExtraDao;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;


@Profile("!jpa")
@Slf4j
@Service
public class CarExtraServiceImp implements CarExtraService {

    private final CarDao carDao;
    private final CarExtraDao carExtraDao;

    @Autowired
    public CarExtraServiceImp(CarDao carDao, CarExtraDao carExtraDao) {
        this.carDao = carDao;
        this.carExtraDao = carExtraDao;
    }

    @Override
    public List<CarExtra> getAllExtras(long carId) throws CarExceptionDatabase {
        log.info("get all extras");
        CarModel car = carDao.findById(carId);
        if (car == null) {
            throw new CarExceptionDatabase("Car not found with id: " + carId);
        }
        return carExtraDao.getAllExtras(carId);
    }


    @Override
    public CarExtra addCarExtra(long carId, CarExtra carExtra) throws CarExceptionDatabase {
        log.info("add an extra");
        CarModel car = carDao.findById(carId);
        if (car == null) {
            throw new CarExceptionDatabase("Car not found with id: " + carId);
        }
        carExtraDao.addCarExtra(carId, carExtra);
        return carExtra;
    }

    @Override
    public void deleteCarExtra(long carId, long extraId) throws CarExceptionDatabase {
        log.info("delete an extra");
        CarModel car = carDao.findById(carId);
        if (car == null) {
            throw new CarExceptionDatabase("Car not found with id: " + carId);
        }
        carExtraDao.deleteCarExtra(carId, extraId);
    }

}

