package edu.bbte.idde.bmim2214.dataaccess.factory;

import edu.bbte.idde.bmim2214.dataaccess.dao.CarDao;

public abstract class AbstractDaoFactory {
    private static AbstractDaoFactory instance;

    public static AbstractDaoFactory getInstance() {
        if (instance == null) {
            instance = new CarDaoJdbcFactory();
        } else {
            instance = new CarDaoMemoFactory();
        }
        return instance;
    }

    public abstract CarDao getCarDao();
}
