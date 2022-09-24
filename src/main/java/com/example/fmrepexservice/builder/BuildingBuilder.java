package com.example.fmrepexservice.builder;

import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.buildingmanagement.business.BuildingService;
import com.example.fmrepexservice.buildingmanagement.business.ManagedBuilding;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BuildingBuilder {
    private BuildingService buildingService;
    private Scanner scanner;
    private Building building;
    private String name;
    private int noOfFlats;
    private AddressBuilder addressBuilder;
    private CoordinateBuilder coordinateBuilder;

    public BuildingBuilder(BuildingService buildingService){
        this.buildingService = buildingService;
    }

    public Building queryBuilding(String buildingId){
        return buildingService.get(buildingId);
    }

    public List<Building> queryBuildings(){
        return buildingService.getAll();
    }

    public List<Tenant> queryBuildingTenants(String buildingId){
        return buildingService.getOccupants(buildingId);
    }

    public BuildingBuilder(Scanner scanner){
        this.scanner = scanner;
        showPrompt("##::Building::##");
        name = getString("Enter building name: ");

        try {
            noOfFlats = Integer.parseInt(getString("Enter no of flats: "));
        }catch (NumberFormatException c){
            noOfFlats = 1;
        }
        addressBuilder = new AddressBuilder(scanner);
        coordinateBuilder = new CoordinateBuilder(scanner);
    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    public int getNumber(String msg){
        showPrompt(msg);
        int c = 0;
        try{
            c = scanner.nextInt();
        }catch (InputMismatchException e){
            showPrompt("Enter a number");
        }catch (NoSuchElementException e){
            showPrompt("Enter a number");
        }

        return c;
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public Building getBuilding(){
        //building = new ManagedBuilding(new BuildingIdGenerator(10));
        building = new ManagedBuilding();
        building.setName(name);
        building.setNoOfFlats(noOfFlats);
        building.setAddress(addressBuilder.getAddress());
        building.setCoordinate(coordinateBuilder.getCoordinate());
        return  building;
    }
}
