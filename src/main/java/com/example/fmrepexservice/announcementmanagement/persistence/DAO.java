package com.example.fmrepexservice.announcementmanagement.persistence;

import com.example.fmrepexservice.announcementmanagement.exception.TableException;
import com.example.fmrepexservice.announcementmanagement.helper.AnnouncementIdGenerator;

import java.util.List;
import java.util.Map;

public abstract class DAO<T> {

    protected AnnouncementIdGenerator announcementIdGenerator;

    public DAO(){
        announcementIdGenerator = new AnnouncementIdGenerator(10);
    }

    protected Map<String, T> store;
    abstract public boolean add(T t);
    abstract public boolean remove(String id);

    abstract public T get(String id);
    abstract  public List<T> getAll();
    abstract public boolean update(T t);
    abstract public void clear();
    abstract public void createTable() throws TableException;
    abstract public void dropTable() throws TableException;

}
