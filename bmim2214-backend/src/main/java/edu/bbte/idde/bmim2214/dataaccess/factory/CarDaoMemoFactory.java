package edu.bbte.idde.bmim2214.dataaccess.factory;

import edu.bbte.idde.bmim2214.dataaccess.dao.AllDao;
import edu.bbte.idde.bmim2214.dataaccess.dao.CarMemoryDB;

public class CarDaoMemoFactory extends AbstractDaoFactory {

    private CarMemoryDB instance;

    @Override
    public AllDao getCarDao() {
        if (instance == null) {
            instance = new CarMemoryDB();
        }
        return instance;

    }
}
