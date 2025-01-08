package edu.bbte.idde.bmim2214.dataaccess.dao;

import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Profile("memory")
@Repository
@Slf4j
public class CarMemoryDB implements CarDao, CarExtraDao {
    private final Map<Long, CarModel> carDatabase = new ConcurrentHashMap<>();
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
    public void deleteById(long id) throws CarExceptionDatabase {
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
    public CarModel findById(long id) throws CarExceptionDatabase {
        log.info("read car");
        if (carDatabase.containsKey(id)) {
            return carDatabase.get(id);
        } else {
            throw new CarExceptionDatabase("There is no such an id");
        }

    }

    @Override
    public List<CarModel> findAll() {
        log.info("get all cars");
        return new ArrayList<>(carDatabase.values());
    }

    @Override
    public List<CarModel> getAllCarsFromSpecYear(int year) {
        log.info("get all cars from specific year");
        return carDatabase.values().stream()
                .filter(car -> car.getYear() == year)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarExtra> getAllExtras(long carId) throws CarExceptionDatabase {
        log.info("get all extras for a car");
        if (carDatabase.containsKey(carId)) {
            return carDatabase.get(carId).getExtras();
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
            List<CarExtra> carExtraList = carDatabase.get(carId).getExtras();
            carExtraList.add(carExtra);
            carDatabase.get(carId).setExtras(carExtraList);
        } else {
            throw new CarExceptionDatabase("There is no such an id");
        }
    }

    @Override
    public void deleteCarExtra(long carId, long extraId) throws CarExceptionDatabase {
        log.info("delete extra from a car");
        if (carDatabase.containsKey(carId)) {
            List<CarExtra> carExtraList = carDatabase.get(carId).getExtras();
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
                carDatabase.get(carId).setExtras(carExtraList);
            }
        } else {
            throw new CarExceptionDatabase("There is no such an id");
        }
    }
}

