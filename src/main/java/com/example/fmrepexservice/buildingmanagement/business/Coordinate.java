package com.example.fmrepexservice.buildingmanagement.business;

public class Coordinate {
    private String longitude;
    private String latitude;

    public Coordinate(){

    }

    public Coordinate(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }



    public String getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
