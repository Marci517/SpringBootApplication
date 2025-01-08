package edu.bbte.idde.bmim2214.controller.memjdbcontrollers;

import edu.bbte.idde.bmim2214.business.memjdbcservices.CarExtraServiceImp;
import edu.bbte.idde.bmim2214.controller.dto.indto.CarExtraDtoIn;
import edu.bbte.idde.bmim2214.controller.dto.outdto.CarExtraDtoOut;
import edu.bbte.idde.bmim2214.controller.mapper.CarExtraMapper;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile("!jpa")
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@RequestMapping("/cars/{carId}/extras")
public class CarExtraController {

    private final CarExtraServiceImp carExtraService;
    private final CarExtraMapper carExtraMapper;

    @Autowired
    public CarExtraController(CarExtraServiceImp carExtraService, CarExtraMapper carExtraMapper) {
        this.carExtraService = carExtraService;
        this.carExtraMapper = carExtraMapper;
    }

    @GetMapping
    public List<CarExtraDtoOut> getAllExtras(@PathVariable String carId)
            throws CarExceptionDatabase, NumberFormatException {
        log.info("get all extras");
        return (List<CarExtraDtoOut>) carExtraMapper
                .carExtrasToDtos(carExtraService.getAllExtras(Long.parseLong(carId)));
    }

    @PostMapping
    public CarExtraDtoOut addCarExtra(@PathVariable String carId, @RequestBody CarExtraDtoIn carExtra)
            throws CarExceptionDatabase, NumberFormatException {
        log.info("add an extra");
        return carExtraMapper.extraToDto(carExtraService
                .addCarExtra(Long.parseLong(carId), carExtraMapper.dtoToExtra(carExtra)));
    }

    @DeleteMapping("/{extraId}")
    public void deleteCarExtra(@PathVariable String carId, @PathVariable String extraId)
            throws CarExceptionDatabase, NumberFormatException {
        log.info("delete an extra");
        carExtraService.deleteCarExtra(Long.parseLong(carId), Long.parseLong(extraId));
    }
}

