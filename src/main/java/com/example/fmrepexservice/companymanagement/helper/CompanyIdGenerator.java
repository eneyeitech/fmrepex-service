package com.example.fmrepexservice.companymanagement.helper;

import java.util.Random;

public class CompanyIdGenerator {
    private int leftLimit = 65;
    private int rightLimit = 90;
    private int targetStringLength;
    private Random random = new Random();

    public CompanyIdGenerator(int len){
        targetStringLength = len;
    }

    public String generate() {
        return "C-"+random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
