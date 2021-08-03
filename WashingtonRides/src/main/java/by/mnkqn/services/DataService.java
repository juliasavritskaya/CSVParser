package by.mnkqn.services;

import by.mnkqn.entities.Rides;
import by.mnkqn.entities.Stations;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DataService {

    static Logger logger = LoggerFactory.getLogger(DataService.class);

    public CSVParser loadFile(String path) {

        CSVParser records = null;
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(path));
            records = CSVParser.parse(lineReader, CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            logger.info("Successfully loaded file");
            return records;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("File wasn't loaded.");
        }
        return records;
    }

    public void uploadDataToCollection(CSVParser records, List<Rides> ridesList, List<Stations> stationsList) {
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

            ridesList.add(ride);

            startStation.setStationID(Integer.parseInt(record.get(5)));
            startStation.setStationName(record.get(4));
            endStation.setStationID(Integer.parseInt(record.get(7)));
            endStation.setStationName(record.get(6));

            boolean start = false;
            boolean end = false;
            for (Stations st : stationsList) {
                if (st.getStationID() == startStation.getStationID()) {
                    start = true;
                }
                if (st.getStationID() == endStation.getStationID()) {
                    end = true;
                }
            }
            if (startStation.getStationID() != endStation.getStationID()) {
                if (!start) {
                    stationsList.add(startStation);
                }
                if (!end) {
                    stationsList.add(endStation);
                }
            } else {
                if (!start) {
                    stationsList.add(startStation);
                }
            }
        }
        logger.info("Rides and stations collection fullfilled");
    }
}
