package com.example.fmrepexservice.workordermanagement.helper;

import java.util.Random;

public class WorkOrderIdGenerator {
    private int leftLimit = 48;
    private int rightLimit = 122;
    private int targetStringLength;
    private Random random = new Random();

    public WorkOrderIdGenerator(int len){
        targetStringLength = len;
    }

    public String generate() {
        return "W-"+random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
