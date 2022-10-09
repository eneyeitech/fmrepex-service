package com.example.fmrepexservice.announcementmanagement.business;

import com.example.fmrepexservice.announcementmanagement.helper.AnnouncementIdGenerator;

import java.time.LocalDateTime;

public class Announcement {
    private String id;
    private String message;
    private LocalDateTime createdDateTime;
    private String managerEmail = null;

    public Announcement(){

    }

    public Announcement(AnnouncementIdGenerator announcementIdGenerator){
        id = announcementIdGenerator.generate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(this.id == null){
            this.id = id;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public boolean hasId(){
        return id != null;
    }

    public boolean hasManager(){
        return managerEmail != null;
    }
}
