package edu.bbte.idde.bmim2214.dataaccess.dao;

import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CarDao {

    void createCar(CarModel car) throws CarExceptionDatabase;

    void deleteById(long id) throws CarExceptionDatabase;

    void updateCar(CarModel car) throws CarExceptionDatabase;

    CarModel findById(long id) throws CarExceptionDatabase;

    List<CarModel> findAll(Specification<CarModel> spec);
}
