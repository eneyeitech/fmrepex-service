package com.example.fmrepexservice.workordermanagement.database;

import com.example.fmrepexservice.requestmanagement.business.Request;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WorkOrderStore extends StoreInstance{

    private static WorkOrderStore instance;
    private ConcurrentHashMap<String, List<Request>> store = new ConcurrentHashMap<>();

    private WorkOrderStore(){

    }

    public static StoreInstance getInstance(){
        if(instance == null){
            instance = new WorkOrderStore();
        }
        return instance;
    }

    @Override
    public ConcurrentHashMap getStore() {
        return store;
    }

    @Override
    public void updateStore(ConcurrentHashMap cm) {
        System.out.println(cm);
    }
}
