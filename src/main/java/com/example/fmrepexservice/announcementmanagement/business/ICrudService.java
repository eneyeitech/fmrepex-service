package com.example.fmrepexservice.announcementmanagement.business;

import java.util.List;

public interface ICrudService<T> {
    boolean add(T t);
    boolean remove(String id);
    boolean update(T t);
    T get(String id);
    List<T> getAll();
}
