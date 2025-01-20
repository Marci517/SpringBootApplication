package edu.bbte.idde.bmim2214.business;

import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CarExtraService {
    Page<CarExtra> getAllExtras(long carId, Pageable pageable) throws CarExceptionDatabase;

    CarExtra addCarExtra(long carId, CarExtra carExtra) throws CarExceptionDatabase;

    void deleteCarExtra(long carId, long extraId) throws CarExceptionDatabase;

}
