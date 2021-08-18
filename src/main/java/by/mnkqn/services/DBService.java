package by.mnkqn.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import by.mnkqn.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBService {

    static Logger logger = LoggerFactory.getLogger(DBService.class);

    public void loadDataToDb(Connection con, List<Rides> ridesList, List<Stations> stationsList) throws SQLException {

        try {
            PreparedStatement statementStations = null;
            String sqlStations = "INSERT INTO stations(STATION_ID, STATION_NAME) VALUES (?, ?)";
            final int STATION_ID = 1, STATION_NAME = 2;
            statementStations = con.prepareStatement(sqlStations);
            for (Stations st : stationsList) {
                statementStations.setInt(STATION_ID, st.getStationID());
                statementStations.setString(STATION_NAME, st.getStationName());
                statementStations.addBatch();
            }
            statementStations.executeBatch();
            logger.info("Stations table fullfilled.");

            PreparedStatement statementRides = null;
            String sqlRides = "INSERT INTO rides(RIDE_ID, RIDEABLE_TYPE, STARTED_AT, ENDED_AT, START_STATION_ID, END_STATION_ID, MEMBER_CASUAL, START_LATITUDE, START_LONGITUDE, END_LATITUDE, END_LONGITUDE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            final int RIDE_ID = 1, RIDEABLE_TYPE = 2, STARTED_AT = 3, ENDED_AT = 4, START_STATION_ID = 5, END_STATION_ID = 6, MEMBER_CASUAL = 7, START_LATITUDE = 8, START_LONGITUDE = 9, END_LATITUDE = 10, END_LONGITUDE = 11;
            statementRides = con.prepareStatement(sqlRides);

            for (Rides record : ridesList) {
                statementRides.setString(RIDE_ID, record.getRideID());
                statementRides.setString(RIDEABLE_TYPE, record.getRideableType());
                statementRides.setString(STARTED_AT, record.getRidesStartedAt());
                statementRides.setString(ENDED_AT, record.getRidesEndedAt());
                statementRides.setInt(START_STATION_ID, record.getStartStationID());
                statementRides.setInt(END_STATION_ID, record.getEndStationID());
                statementRides.setString(MEMBER_CASUAL, record.getMemberCasual());
                statementRides.setString(START_LATITUDE, record.getRideStartLat());
                statementRides.setString(START_LONGITUDE, record.getRideStartLong());
                statementRides.setString(END_LATITUDE, record.getRideEndLat());
                statementRides.setString(END_LONGITUDE, record.getRideEndLong());
                statementRides.addBatch();
            }
            statementRides.executeBatch();
            logger.info("Rides table fullfilled.");
            logger.info("SQL statements commited.");

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("SQL exception caught, data wasn't transferred");
        } finally {
            //con.close();
            logger.info("Connection closed.");
        }
    }

}