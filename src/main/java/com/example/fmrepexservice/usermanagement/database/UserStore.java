package com.example.fmrepexservice.usermanagement.database;

import com.example.fmrepexservice.usermanagement.business.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserStore extends StoreInstance{
    private static UserStore instance;
    private ConcurrentHashMap<String, User> store = new ConcurrentHashMap<>();

    private UserStore(){

    }

    public static StoreInstance getInstance(){
        if(instance == null){
            instance = new UserStore();
        }
        return instance;
    }

    public ConcurrentHashMap<String, User> getStore(){
        return store;
    }
}
