package edu.bbte.idde.bmim2214.dataaccess.factory;

import edu.bbte.idde.bmim2214.dataaccess.dao.CarDao;

public abstract class AbstractDaoFactory {
    private static volatile AbstractDaoFactory instance; // minden szal szamara lathato

    public static synchronized AbstractDaoFactory getInstance() {
        if (instance == null) {
            instance = new CarDaoJdbcFactory();
            //instance = new CarDaoMemoFactory();
        }
        return instance;
    }

    public abstract CarDao getCarDao();
}
