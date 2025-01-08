package edu.bbte.idde.bmim2214;

import edu.bbte.idde.bmim2214.business.serviceimps.AllServiceImp;
import edu.bbte.idde.bmim2214.dataaccess.dao.AllDao;
import edu.bbte.idde.bmim2214.dataaccess.factory.AbstractDaoFactory;
import edu.bbte.idde.bmim2214.business.*;

import edu.bbte.idde.bmim2214.presentation.CarActions;
import edu.bbte.idde.bmim2214.presentation.CarFrontend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(Main.class);
        log.info("start");
        AbstractDaoFactory abstractDaoFactory = AbstractDaoFactory.getInstance();
        AllDao carDao = abstractDaoFactory.getCarDao();
        AllService carService = new AllServiceImp(carDao);
        CarActions carActions = new CarActions(carService);
        CarFrontend carFrontend = new CarFrontend(carActions);
        carFrontend.display();
    }
}