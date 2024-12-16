package edu.bbte.idde.bmim2214.controller;

import edu.bbte.idde.bmim2214.business.CarServiceImp;
import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import edu.bbte.idde.bmim2214.controller.dto.indto.CarDtoIn;
import edu.bbte.idde.bmim2214.controller.dto.outdto.CarDtoOut;
import edu.bbte.idde.bmim2214.controller.dto.outdto.CarShortDtoOut;
import edu.bbte.idde.bmim2214.controller.mapper.CarMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/cars")
public class CarController {
    private final CarMapper mapper;
    private final CarServiceImp service;

    @Autowired
    public CarController(CarMapper mapper, CarServiceImp service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
    public CarDtoOut getCar(@PathVariable String id) throws CarExceptionDatabase, NumberFormatException {
        log.info("GET/cars/id");
        CarModel car = service.getCar(Integer.parseInt(id));
        return mapper.carToDto(car);
    }


    @GetMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public List<CarShortDtoOut> getAllCarsFromSpecYear(@RequestParam(value = "year", required = false) String year)
            throws CarExceptionDatabase, NumberFormatException {
        log.info("GET/cars/{year}");
        if (year != null) {
            return (List<CarShortDtoOut>) mapper.carsToDtos(service.getAllCarsFromSpecYear(Integer.parseInt(year)));
        } else {
            return (List<CarShortDtoOut>) mapper.carsToDtos(service.getAllCars());
        }
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
    public void deleteCar(@PathVariable String id) throws CarExceptionDatabase, NumberFormatException {
        log.info("DELETE/cars/{id}");
        service.deleteCar(Integer.parseInt(id));
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public CarDtoOut addCar(@RequestBody @Valid CarDtoIn car)
            throws CarExceptionDatabase, CarExceptionDates {
        log.info("POST/cars");
        LocalDate localDate = LocalDate.now();
        CarModel carToSet = mapper.dtoToCar(car);
        int year = localDate.getYear();
        Date today = new Date(year - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        carToSet.setUploadDate(today);
        return mapper.carToDto(service.addCar(carToSet));

    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
    public CarDtoOut updateCar(@PathVariable String id, @RequestBody @Valid CarDtoIn car)
            throws CarExceptionDatabase, CarExceptionDates, NumberFormatException {
        log.info("PUT/car/id");

        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        CarModel carToSet = mapper.dtoToCar(car);
        Date today = new Date(year - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        carToSet.setUploadDate(today);
        carToSet.setId(Long.parseLong(id));
        return mapper.carToDto(service.updateCar(carToSet));
    }

}
