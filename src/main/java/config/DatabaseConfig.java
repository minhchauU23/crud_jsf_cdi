package config;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
public class DatabaseConfig {
    
    private PGSimpleDataSource dataSource;

    public DatabaseConfig() {
        dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/employee_db");
        dataSource.setUser("postgres");
        dataSource.setPassword("123456789");
    }

    @Produces
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @PreDestroy
    public void close() {
        dataSource = null;  
    }
}
