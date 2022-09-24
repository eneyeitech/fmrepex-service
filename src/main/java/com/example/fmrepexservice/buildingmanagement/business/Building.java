package com.example.fmrepexservice.buildingmanagement.business;

public abstract class Building {
    protected String id;
    protected String name;
    protected int noOfFlats = 0;
    protected Address address;
    protected Coordinate coordinate;

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

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public String getLongitude() {
        return coordinate.getLongitude();
    }



    public String getLatitude() {
        return coordinate.getLatitude();
    }

    public void setLatitude(String latitude) {
        setCoordinate(new Coordinate(coordinate.getLongitude(), latitude));
    }
    public void setLongitude(String longitude) {
        setCoordinate(new Coordinate(longitude, coordinate.getLatitude()));
    }

    public int getNoOfFlats() {
        return noOfFlats;
    }

    public void setNoOfFlats(int noOfFlats) {
        this.noOfFlats = noOfFlats;
    }

    public boolean hasId(){
        return getId() != null;
    }
}
