package edu.bbte.idde.bmim2214.dataaccess.factory;

import edu.bbte.idde.bmim2214.dataaccess.dao.CarDao;
import edu.bbte.idde.bmim2214.dataaccess.dao.CarMemoryDB;

public class CarDaoMemoFactory extends AbstractDaoFactory {

    private CarMemoryDB instance;

    @Override
    public CarDao getCarDao() {
        if (instance == null) {
            instance = new CarMemoryDB();
        }
        return instance;

    }
}
