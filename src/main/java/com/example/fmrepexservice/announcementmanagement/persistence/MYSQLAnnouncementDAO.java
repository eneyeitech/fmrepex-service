package com.example.fmrepexservice.announcementmanagement.persistence;

import com.example.fmrepexservice.announcementmanagement.business.Announcement;
import com.example.fmrepexservice.announcementmanagement.exception.TableException;

import java.util.List;

public class MYSQLAnnouncementDAO extends DAO<Announcement> {
    @Override
    public boolean add(Announcement announcement) {
        return false;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public Announcement get(String id) {
        return null;
    }

    @Override
    public List<Announcement> getAll() {
        return null;
    }

    @Override
    public boolean update(Announcement announcement) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void createTable() throws TableException {

    }

    @Override
    public void dropTable() throws TableException {

    }


}
