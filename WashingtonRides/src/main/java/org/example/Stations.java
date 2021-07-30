package org.example;

public class Stations {
    private int stationID;
    private String stationName;

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return "Stations{" +
                "stationID=" + stationID +
                ", stationName='" + stationName + '\'' +
                '}';
    }
}