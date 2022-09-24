package com.example.fmrepexservice.buildingmanagement.persistence;

import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.buildingmanagement.database.BuildingStore;
import com.example.fmrepexservice.buildingmanagement.database.StoreInstance;


public class StoreDAOFactory extends DAOFactory {

    public static StoreInstance createInstance(){
        return BuildingStore.getInstance();
    }

    @Override
    public DAO<Building> getBuildingDAO() {
        return new StoreBuildingDAO();
    }
}
