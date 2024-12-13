package edu.bbte.idde.bmim2214.dataaccess.dao;

import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
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
public class CarMemoryDB implements CarDao {
    private final Map<Long, CarModel> carDatabase = new ConcurrentHashMap<>();
    private long currentId;

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
    public List<CarModel> getAllCarsFromSpecYear(int year) {
        log.info("get all cars from specific year");
        return carDatabase.values().stream()
                .filter(car -> car.getYear() == year)
                .collect(Collectors.toList());
    }
}
