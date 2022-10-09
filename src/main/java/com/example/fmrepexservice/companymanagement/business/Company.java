package com.example.fmrepexservice.companymanagement.business;

import com.example.fmrepexservice.companymanagement.helper.CompanyIdGenerator;
import com.example.fmrepexservice.constant.Address;
import com.example.fmrepexservice.constant.State;

public class Company {
    private String id;
    private String name;
    private Address address;

    public Company(){

    }

    public Company(CompanyIdGenerator companyIdGenerator){
        id= companyIdGenerator.generate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(this.id == null){
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean hasId(){
        return id != null;
    }


    public String getHouseNo() {
        return address.getHouseNo();
    }

    public String getStreetName() {
        return address.getStreetName();
    }


    public String getTownName() {
        return address.getTownName();
    }

    public State getState() {
        return address.getState();
    }

    public void setHouseNo(String houseNo) {
        setAddress(new Address(houseNo, address.getStreetName(), address.getTownName(), address.getState()));
    }

    public void setStreetName(String streetName) {
        setAddress(new Address(address.getHouseNo(), streetName, address.getTownName(), address.getState()));
    }


    public void setTownName(String townName) {
        setAddress(new Address(address.getHouseNo(), address.getStreetName(), townName, address.getState()));
    }



    public void setState(State state) {
        setAddress(new Address(address.getHouseNo(), address.getStreetName(), address.getTownName(), state));
    }

}
