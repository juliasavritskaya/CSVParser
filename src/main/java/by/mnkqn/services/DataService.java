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
import java.util.Objects;

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

            if (!Objects.equals(record.get("start_station_id"), "") && !Objects.equals(record.get("end_station_id"), "")) {
                Stations startStation = new Stations();
                Stations endStation = new Stations();
                Rides ride = new Rides();
                ride.setRideID(record.get("ride_id"));
                ride.setRideableType(record.get("rideable_type"));
                ride.setRidesStartedAt(record.get("started_at"));
                ride.setRidesEndedAt(record.get("ended_at"));
                ride.setStartStationID(Integer.parseInt(record.get("start_station_id")));
                ride.setEndStationID(Integer.parseInt(record.get("end_station_id")));
                ride.setRideStartLat(record.get("start_lng"));
                ride.setRideStartLong(record.get("start_lng"));
                ride.setRideEndLat(record.get("end_lat"));
                ride.setRideEndLong(record.get("end_lng"));
                ride.setMemberCasual(record.get("member_casual"));

                ridesList.add(ride);

                startStation.setStationID(Integer.parseInt(record.get("start_station_id")));
                startStation.setStationName(record.get("start_station_name"));
                endStation.setStationID(Integer.parseInt(record.get("end_station_id")));
                endStation.setStationName(record.get("end_station_name"));

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
        }
        logger.info("Rides and stations collection fullfilled");
    }
}