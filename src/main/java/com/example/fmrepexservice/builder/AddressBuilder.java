package com.example.fmrepexservice.builder;

import com.example.fmrepexservice.buildingmanagement.business.Address;
import com.example.fmrepexservice.buildingmanagement.business.State;

import java.util.Random;
import java.util.Scanner;

public class AddressBuilder {

    private Scanner scanner;
    private Address address;

    private String houseNo;
    private String streetName;
    private String townName;
    private State state;

    public AddressBuilder(Scanner scanner){
        this.scanner = scanner;
        showPrompt("::Address::");
        houseNo = getString("Enter house number: ");
        streetName = getString("Enter street name: ");
        townName = getString("Enter town name: ");

        State[] states = State.values();
        Random rand = new Random();

        int n = rand.nextInt(states.length);
        state = states[n];


    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public Address getAddress(){
        address = new Address(houseNo, streetName, townName, state);
        return  address;
    }
}
