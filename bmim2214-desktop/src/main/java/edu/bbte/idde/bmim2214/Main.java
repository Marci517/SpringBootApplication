package edu.bbte.idde.bmim2214;

import edu.bbte.idde.bmim2214.dataaccess.dao.CarDao;
import edu.bbte.idde.bmim2214.dataaccess.factory.AbstractDaoFactory;
import edu.bbte.idde.bmim2214.business.*;

import edu.bbte.idde.bmim2214.presentation.CarFrontend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(Main.class);
        log.info("start");
        AbstractDaoFactory abstractDaoFactory = AbstractDaoFactory.getInstance();
        CarDao carDao = abstractDaoFactory.getCarDao();
        CarService carService = new CarServiceImp(carDao);
        CarFrontend carFrontend = new CarFrontend(carService);
        carFrontend.display();
    }
}