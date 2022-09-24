package com.example.fmrepexservice.buildingmanagement.helper;

import java.util.Random;

public class BuildingIdGenerator {
    private int leftLimit = 48;
    private int rightLimit = 122;
    private int targetStringLength;
    private Random random = new Random();

    public BuildingIdGenerator(int len){
        targetStringLength = len;
    }

    public String generate() {
        return "B-"+random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
