package edu.bbte.idde.bmim2214.dataaccess.factory;

import edu.bbte.idde.bmim2214.dataaccess.dao.AllDao;
import edu.bbte.idde.bmim2214.dataaccess.dao.CarDaoJdbc;

public class CarDaoJdbcFactory extends AbstractDaoFactory {
    @Override
    public AllDao getCarDao() {
        return new CarDaoJdbc();
    }
}
