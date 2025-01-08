package edu.bbte.idde.bmim2214.dataaccess.factory;

import edu.bbte.idde.bmim2214.dataaccess.dao.AllDao;

import static edu.bbte.idde.bmim2214.profile.ProfileProvider.getProfile;

public abstract class AbstractDaoFactory {
    private static volatile AbstractDaoFactory instance; // minden szal szamara lathato

    public static synchronized AbstractDaoFactory getInstance() {
        if (instance == null) {
            String profile = getProfile("profile");
            if ("jdbc".equals(profile)) {
                instance = new CarDaoJdbcFactory();
            } else {
                instance = new CarDaoMemoFactory();
            }

        }
        return instance;
    }

    public abstract AllDao getCarDao();
}
