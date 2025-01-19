package edu.bbte.idde.bmim2214.business;

import edu.bbte.idde.bmim2214.dataaccess.repos.CarExtraRepo;
import edu.bbte.idde.bmim2214.dataaccess.repos.CarModelRepo;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;


@Profile("jpa")
@Slf4j
@Service
public class CarExtraServiceImp implements CarExtraService {

    private final CarModelRepo carModelRepo;
    private final CarExtraRepo carExtraRepo;

    @Autowired
    public CarExtraServiceImp(CarModelRepo carModelRepo, CarExtraRepo carExtraRepo) {
        this.carModelRepo = carModelRepo;
        this.carExtraRepo = carExtraRepo;
    }

    @Override
    @Cacheable(value = "carExtras", key = "#carId")
    public List<CarExtra> getAllExtras(long carId) throws CarExceptionDatabase {
        log.info("get all extras");
        CarModel car = carModelRepo.findById(carId)
                .orElseThrow(() -> new CarExceptionDatabase("Car not found with id: " + carId));
        List<CarExtra> extras = car.getExtras();
        log.info(String.valueOf(extras.size()));
        return car.getExtras();
    }

    @Override
    @CacheEvict(value = "carExtras", key = "#carId")
    public CarExtra addCarExtra(long carId, CarExtra carExtra) throws CarExceptionDatabase {
        log.info("add an extra");
        CarModel car = carModelRepo.findById(carId)
                .orElseThrow(() -> new CarExceptionDatabase("Car not found with id: " + carId));
        carExtra.setCar(car);
        return carExtraRepo.save(carExtra);
    }

    @Override
    @CacheEvict(value = "carExtras", key = "#carId")
    public void deleteCarExtra(long carId, long extraId) throws CarExceptionDatabase {
        log.info("delete an extra");
        CarModel car = carModelRepo.findById(carId)
                .orElseThrow(() -> new CarExceptionDatabase("Car not found with id: " + carId));
        car.getExtras().removeIf(extra -> extra.getId() == extraId);
        carModelRepo.save(car);
    }

}

