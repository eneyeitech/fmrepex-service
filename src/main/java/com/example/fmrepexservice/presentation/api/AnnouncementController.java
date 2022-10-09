package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.announcementmanagement.business.Announcement;
import com.example.fmrepexservice.business.APIAnnouncementService;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Manager;
import com.example.fmrepexservice.usermanagement.business.user.Technician;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AnnouncementController {

    private Map<String, Object> feedbackMap;
    @Autowired
    private APIAnnouncementService announcementService;


    @Autowired
    public AnnouncementController(){
        feedbackMap = new HashMap<>();
    }

    @GetMapping("api/announcement")
    public Object getAnnouncements(){
        resetFeedback();
        User user = Helper.retrieveUser();
        if(user.getUserType() == UserType.MANAGER){
            return announcementService.getAnnouncementsByManager(user.getEmail());
        }

        if(user.getUserType() == UserType.TENANT && ((Tenant) user).hasAManager()){
            return announcementService.getAnnouncementsByManager(((Tenant) user).getManagerEmail());
        }
        else {
            feedbackMap.put("message", "announcements not available");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("api/manager/announcement")
    public Object getAnnouncementsByManager(){
        resetFeedback();
        User user = Helper.retrieveUser();
        if(user.getUserType() == UserType.MANAGER){
            return announcementService.getAnnouncementsByManager(user.getEmail());
        }else {
            feedbackMap.put("message", "announcements not available");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("api/tenant/announcement")
    public Object getAnnouncementsByTenant(){
        resetFeedback();
        User user = Helper.retrieveUser();
        if(user.getUserType() == UserType.TENANT){
            return announcementService.getAnnouncementsByManager(((Tenant)user).getManagerEmail());
        }else {
            feedbackMap.put("message", "announcements not available");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("api/slug/announcement")
    public Object getAnnouncement(@RequestParam String slug){
        resetFeedback();

        return announcementService.getAnnouncement(slug);

    }

    @GetMapping("api/announcement/all")
    public Object getAllAnnouncements(){
        resetFeedback();

        return announcementService.getAnnouncements();

    }

    @DeleteMapping("api/announcement/{id}")
    public Object deleteAnnouncement(@PathVariable String id){
        feedbackMap = new HashMap<>();
        if(!announcementService.doesAnnouncementExist(id)){
            feedbackMap.put("message", "Announcement does not exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
        User loggedInUser = Helper.retrieveUser();
        if(loggedInUser.getUserType() != UserType.MANAGER){
            feedbackMap.put("message", "Action not approved for user");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        Announcement announcementToDelete = announcementService.getAnnouncement(id);

        if(!announcementToDelete.getManagerEmail().equalsIgnoreCase(loggedInUser.getEmail())){
            feedbackMap.put("message", "Announcement can not be deleted");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        announcementService.deleteAnnouncement(id);

        if(announcementService.doesAnnouncementExist(id)){
            feedbackMap.put("message", "Announcement not deleted");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }else{
            feedbackMap.put("message", "Announcement deleted successfully");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
        }
    }


    public void resetFeedback(){
        feedbackMap = new HashMap<>();
    }
}
