package edu.bbte.idde.bmim2214.controller;

import edu.bbte.idde.bmim2214.business.CarExtraServiceImp;
import edu.bbte.idde.bmim2214.controller.dto.indto.CarExtraDtoIn;
import edu.bbte.idde.bmim2214.controller.dto.outdto.CarExtraDtoOut;
import edu.bbte.idde.bmim2214.controller.mapper.CarExtraMapper;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile("jpa")
@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "http://192.168.1.6:3000"})
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
    public List<CarExtraDtoOut> getAllExtras(@PathVariable Long carId,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "5") int size,
                                             @RequestParam(defaultValue = "description") String sortBy,
                                             @RequestParam(defaultValue = "asc") String sortDirection,
                                             HttpServletResponse response)
            throws CarExceptionDatabase, NumberFormatException {
        log.info("get all extras");

        String sortByDef = "description".equalsIgnoreCase(sortBy) ? sortBy : "description";

        Sort sort = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortByDef).descending()
                : Sort.by(sortByDef).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CarExtra> extraPage = carExtraService.getAllExtras(carId, pageable);

        response.setHeader("X-Total-Count", String.valueOf(extraPage.getTotalElements()));

        return (List<CarExtraDtoOut>) carExtraMapper
                .carExtrasToDtos(extraPage.getContent());
    }

    @PostMapping
    public CarExtraDtoOut addCarExtra(@PathVariable Long carId, @RequestBody CarExtraDtoIn carExtra)
            throws CarExceptionDatabase, NumberFormatException {
        log.info("add an extra");
        return carExtraMapper.extraToDto(carExtraService
                .addCarExtra(carId, carExtraMapper.dtoToExtra(carExtra)));
    }

    @DeleteMapping("/{extraId}")
    public void deleteCarExtra(@PathVariable Long carId, @PathVariable Long extraId)
            throws CarExceptionDatabase, NumberFormatException {
        log.info("delete an extra");
        carExtraService.deleteCarExtra(carId, extraId);
    }
}

