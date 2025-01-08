package edu.bbte.idde.bmim2214.dataaccess.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarMemoryDB implements AllDao {
    private final Map<Long, CarModel> carDatabase = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(CarMemoryDB.class);
    private long currentId;
    private long currentExtraId;

    @Override
    public void createCar(CarModel car) {
        log.info("create car");
        currentId++;
        car.setId(currentId);
        carDatabase.put(currentId, car);
    }

    @Override
    public void deleteCar(long id) throws CarExceptionDatabase {
        log.info("delete car");
        if (carDatabase.containsKey(id)) {
            carDatabase.remove(id);
        } else {
            throw new CarExceptionDatabase("There is no such an id");
        }

    }

    @Override
    public void updateCar(CarModel car) throws CarExceptionDatabase {
        log.info("update car");
        long id = car.getId();
        if (carDatabase.containsKey(id)) {
            carDatabase.put(id, car);
        } else {
            throw new CarExceptionDatabase("There is no such an id");
        }
    }

    @Override
    public CarModel readCar(long id) throws CarExceptionDatabase {
        log.info("read car");
        if (carDatabase.containsKey(id)) {
            return carDatabase.get(id);
        } else {
            throw new CarExceptionDatabase("There is no such an id");
        }

    }

    @Override
    public List<CarModel> getAllCars() {
        log.info("get all cars");
        return new ArrayList<>(carDatabase.values());
    }

    @Override
    public List<CarExtra> getAllExtras(long carId) throws CarExceptionDatabase {
        log.info("get all extras for a car");
        if (carDatabase.containsKey(carId)) {
            return carDatabase.get(carId).getCarExtras();
        } else {
            throw new CarExceptionDatabase("There is no such an id");
        }
    }

    @Override
    public void addCarExtra(long carId, CarExtra carExtra) throws CarExceptionDatabase {
        log.info("add extra for a car");
        if (carDatabase.containsKey(carId)) {
            currentExtraId++;
            carExtra.setId(currentExtraId);
            List<CarExtra> carExtraList = carDatabase.get(carId).getCarExtras();
            carExtraList.add(carExtra);
            carDatabase.get(carId).setCarExtras(carExtraList);
        } else {
            throw new CarExceptionDatabase("There is no such an id");
        }
    }

    @Override
    public void deleteCarExtra(long carId, long extraId) throws CarExceptionDatabase {
        log.info("delete extra from a car");
        if (carDatabase.containsKey(carId)) {
            List<CarExtra> carExtraList = carDatabase.get(carId).getCarExtras();
            CarExtra carExtra = null;
            for (CarExtra i : carExtraList) {
                if (i.getId() == extraId) {
                    carExtra = i;
                    break;
                }
            }
            if (carExtra == null) {
                throw new CarExceptionDatabase("There is no such an extra id");
            } else {
                carExtraList.remove(carExtra);
                carDatabase.get(carId).setCarExtras(carExtraList);
            }
        } else {
            throw new CarExceptionDatabase("There is no such an id");
        }
    }
}
