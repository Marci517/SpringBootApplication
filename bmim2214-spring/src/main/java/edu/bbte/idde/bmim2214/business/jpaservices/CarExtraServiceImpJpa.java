package edu.bbte.idde.bmim2214.business.jpaservices;

import edu.bbte.idde.bmim2214.business.CarExtraService;
import edu.bbte.idde.bmim2214.dataaccess.dao.repo.CarExtraRepo;
import edu.bbte.idde.bmim2214.dataaccess.dao.repo.CarModelRepo;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Profile("jpa")
@Slf4j
@Service
public class CarExtraServiceImpJpa implements CarExtraService {

    private final CarModelRepo carModelRepo;
    private final CarExtraRepo carExtraRepo;

    @Autowired
    public CarExtraServiceImpJpa(CarModelRepo carModelRepo, CarExtraRepo carExtraRepo) {
        this.carModelRepo = carModelRepo;
        this.carExtraRepo = carExtraRepo;
    }

    @Override
    public List<CarExtra> getAllExtras(long carId) throws CarExceptionDatabase {
        log.info("get all extras");
        Optional<CarModel> car = carModelRepo.findById(carId);
        CarModel carToCheck = car.orElseThrow(() -> new CarExceptionDatabase("Car not found with id: " + carId));
        return carToCheck.getExtras();
    }


    @Override
    public CarExtra addCarExtra(long carId, CarExtra carExtra) throws CarExceptionDatabase {
        log.info("add an extra");
        Optional<CarModel> carOpt = carModelRepo.findById(carId);
        CarModel car = carOpt.orElseThrow(() -> new CarExceptionDatabase("Car not found with id: " + carId));

        carExtra.setCar(car);
        return carExtraRepo.save(carExtra);
    }

    @Override
    public void deleteCarExtra(long carId, long extraId) throws CarExceptionDatabase {
        log.info("delete an extra");
        Optional<CarModel> carOpt = carModelRepo.findById(carId);
        CarModel car = carOpt.orElseThrow(() -> new CarExceptionDatabase("Car not found with id: " + carId));
        car.getExtras().removeIf(extra -> extra.getId() == extraId);
        carModelRepo.save(car);
    }

}

