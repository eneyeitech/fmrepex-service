package com.example.fmrepexservice.query;

import com.example.fmrepexservice.announcementmanagement.business.Announcement;
import com.example.fmrepexservice.announcementmanagement.business.AnnouncementService;
import com.example.fmrepexservice.companymanagement.business.Company;
import com.example.fmrepexservice.companymanagement.business.CompanyService;
import com.example.fmrepexservice.constant.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class AnnouncementQuery {

    private AnnouncementService announcementService;
    @Autowired
    public AnnouncementQuery(AnnouncementService announcementService){
        this.announcementService = announcementService;
    }

    public Announcement getAnnouncement(String announcementId){
        return announcementService.get(announcementId);
    }
    public List<Announcement> getAnnouncements(){
        return announcementService.getAll();
    }

    public List<Announcement> getAnnouncementsByManager(String managerEmail){
        return getAnnouncements().stream().filter(a -> (a.getManagerEmail().equalsIgnoreCase(managerEmail))).collect(Collectors.toList());
    }



}
