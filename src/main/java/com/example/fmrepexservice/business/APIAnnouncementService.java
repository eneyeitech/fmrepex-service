package com.example.fmrepexservice.business;

import com.example.fmrepexservice.announcementmanagement.business.Announcement;
import com.example.fmrepexservice.announcementmanagement.business.AnnouncementService;
import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.query.AnnouncementQuery;
import com.example.fmrepexservice.query.BuildingQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class APIAnnouncementService {

    @Autowired
    private AnnouncementQuery query;

    @Autowired
    private AnnouncementService announcementService;


    public Announcement getAnnouncement(String id){
        return query.getAnnouncement(id);
    }
    public boolean doesAnnouncementExist(String id){
        return getAnnouncement(id) != null;
    }

    public List<Announcement> getAnnouncements(){
        return query.getAnnouncements();
    }

    public List<Announcement> getAnnouncementsByManager(String email){
        return query.getAnnouncementsByManager(email);
    }

    public void deleteAnnouncement(String id){
        announcementService.remove(id);
    }
}
