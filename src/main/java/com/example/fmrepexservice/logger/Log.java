package com.example.fmrepexservice.logger;

import java.time.LocalDateTime;

public class Log {
    private String message;
    private LocalDateTime dateTime;

    public Log(String m){
        message = m;
        dateTime = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
