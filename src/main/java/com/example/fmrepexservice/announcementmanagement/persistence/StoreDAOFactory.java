package com.example.fmrepexservice.announcementmanagement.persistence;


import com.example.fmrepexservice.announcementmanagement.business.Announcement;
import com.example.fmrepexservice.announcementmanagement.database.AnnouncementStore;
import com.example.fmrepexservice.announcementmanagement.database.StoreInstance;

public class StoreDAOFactory extends DAOFactory {

    public static StoreInstance createInstance(){
        return AnnouncementStore.getInstance();
    }

    @Override
    public DAO<Announcement> getAnnouncementDAO() {
        return new StoreAnnouncementDAO();
    }
}
