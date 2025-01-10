package edu.bbte.idde.bmim2214.business;

import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;

import java.util.List;

public interface CarExtraService {
    List<CarExtra> getAllExtras(long carId) throws CarExceptionDatabase;

    CarExtra addCarExtra(long carId, CarExtra carExtra) throws CarExceptionDatabase;

    void deleteCarExtra(long carId, long extraId) throws CarExceptionDatabase;

}
