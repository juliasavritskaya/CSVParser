CREATE TABLE Rides
(
    RideId VARCHAR(50) PRIMARY KEY,
    RideableType VARCHAR(100),
    StartedAt DATETIME,
    EndedAt DATETIME,
    StartStationId INT,
    EndStationId INT,
    FOREIGN KEY (StartStationId) references Stations (StationId),
    FOREIGN KEY (EndStationId) references Stations (StationId),
    MemberCasual VARCHAR(50),
    StartLat VARCHAR (50),
    StartLong VARCHAR (50),
    EndLat VARCHAR (50),
    EndLong VARCHAR (50)
);