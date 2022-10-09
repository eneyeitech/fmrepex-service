package com.example.fmrepexservice.announcementmanagement.database;

import com.example.fmrepexservice.announcementmanagement.business.Announcement;

import java.util.concurrent.ConcurrentHashMap;

public class AnnouncementStore extends StoreInstance {
    private static AnnouncementStore instance;
    private ConcurrentHashMap<String, Announcement> store = new ConcurrentHashMap<>();

    private AnnouncementStore(){

    }

    public static StoreInstance getInstance(){
        if(instance == null){
            instance = new AnnouncementStore();
        }
        return instance;
    }

    public ConcurrentHashMap<String, Announcement> getStore(){
        return store;
    }
}
