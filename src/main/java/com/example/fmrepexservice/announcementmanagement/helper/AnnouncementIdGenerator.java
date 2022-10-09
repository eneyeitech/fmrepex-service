package com.example.fmrepexservice.announcementmanagement.helper;

import java.util.Random;

public class AnnouncementIdGenerator {
    private int leftLimit = 65;
    private int rightLimit = 90;
    private int targetStringLength;
    private Random random = new Random();

    public AnnouncementIdGenerator(int len){
        targetStringLength = len;
    }

    public String generate() {
        return "A-"+random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
