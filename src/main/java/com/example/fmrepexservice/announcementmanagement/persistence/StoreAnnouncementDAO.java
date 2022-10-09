package com.example.fmrepexservice.announcementmanagement.persistence;

import com.example.fmrepexservice.announcementmanagement.business.Announcement;
import com.example.fmrepexservice.announcementmanagement.database.AnnouncementStore;
import com.example.fmrepexservice.announcementmanagement.exception.TableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StoreAnnouncementDAO extends DAO<Announcement> {

    public StoreAnnouncementDAO(){
        store = AnnouncementStore.getInstance().getStore();
    }

    @Override
    public boolean add(Announcement announcement) {
        if(announcement.hasId()){
            return update(announcement);
        }
        announcement.setId(announcementIdGenerator.generate());
        Announcement buildingInStore = store.put(announcement.getId(), announcement);
        if(buildingInStore == null){
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String id) {
        Announcement announcement = store.remove(id);
        if(announcement == null){
            return false;
        }
        return true;
    }

    @Override
    public Announcement get(String id) {
        return store.get(id);
    }

    @Override
    public List<Announcement> getAll() {
        List<Announcement> list = new ArrayList<>();
        for(ConcurrentHashMap.Entry<String, Announcement> m: store.entrySet()){
            list.add(m.getValue());
        }
        Collections.sort(list, (o1, o2) -> o1.getCreatedDateTime().toLocalDate().compareTo(o2.getCreatedDateTime().toLocalDate()));
        return list;
    }

    @Override
    public boolean update(Announcement announcement) {
        Announcement announcementInStore = store.put(announcement.getId(), announcement);
        if(announcementInStore == null){
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        store.clear();
    }

    @Override
    public void createTable() throws TableException {

    }

    @Override
    public void dropTable() throws TableException {

    }
}
