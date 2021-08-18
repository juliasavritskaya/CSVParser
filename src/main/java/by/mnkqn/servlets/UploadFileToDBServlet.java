package by.mnkqn.servlets;

import by.mnkqn.entities.Rides;
import by.mnkqn.entities.Stations;
import by.mnkqn.services.DBService;
import by.mnkqn.services.DataService;
import by.mnkqn.services.HikariCPDataSource;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "UploadFileToDBServlet", value = "/tripdata")
public class UploadFileToDBServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Rides> ridesByDateResult = new ArrayList<>();
        try {
            Connection connection = HikariCPDataSource.getConnection();
            String sqlStatement = "SELECT * FROM rides WHERE STARTED_AT >= ? AND ENDED_AT <= ?";
            final int START_DATE = 1;
            final int END_DATE = 2;
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(START_DATE, startDate);
            preparedStatement.setString(END_DATE, endDate);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Rides rides = new Rides();
                rides.setRideID(rs.getString("ride_id"));
                rides.setRideableType(rs.getString("rideable_type"));
                rides.setRidesStartedAt(rs.getString("started_at"));
                rides.setRidesEndedAt(rs.getString("ended_at"));
                rides.setStartStationID(Integer.parseInt(rs.getString("start_station_id")));
                rides.setEndStationID(Integer.parseInt(rs.getString("end_station_id")));
                rides.setRideStartLat(rs.getString("start_latitude"));
                rides.setRideStartLong(rs.getString("start_longitude"));
                rides.setRideEndLat(rs.getString("end_latitude"));
                rides.setRideEndLong(rs.getString("end_longitude"));
                rides.setMemberCasual(rs.getString("member_casual"));

                ridesByDateResult.add(rides);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        for (Rides ride : ridesByDateResult) {
            String rideJsonString = new Gson().toJson(ride);
            out.print(rideJsonString);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String filepath = request.getParameter("fileName");

        Logger logger = LoggerFactory.getLogger(UploadFileToDBServlet.class);
        logger.info("Program started");

        try (Connection con = HikariCPDataSource.getConnection()) {

            DataService dataService = new DataService();
            CSVParser records = dataService.loadFile(filepath);

            List<Rides> ridesList = new LinkedList<>();
            List<Stations> stationsList = new LinkedList<>();

            dataService.uploadDataToCollection(records, ridesList, stationsList);

            DBService dbService = new DBService();
            dbService.loadDataToDb(con, ridesList, stationsList);

            records.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("Program finished");

    }
}
