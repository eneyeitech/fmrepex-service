package com.example.fmrepexservice.requestmanagement.persistence;



import com.example.fmrepexservice.requestmanagement.exception.TableException;
import com.example.fmrepexservice.requestmanagement.helper.RequestIdGenerator;

import java.util.List;
import java.util.Map;

public abstract class DAO<T> {

    protected RequestIdGenerator requestIdGenerator;

    public DAO(){
        requestIdGenerator = new RequestIdGenerator(10);
    }

    protected Map<String, List<T>> store;
    abstract public boolean add(T t);
    abstract public boolean remove(String id);

    abstract public T get(String id);
    abstract  public List<T> getAll();
    abstract  public List<T> getAll(String tenantEmail);
    abstract public boolean update(T t);
    abstract public void clear();
    abstract public void createTable() throws TableException;
    abstract public void dropTable() throws TableException;

}
