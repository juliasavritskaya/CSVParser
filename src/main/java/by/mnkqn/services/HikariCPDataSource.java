package by.mnkqn.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCPDataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        Properties property = new Properties();
        try {
            FileInputStream fis = new FileInputStream("D:/Javaproj/itech/finaltasks/WashingtonRides/src/main/resources/application.properties");
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String user = property.getProperty("db.user");
        String password = property.getProperty("db.password");
        String dburl = property.getProperty("db.url");
        config.setJdbcUrl(dburl);
        config.setUsername(user);
        config.setPassword(password);
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private HikariCPDataSource(){}
}
