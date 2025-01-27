package edu.bbte.idde.bmim2214.controller;

import edu.bbte.idde.bmim2214.business.CarServiceImp;
import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.controller.dto.outdto.CarVeryShortDtoOut;
import edu.bbte.idde.bmim2214.controller.exceptions.ExceptionFull;
import edu.bbte.idde.bmim2214.controller.mapper.CarExtraMapper;
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
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/cars")
public class CarController {
    private final CarMapper mapper;
    private final CarExtraMapper extraMapper;
    private final CarServiceImp service;

    @Autowired
    public CarController(CarMapper mapper, CarServiceImp service, CarExtraMapper carExtraMapper) {
        this.mapper = mapper;
        this.service = service;
        this.extraMapper = carExtraMapper;
    }

    @GetMapping("/{id}")
    public CarDtoOut getCar(@PathVariable Long id,
                            @RequestParam String full)
            throws CarExceptionDatabase, NumberFormatException, ExceptionFull {
        log.info("GET/cars/id");
        if ("yes".equals(full)) {
            CarModel car = service.findById(id);
            return mapper.carToDto(car);
        }
        if ("no".equals(full)) {
            CarVeryShortDtoOut carVeryShortDtoOut = new CarVeryShortDtoOut();
            CarModel car = service.findById(id);
            carVeryShortDtoOut.setBrand(car.getBrand());
            carVeryShortDtoOut.setPrice(car.getPrice());
            carVeryShortDtoOut.setName(car.getName());
            carVeryShortDtoOut.setId(id);
            carVeryShortDtoOut.setExtras(extraMapper.carExtrasToDtos(car.getExtras()));
            return mapper.carToDto(car);
        }
        throw new ExceptionFull("Wrong value for full parameter");

    }


    @GetMapping
    public List<CarShortDtoOut> getAllCarsFromSpecYear(@RequestParam(value = "year", required = false) String year)
            throws CarExceptionDatabase, NumberFormatException {
        log.info("GET/cars/{year}");
        if (year != null) {
            return (List<CarShortDtoOut>) mapper.carsToDtos(service.getAllCarsFromSpecYear(Integer.parseInt(year)));
        } else {
            return (List<CarShortDtoOut>) mapper.carsToDtos(service.findAll());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) throws CarExceptionDatabase, NumberFormatException {
        log.info("DELETE/cars/{id}");
        service.deleteById(id);
    }

    @PostMapping
    public CarDtoOut createCar(@RequestBody @Valid CarDtoIn car)
            throws CarExceptionDatabase, CarExceptionDates {
        log.info("POST/cars");
        LocalDate localDate = LocalDate.now();
        CarModel carToSet = mapper.dtoToCar(car);
        int year = localDate.getYear();
        Date today = new Date(year - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        carToSet.setUploadDate(today);
        return mapper.carToDto(service.createCar(carToSet));

    }

    @PutMapping("/{id}")
    public CarDtoOut updateCar(@PathVariable Long id, @RequestBody @Valid CarDtoIn car)
            throws CarExceptionDatabase, CarExceptionDates, NumberFormatException {
        log.info("PUT/car/id");

        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        CarModel carToSet = mapper.dtoToCar(car);
        Date today = new Date(year - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        carToSet.setUploadDate(today);
        carToSet.setId(id);
        return mapper.carToDto(service.updateCar(carToSet));
    }

}
