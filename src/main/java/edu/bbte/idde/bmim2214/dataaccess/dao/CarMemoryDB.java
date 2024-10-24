package edu.bbte.idde.bmim2214.dataaccess.dao;

import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionNoId;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarMemoryDB implements CarDao {
    private final Map<Long, CarModel> carDatabase = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(CarMemoryDB.class);
    private long currentId;

    @Override
    public void createCar(CarModel car) {
        log.info("create car");
        currentId++;
        car.setId(currentId);
        carDatabase.put(currentId, car);
    }

    @Override
    public void deleteCar(long id) throws CarExceptionNoId {
        log.info("delete car");
        if (carDatabase.containsKey(id)) {
            carDatabase.remove(id);
        } else {
            throw new CarExceptionNoId("There is no such an id");
        }

    }

    @Override
    public void updateCar(CarModel car) throws CarExceptionNoId {
        log.info("update car");
        long id = car.getId();
        if (carDatabase.containsKey(id)) {
            carDatabase.put(id, car);
        } else {
            throw new CarExceptionNoId("There is no such an id");
        }
    }

    @Override
    public CarModel readCar(long id) throws CarExceptionNoId {
        log.info("read car");
        if (carDatabase.containsKey(id)) {
            return carDatabase.get(id);
        } else {
            throw new CarExceptionNoId("There is no such an id");
        }

    }

    @Override
    public List<CarModel> getAllCars() {
        log.info("get all cars");
        return new ArrayList<>(carDatabase.values());
    }
}
