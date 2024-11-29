package edu.bbte.idde.bmim2214.dataaccess.factory;

import com.zaxxer.hikari.HikariDataSource;

import static edu.bbte.idde.bmim2214.profile.ProfileProvider.getProfile;

public class DataSourceFactory {
    private static volatile HikariDataSource dataSource;

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new HikariDataSource();
            dataSource.setDriverClassName(getProfile("driverClass"));
            dataSource.setJdbcUrl(getProfile("url"));
            dataSource.setUsername(getProfile("username"));
            dataSource.setPassword(getProfile("password"));
            dataSource.setMaximumPoolSize(Integer.parseInt(getProfile("poolSize")));
        }
        return dataSource;
    }

}
