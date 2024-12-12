package edu.bbte.idde.bmim2214.dataaccess.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("jdbc")
@Data
@Configuration
@ConfigurationProperties(prefix = "jdbc")

public class DataSourceConfig {
    private String driverClass;
    private String url;
    private String username;
    private String password;
    private Integer poolSize;

    @Bean
    public HikariDataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(poolSize);
        return dataSource;
    }

}
