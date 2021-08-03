package by.mnkqn.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import by.mnkqn.App;
import by.mnkqn.entities.*;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBService {

    static Logger logger = LoggerFactory.getLogger(DBService.class);

    public Connection dbConnection() {

        FileInputStream fis;
        Connection connection = null;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/application.properties");
            property.load(fis);
            String user = property.getProperty("db.user");
            String password = property.getProperty("db.password");
            String dburl = property.getProperty("db.url");

            MysqlDataSource mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(dburl);
            mysqlDS.setUser(user);
            mysqlDS.setPassword(password);
            connection = mysqlDS.getConnection();
            connection.setAutoCommit(false);
            logger.info("Connection to database established");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            logger.error("Connection failed");
        }
        return connection;

    }

    public void loadDataToDb(List<Rides> ridesList, List<Stations> stationsList) throws SQLException {
        Connection con = dbConnection();
        try {
            PreparedStatement statementStations = null;
            String sqlStations = "INSERT INTO stations(STATION_ID, STATION_NAME) VALUES (?, ?)";
            statementStations = con.prepareStatement(sqlStations);
            for (Stations st : stationsList) {
                statementStations.setInt(1, st.getStationID());
                statementStations.setString(2, st.getStationName());
                statementStations.addBatch();
            }
            statementStations.executeBatch();
            logger.info("Stations table fullfilled.");

            PreparedStatement statementRides = null;
            String sqlRides = "INSERT INTO rides(RIDE_ID, RIDEABLE_TYPE, STARTED_AT, ENDED_AT, START_STATION_ID, END_STATION_ID, MEMBER_CASUAL, START_LATITUDE, START_LONGITUDE, END_LATITUDE, END_LONGITUDE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statementRides = con.prepareStatement(sqlRides);

            for (Rides record : ridesList) {
                statementRides.setString(1, record.getRideID());
                statementRides.setString(2, record.getRideableType());
                statementRides.setString(3, record.getRidesStartedAt());
                statementRides.setString(4, record.getRidesEndedAt());
                statementRides.setInt(5, record.getStartStationID());
                statementRides.setInt(6, record.getEndStationID());
                statementRides.setString(7, record.getMemberCasual());
                statementRides.setString(8, record.getRideStartLat());
                statementRides.setString(9, record.getRideStartLong());
                statementRides.setString(10, record.getRideEndLat());
                statementRides.setString(11, record.getRideEndLong());
                statementRides.addBatch();
            }
            statementRides.executeBatch();
            logger.info("Rides table fullfilled.");
            con.commit();
            logger.info("SQL statements commited.");

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("SQL exception caught, data wasn't transferred");
        }
        finally {
            con.close();
            logger.info("Connection closed.");
        }
    }
}