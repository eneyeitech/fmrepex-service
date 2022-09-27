package com.example.fmrepexservice.business;

import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.query.BuildingQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class APIBuildingService {

    @Autowired
    private BuildingQuery query;


    public Building getBuilding(String id){
        return query.getBuilding(id);
    }
    public boolean doesBuildingExist(String id){
        return getBuilding(id) != null;
    }

    public List<Building> getBuildings(){
        return query.getBuildings();
    }

    public List<Building> getBuildingsByManager(String email){
        return query.getBuildingsByManager(email);
    }
}
