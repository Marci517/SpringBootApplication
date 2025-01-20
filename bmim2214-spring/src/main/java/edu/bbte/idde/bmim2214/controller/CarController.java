package edu.bbte.idde.bmim2214.controller;

import edu.bbte.idde.bmim2214.business.CarServiceImp;
import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.controller.dto.indto.CarModelFilter;
import edu.bbte.idde.bmim2214.controller.dto.outdto.CarShortDtoOut;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import edu.bbte.idde.bmim2214.controller.dto.indto.CarDtoIn;
import edu.bbte.idde.bmim2214.controller.dto.outdto.CarDtoOut;
import edu.bbte.idde.bmim2214.controller.mapper.CarMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "http://192.168.1.6:3000"})
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
    public CarDtoOut getCar(@PathVariable Long id) throws CarExceptionDatabase, NumberFormatException {
        log.info("GET/cars/id");
        CarModel car = service.findById(id);
        return mapper.carToDto(car);
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

    @GetMapping
    public List<CarShortDtoOut> getFilteredCars(CarModelFilter filter,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "2") int size,
                                                @RequestParam(defaultValue = "name") String sortBy,
                                                @RequestParam(defaultValue = "asc") String sortDirection,
                                                HttpServletResponse response) {
        String defaultSortBy;
        if ("name".equalsIgnoreCase(sortBy)
                || "year".equalsIgnoreCase(sortBy)
                || "price".equalsIgnoreCase(sortBy)
                || "brand".equalsIgnoreCase(sortBy)) {
            defaultSortBy = sortBy;
        } else {
            defaultSortBy = "name";
        }
        Sort sort = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.by(defaultSortBy).descending() : Sort.by(defaultSortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CarModel> carPage = service.getFilteredCars(filter, pageable);

        response.setHeader("X-Total-Count", String.valueOf(carPage.getTotalElements()));

        log.info("GET/cars?params");
        return (List<CarShortDtoOut>) mapper.carsToDtos(carPage.getContent());
    }

}
