package com.example.fmrepexservice.requestmanagement.helper;

import java.util.Random;

public class RequestIdGenerator {
    private int leftLimit = 65;
    private int rightLimit = 90;
    private int targetStringLength;
    private Random random = new Random();

    public RequestIdGenerator(int len){
        targetStringLength = len;
    }

    public String generate() {
        return "R-"+random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
