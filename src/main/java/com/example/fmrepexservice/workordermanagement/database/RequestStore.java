package com.example.fmrepexservice.workordermanagement.database;

import com.example.fmrepexservice.requestmanagement.business.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestStore extends StoreInstance {
    private static RequestStore instance;
    private ConcurrentHashMap<String, List<Request>> store = new ConcurrentHashMap<>();

    private RequestStore(){

    }

    public static StoreInstance getInstance(){
        if(instance == null){
            instance = new RequestStore();
        }
        return instance;
    }

    public ConcurrentHashMap<String, List<Request>> getStore(){
        return store;
    }

    @Override
    public void updateStore(ConcurrentHashMap cm) {
        store = new ConcurrentHashMap<>(cm);
        System.out.println("STORE::"+store);
    }
}
