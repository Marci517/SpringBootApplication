package edu.bbte.idde.bmim2214.controller;

import edu.bbte.idde.bmim2214.business.CarServiceImp;
import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import edu.bbte.idde.bmim2214.dataaccess.model.dto.in.CarDtoIn;
import edu.bbte.idde.bmim2214.dataaccess.model.dto.out.CarDtoOut;
import edu.bbte.idde.bmim2214.dataaccess.model.dto.out.CarShortDtoOut;
import edu.bbte.idde.bmim2214.dataaccess.model.mapper.CarMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/spring")
public class Controller {
    private final CarMapper mapper;
    private final CarServiceImp service;

    @Autowired
    public Controller(CarMapper mapper, CarServiceImp service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping("/car/{id}")
    public CarDtoOut getCar(@PathVariable String id) throws CarExceptionDatabase {
        log.info("GET/car/id");
        try {
            CarModel car = service.getCar(Integer.parseInt(id));
            return mapper.carToDto(car);
        } catch (CarExceptionDatabase | NumberFormatException e) {
            throw new CarExceptionDatabase("Car not found", e);
        }
    }

    @GetMapping("/cars")
    public List<CarShortDtoOut> getAllCars() throws CarExceptionDatabase {
        log.info("GET/cars");
        try {
            return (List<CarShortDtoOut>) mapper.carsToDtos(service.getAllCars());
        } catch (CarExceptionDatabase e) {
            throw new CarExceptionDatabase("Failed to get all cars", e);
        }
    }

    @GetMapping("/cars/search/{year}")
    public List<CarShortDtoOut> getAllCarsFromSpecYear(@PathVariable String year) throws CarExceptionDatabase {
        log.info("GET/cars/search/{year}");
        try {
            return (List<CarShortDtoOut>) mapper.carsToDtos(service.getAllCarsFromSpecYear(Integer.parseInt(year)));
        } catch (CarExceptionDatabase | NumberFormatException e) {
            throw new CarExceptionDatabase("Failed to get all cars from specific year", e);
        }
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable String id) throws CarExceptionDatabase {
        log.info("DELETE/car/id");
        try {
            service.deleteCar(Integer.parseInt(id));
            return ResponseEntity.ok("Car deleted successfully");
        } catch (CarExceptionDatabase | NumberFormatException e) {
            throw new CarExceptionDatabase("Car not found", e);
        }
    }

    @PostMapping("/car")
    public ResponseEntity<String> addCar(@RequestBody @Valid CarDtoIn car)
            throws CarExceptionDatabase, CarExceptionDates {
        log.info("POST/car");
        try {
            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            Date today = new Date(year - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
            car.setUploadDate(today);
            service.addCar(mapper.dtoToCar(car));
            return ResponseEntity.ok("Car added successfully");
        } catch (CarExceptionDatabase | IllegalArgumentException e) {
            throw new CarExceptionDatabase("Failed to add car", e);
        } catch (CarExceptionDates e) {
            throw new CarExceptionDates("Dates should be in the past or present", e);
        }

    }

    @PutMapping("/car")
    public ResponseEntity<String> updateCar(@RequestBody @Valid CarDtoIn car)
            throws CarExceptionDatabase, CarExceptionDates {
        log.info("PUT/car");
        try {
            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            Date today = new Date(year - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
            car.setUploadDate(today);
            service.updateCar(mapper.dtoToCar(car));
            return ResponseEntity.ok("Car updated successfully");
        } catch (CarExceptionDatabase | IllegalArgumentException e) {
            throw new CarExceptionDatabase("Failed to update car", e);
        } catch (CarExceptionDates e) {
            throw new CarExceptionDates("Dates should be in the past or present", e);
        }

    }

}
