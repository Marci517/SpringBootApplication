package edu.bbte.idde.bmim2214.business;

import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.controller.dto.indto.CarModelFilter;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {
    CarModel createCar(CarModel car) throws CarExceptionDatabase, CarExceptionDates;

    void deleteById(long id) throws CarExceptionDatabase;

    CarModel updateCar(CarModel car) throws CarExceptionDatabase, CarExceptionDates;

    CarModel findById(long id) throws CarExceptionDatabase;

    Page<CarModel> getFilteredCars(CarModelFilter spec, Pageable pageable);

}
