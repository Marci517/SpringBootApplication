package edu.bbte.idde.bmim2214.business.jpaservices;

import edu.bbte.idde.bmim2214.business.CarService;
import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.dao.repo.CarModelRepo;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Profile("jpa")
public class CarServiceImpJpa implements CarService {

    private final CarModelRepo carModelRepo;

    @Autowired
    public CarServiceImpJpa(CarModelRepo carModelRepo) {
        this.carModelRepo = carModelRepo;
    }

    @Override
    public CarModel createCar(CarModel car) throws CarExceptionDatabase, CarExceptionDates {
        log.info("add car");
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        if (car.getYear() > currentYear) {
            throw new CarExceptionDates("The year should be between 1900 and the current year.");
        } else {
            carModelRepo.createCar(car);
            return car;
        }

    }

    @Override
    public void deleteById(long id) throws CarExceptionDatabase {
        log.info("delete car");
        Optional<CarModel> car = carModelRepo.findById(id);
        car.orElseThrow(() -> new CarExceptionDatabase("Car not found with id: " + id));
        carModelRepo.deleteById(id);

    }

    @Override
    public CarModel updateCar(CarModel car) throws CarExceptionDatabase, CarExceptionDates {
        log.info("update car");
        Optional<CarModel> carToCheck = carModelRepo.findById(car.getId());
        carToCheck.orElseThrow(() -> new CarExceptionDatabase("Car not found with id: " + car.getId()));
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        if (car.getYear() > currentYear) {
            throw new CarExceptionDates("The year should be between 1900 and the current year.");
        } else {
            carModelRepo.updateCar(car);
            return car;
        }
    }

    @Override
    public CarModel findById(long id) throws CarExceptionDatabase {
        log.info("get car");
        Optional<CarModel> carToCheck = carModelRepo.findById(id);
        return carToCheck.orElseThrow(() -> new CarExceptionDatabase("Car not found with id: " + id));
    }

    @Override
    public List<CarModel> findAll() throws CarExceptionDatabase {
        log.info("get all cars");
        return carModelRepo.findAll();
    }

    @Override
    public List<CarModel> getAllCarsFromSpecYear(int year) throws CarExceptionDatabase {
        log.info("get all cars from a specific year");
        return carModelRepo.getAllCarsFromSpecYear(year);
    }


}
