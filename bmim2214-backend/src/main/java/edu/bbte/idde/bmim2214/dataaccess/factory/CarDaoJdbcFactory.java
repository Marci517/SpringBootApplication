package edu.bbte.idde.bmim2214.dataaccess.factory;

import edu.bbte.idde.bmim2214.dataaccess.dao.CarDao;
import edu.bbte.idde.bmim2214.dataaccess.dao.CarDaoJdbc;

public class CarDaoJdbcFactory extends AbstractDaoFactory {
    @Override
    public CarDao getCarDao() {
        return new CarDaoJdbc();
    }
}
