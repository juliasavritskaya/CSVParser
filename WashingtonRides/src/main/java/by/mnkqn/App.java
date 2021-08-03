package by.mnkqn;

import by.mnkqn.entities.*;
import by.mnkqn.services.*;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws SQLException, IOException {

        Logger logger = LoggerFactory.getLogger(App.class);
        logger.info("Program started");

        FileInputStream fis = new FileInputStream("src/main/resources/application.properties");
        Properties property = new Properties();
        property.load(fis);
        String filepath = property.getProperty("csvfilepath");

        DataService dataService = new DataService();
        CSVParser records = dataService.loadFile(filepath);

        List<Rides> ridesList = new LinkedList<>();
        List<Stations> stationsList = new LinkedList<>();

        dataService.uploadDataToCollection(records, ridesList, stationsList);

        DBService dbService = new DBService();
        dbService.loadDataToDb(ridesList, stationsList);

        records.close();
        logger.info("Program finished");
    }
}
