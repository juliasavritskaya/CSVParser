package org.example;

import org.apache.commons.csv.*;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {

        dbconnection();
        ridesdata();

    }

    public static void ridesdata() {

        String csvFilePath = "./src/bikesdata.csv";
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            CSVParser records = CSVParser.parse(lineReader, CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            ArrayList<Stations> stations = new ArrayList<>();
            ArrayList<Rides> rides = new ArrayList<>();
            for (CSVRecord record : records) {
                Stations startStation = new Stations();
                Stations endStation = new Stations();
                Rides ride = new Rides();
                ride.setRideID(record.get(0));
                ride.setRideableType(record.get(1));
                ride.setRidesStartedAt(record.get(2));
                ride.setRidesEndedAt(record.get(3));
                ride.setStartStationID(Integer.parseInt(record.get(5)));
                ride.setEndStationID(Integer.parseInt(record.get(7)));
                ride.setRideStartLat(record.get(8));
                ride.setRideStartLong(record.get(9));
                ride.setRideEndLat(record.get(10));
                ride.setRideEndLong(record.get(11));
                ride.setMemberCasual(record.get(12));
                rides.add(ride);

                startStation.setStationID(Integer.parseInt(record.get(5)));
                startStation.setStationName(record.get(4));
                endStation.setStationID(Integer.parseInt(record.get(7)));
                endStation.setStationName(record.get(6));

                boolean start = false;
                boolean end = false;
                for (Stations st : stations) {
                    if (st.getStationID() == startStation.getStationID()) {
                        start = true;
                    }
                    if (st.getStationID() == endStation.getStationID()) {
                        end = true;
                    }
                }
                if (startStation.getStationID() != endStation.getStationID()) {
                    if (!start) {
                        stations.add(startStation);
                    }
                    if (!end) {
                        stations.add(endStation);
                    }
                } else {
                    if (!start) {
                        stations.add(startStation);
                    }
                }
            }

            PreparedStatement statementStations = null;
            Connection con = dbconnection();
            String sqlStations = "INSERT INTO stations(STATIONID, STATIONNAME) VALUES (?, ?)";
            statementStations = con.prepareStatement(sqlStations);
            for (Stations st : stations) {
                statementStations.setInt(1, st.getStationID());
                statementStations.setString(2, st.getStationName());
                statementStations.addBatch();
            }
            statementStations.executeBatch();

            PreparedStatement statementRides = null;
            String sqlRides = "INSERT INTO rides(RIDEID, RIDEABLETYPE, STARTEDAT, ENDEDAT, STARTSTATIONID, ENDSTATIONID, MEMBERCASUAL, STARTLAT, STARTLONG, ENDLAT, ENDLONG) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statementRides = con.prepareStatement(sqlRides);

            for (Rides record : rides) {
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
            con.commit();
            con.close();

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection dbconnection() {

        Connection connection = null;
        try {
            String user = "root";
            String password = "mnkqn1354";
            String dburl = "jdbc:mysql://localhost:3306/washrides?serverTimezone=Europe/Moscow&useSSL=false";
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dburl, user, password);
            System.out.println("connection sucessfull");
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
