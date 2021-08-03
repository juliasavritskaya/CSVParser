CREATE TABLE rides
(
    ride_id VARCHAR(50) PRIMARY KEY,
    rideable_type VARCHAR(100),
    started_at DATETIME,
    ended_at DATETIME,
    start_station_id INT,
    end_station_id INT,
    FOREIGN KEY (start_station_id) references stations (station_id),
    FOREIGN KEY (end_station_id) references Stations (station_id),
    member_casual VARCHAR(50),
    start_latitude DECIMAL (25, 22),
    start_longitude DECIMAL (25, 22),
    end_longitude DECIMAL (25, 22),
    end_longitude DECIMAL (25, 22)
);
