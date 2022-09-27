package com.example.fmrepexservice.business;

import com.example.fmrepexservice.buildingmanagement.business.State;

public class NewBuilding {


    private String name;
    private int noOfFlats = 0;
    private String houseNo;
    private String streetName;
    private String townName;
    private String state;
    private String longitude;
    private String latitude;

    public NewBuilding(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfFlats() {
        return noOfFlats;
    }

    public void setNoOfFlats(int noOfFlats) {
        this.noOfFlats = noOfFlats;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
