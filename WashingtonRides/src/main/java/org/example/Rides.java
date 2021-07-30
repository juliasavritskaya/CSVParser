package org.example;

public class Rides {
    private String rideID;
    private String rideableType;
    private String memberCasual;
    private String ridesStartedAt;
    private String ridesEndedAt;
    private String rideStartLat;
    private String rideStartLong;
    private String rideEndLat;
    private String rideEndLong;
    private int startStationID;
    private int endStationID;

    public String getRideID() {
        return rideID;
    }

    public void setRideID(String rideID) {
        this.rideID = rideID;
    }

    public String getRideableType() {
        return rideableType;
    }

    public void setRideableType(String rideableType) {
        this.rideableType = rideableType;
    }

    public String getMemberCasual() {
        return memberCasual;
    }

    public void setMemberCasual(String memberCasual) {
        this.memberCasual = memberCasual;
    }

    public String getRidesEndedAt() {
        return ridesEndedAt;
    }

    public void setRidesEndedAt(String ridesEndedAt) {
        this.ridesEndedAt = ridesEndedAt;
    }

    public String getRidesStartedAt() {
        return ridesStartedAt;
    }

    public void setRidesStartedAt(String ridesStartedAt) {
        this.ridesStartedAt = ridesStartedAt;
    }

    public int getStartStationID() {
        return startStationID;
    }

    public void setStartStationID(int startStationID) {
        this.startStationID = startStationID;
    }

    public int getEndStationID() {
        return endStationID;
    }

    public void setEndStationID(int endStationID) {
        this.endStationID = endStationID;
    }

    public String getRideStartLat() {
        return rideStartLat;
    }

    public void setRideStartLat(String rideStartLat) {
        this.rideStartLat = rideStartLat;
    }

    public String getRideStartLong() {
        return rideStartLong;
    }

    public void setRideStartLong(String rideStartLong) {
        this.rideStartLong = rideStartLong;
    }

    public String getRideEndLat() {
        return rideEndLat;
    }

    public void setRideEndLat(String rideEndLat) {
        this.rideEndLat = rideEndLat;
    }

    public String getRideEndLong() {
        return rideEndLong;
    }

    public void setRideEndLong(String rideEndLong) {
        this.rideEndLong = rideEndLong;
    }

    @Override
    public String toString() {
        return "Rides{" +
                "rideID='" + rideID + '\'' +
                ", rideableType='" + rideableType + '\'' +
                ", memberCasual='" + memberCasual + '\'' +
                ", ridesEndedAt='" + ridesEndedAt + '\'' +
                ", ridesStartedAt='" + ridesStartedAt + '\'' +
                ", startStationID=" + startStationID +
                ", endStationID=" + endStationID +
                ", rideStartLat='" + rideStartLat + '\'' +
                ", rideStartLong='" + rideStartLong + '\'' +
                ", rideEndLat='" + rideEndLat + '\'' +
                ", rideEndLong='" + rideEndLong + '\'' +
                '}';
    }
}