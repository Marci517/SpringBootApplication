package edu.bbte.idde.bmim2214.dataaccess;

import edu.bbte.idde.bmim2214.dataaccess.Exceptions.CarExceptionNoId;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarMemoryDB implements CarDao {
    private Map<Long, CarModel> carDatabase = new HashMap<>();
    private long currentId = 0;

    @Override
    public void createCar(CarModel car) {
        currentId++;
        car.setId(currentId);
        carDatabase.put(currentId, car);
    }

    @Override
    public void deleteCar(long id) throws CarExceptionNoId {
        if (carDatabase.containsKey(id)) {
            carDatabase.remove(id);
        } else {
            throw new CarExceptionNoId("There is no such an id");
        }

    }

    @Override
    public void updateCar(CarModel car) throws CarExceptionNoId {
        long id = car.getId();
        if (carDatabase.containsKey(id)) {
            carDatabase.put(id, car);
        } else {
            throw new CarExceptionNoId("There is no such an id");
        }
    }

    @Override
    public CarModel readCar(long id) throws CarExceptionNoId {
        if (carDatabase.containsKey(id)) {
            return carDatabase.get(id);
        } else {
            throw new CarExceptionNoId("There is no such an id");
        }

    }

    @Override
    public List<CarModel> getAllCars() {
        return new ArrayList<>(carDatabase.values());
    }
}
