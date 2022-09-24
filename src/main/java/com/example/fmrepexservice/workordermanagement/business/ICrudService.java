package com.example.fmrepexservice.workordermanagement.business;

import java.util.List;

public interface ICrudService<T> {
    boolean add(T t);
    boolean remove(String id);
    boolean update(T t);
    T get(String id);
    List<T> getAll();
    List<T> getAll(String email);
}
