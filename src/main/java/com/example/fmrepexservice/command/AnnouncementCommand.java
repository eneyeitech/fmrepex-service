package com.example.fmrepexservice.command;

import com.example.fmrepexservice.announcementmanagement.business.Announcement;
import com.example.fmrepexservice.announcementmanagement.business.AnnouncementService;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.user.Manager;

public class AnnouncementCommand extends Command{

    protected User loggedInUser;
    protected Announcement announcement;
    private AnnouncementService announcementService;

    protected boolean announcementCreated;
    protected boolean announcementEdited;


    public AnnouncementCommand(User loggedInUser, Announcement announcement, AnnouncementService announcementService){
        this.loggedInUser = loggedInUser;
        this.announcement = announcement;
        this.announcementService = announcementService;
    }

    private void handleRequest(){
        switch (loggedInUser.getUserType()){
            case MANAGER:
                Manager manager = (Manager) loggedInUser;
                createAnnouncement();
                break;
            case TENANT:
            case DEPENDANT:
            case TECHNICIAN:
            case ADMINISTRATOR:
            default:
        }
    }

    private boolean createAnnouncement(){
        if(announcementService.add(announcement)){
            setSuccessful(true);
            setAnnouncementCreated(true);
            return true;
        }
        return false;
    }

    public boolean editAnnouncement(){
        if(announcementService.update(announcement)){
            setSuccessful(true);
            setAnnouncementEdited(true);
            return true;
        }
        return false;
    }

    @Override
    public void actionRequester() {
        handleRequest();
        notifyObservers();
    }

    public boolean isAnnouncementCreated() {
        return announcementCreated;
    }

    public void setAnnouncementCreated(boolean announcementCreated) {
        this.announcementCreated = announcementCreated;
    }

    public boolean isAnnouncementEdited() {
        return announcementEdited;
    }

    public void setAnnouncementEdited(boolean announcementEdited) {
        this.announcementEdited = announcementEdited;
    }
}
