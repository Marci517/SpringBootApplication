package edu.bbte.idde.bmim2214.business.serviceimps;

import edu.bbte.idde.bmim2214.business.CarExtraService;
import edu.bbte.idde.bmim2214.dataaccess.dao.AllDao;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.factory.AbstractDaoFactory;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CarExtraServiceImp implements CarExtraService {

    private final AllDao carDao;
    private static final Logger log = LoggerFactory.getLogger(AllServiceImp.class);

    public CarExtraServiceImp() {
        AbstractDaoFactory abstractDaoFactory = AbstractDaoFactory.getInstance();
        this.carDao = abstractDaoFactory.getCarDao();
    }

    @Override
    public List<CarExtra> getAllExtras(long carId) throws CarExceptionDatabase {
        log.info("get all extras for a car");
        return carDao.getAllExtras(carId);
    }

    @Override
    public void addCarExtra(long carId, CarExtra carExtra) throws CarExceptionDatabase {
        log.info("add an extra");
        carDao.addCarExtra(carId, carExtra);
    }

    @Override
    public void deleteCarExtra(long carId, long extraId) throws CarExceptionDatabase {
        log.info("delete an extra from a car");
        carDao.deleteCarExtra(carId, extraId);
    }
}
