package by.mnkqn.servlets;

import by.mnkqn.entities.Rides;
import by.mnkqn.services.HikariCPDataSource;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "getRidesByBicycle", value = "/bicycle")
public class RidesByBicycleServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String rideableType = request.getParameter("rideable_type");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Rides> ridesResult = new ArrayList<>();
        try {
            Connection connection = HikariCPDataSource.getConnection();
            String sqlStatement = "SELECT * FROM rides WHERE RIDEABLE_TYPE = ?";
            final int RIDEABLE_TYPE = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(RIDEABLE_TYPE, rideableType);
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

                ridesResult.add(rides);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        for (Rides ride : ridesResult) {
            String rideJsonString = new Gson().toJson(ride);
            out.print(rideJsonString);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
