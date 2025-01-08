package edu.bbte.idde.bmim2214.dataaccess.dao;

import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;

import java.util.List;

public interface CarExtraDao {
    List<CarExtra> getAllExtras(long carId) throws CarExceptionDatabase;

    void addCarExtra(long carId, CarExtra carExtra) throws CarExceptionDatabase;

    void deleteCarExtra(long carId, long extraId) throws CarExceptionDatabase;
}
