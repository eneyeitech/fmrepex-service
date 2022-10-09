package com.example.fmrepexservice.constant;

import com.example.fmrepexservice.constant.State;

public class Address {
    private String houseNo;
    private String streetName;
    private String townName;
    private State state;

    public Address(){

    }

    public Address(String houseNo, String streetName, String townName, State state) {
        this.houseNo = houseNo;
        this.streetName = streetName;
        this.townName = townName;
        this.state = state;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public String getStreetName() {
        return streetName;
    }


    public String getTownName() {
        return townName;
    }



    public State getState() {
        return state;
    }



    @Override
    public String toString() {
        return "Address{" +
                "houseNo='" + houseNo + '\'' +
                ", streetName='" + streetName + '\'' +
                ", townName='" + townName + '\'' +
                ", state=" + state +
                '}';
    }
}
