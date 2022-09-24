package com.example.fmrepexservice.buildingmanagement.database;

import com.example.fmrepexservice.buildingmanagement.business.Building;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BuildingStore extends StoreInstance {
    private static BuildingStore instance;
    private ConcurrentHashMap<String, Building> store = new ConcurrentHashMap<>();

    private BuildingStore(){

    }

    public static StoreInstance getInstance(){
        if(instance == null){
            instance = new BuildingStore();
        }
        return instance;
    }

    public ConcurrentHashMap<String, Building> getStore(){
        return store;
    }
}
