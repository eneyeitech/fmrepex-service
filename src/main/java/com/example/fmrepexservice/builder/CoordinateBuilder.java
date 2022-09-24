package com.example.fmrepexservice.builder;

import com.example.fmrepexservice.buildingmanagement.business.Coordinate;

import java.util.Scanner;

public class CoordinateBuilder {
    private Scanner scanner;
    private Coordinate coordinate;

    private String longitude;
    private String latitude;

    public CoordinateBuilder(Scanner scanner){
        this.scanner = scanner;
        showPrompt("::Coordinate::");
        longitude = getString("Enter longitude: ");
        latitude = getString("Enter latitude: ");
    }



    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public Coordinate getCoordinate(){
        coordinate = new Coordinate(longitude, latitude);
        return coordinate;
    }
}
