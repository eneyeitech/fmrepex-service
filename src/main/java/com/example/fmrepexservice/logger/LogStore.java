package com.example.fmrepexservice.logger;

import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.buildingmanagement.database.BuildingStore;
import com.example.fmrepexservice.buildingmanagement.database.StoreInstance;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LogStore {
    private static LogStore instance;
    private static int key;
    private ConcurrentHashMap<Integer, Log> store = new ConcurrentHashMap<>();

    private LogStore(){

    }

    public static LogStore getInstance(){
        if(instance == null){
            instance = new LogStore();
        }
        return instance;
    }

    /**public ConcurrentHashMap<Integer, Log> getStore(){
        return store;
    }*/

    public void addToLog(Log log){
        if(log != null){
            store.put(key++, log);
        }
    }

    public List<Log> getLogs(){
        return store.values().stream().collect(Collectors.toList());
    }
}
