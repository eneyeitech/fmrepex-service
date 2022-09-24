package com.example.fmrepexservice.buildingmanagement.persistence;

import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.buildingmanagement.database.BuildingStore;
import com.example.fmrepexservice.buildingmanagement.exception.TableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StoreBuildingDAO extends DAO<Building>{

    public StoreBuildingDAO(){
        store = BuildingStore.getInstance().getStore();
    }

    @Override
    public boolean add(Building building) {
        if(building.hasId()){
            return update(building);
        }
        building.setId(buildingIdGenerator.generate());
        Building buildingInStore = store.put(building.getId(), building);
        if(buildingInStore == null){
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String id) {
        Building building = store.remove(id);
        if(building == null){
            return false;
        }
        return true;
    }

    @Override
    public Building get(String id) {
        return store.get(id);
    }

    @Override
    public List<Building> getAll() {
        List<Building> list = new ArrayList<>();
        for(ConcurrentHashMap.Entry<String, Building> m: store.entrySet()){
            list.add(m.getValue());
        }
        Collections.sort(list, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        return list;
    }

    @Override
    public boolean update(Building building) {
        Building buildingInStore = store.put(building.getId(), building);
        if(buildingInStore == null){
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        store.clear();
    }

    @Override
    public void createTable() throws TableException {

    }

    @Override
    public void dropTable() throws TableException {

    }
}
