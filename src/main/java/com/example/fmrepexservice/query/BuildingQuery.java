package com.example.fmrepexservice.query;

import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.buildingmanagement.business.BuildingService;
import com.example.fmrepexservice.buildingmanagement.business.ManagedBuilding;
import com.example.fmrepexservice.buildingmanagement.business.State;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class BuildingQuery {


    private BuildingService buildingService;
    @Autowired
    public BuildingQuery(BuildingService buildingService){
        this.buildingService = buildingService;
    }

    public Building getBuilding(String buildingId){
        return buildingService.get(buildingId);
    }

    public List<Building> getBuildings(){
        return buildingService.getAll();
    }

    public List<Building> getBuildingsByManager(String managerEmail){
        return getBuildings().stream().filter(b -> ((ManagedBuilding)b).getManagerEmail().equalsIgnoreCase(managerEmail)).collect(Collectors.toList());
    }

    public List<Building> getBuildingsByState(State state){
        return getBuildings().stream().filter(b -> ((ManagedBuilding)b).getState() == state).collect(Collectors.toList());
    }

    public List<Building> getBuildingsWithCapacityOver(int capacity){
        return getBuildings().stream().filter(b -> ((ManagedBuilding)b).getBuildingFlatCapacity() >= capacity).collect(Collectors.toList());
    }
    public List<Building> getBuildingsWithCapacityBelow(int capacity){
        return getBuildings().stream().filter(b -> ((ManagedBuilding)b).getBuildingFlatCapacity() <= capacity).collect(Collectors.toList());
    }
    public List<Building> getBuildingsByStateWithCapacityOver(int capacity, State state){
        return getBuildingsByState(state).stream().filter(b -> ((ManagedBuilding)b).getBuildingFlatCapacity() >= capacity).collect(Collectors.toList());
    }
    public List<Building> getBuildingsByStateWithCapacityBelow(int capacity, State state){
        return getBuildingsByState(state).stream().filter(b -> ((ManagedBuilding)b).getBuildingFlatCapacity() <= capacity).collect(Collectors.toList());
    }

    public List<Building> getBuildingsAllocated(){
        return getBuildings().stream().filter(b -> ((ManagedBuilding)b).hasTenants()).collect(Collectors.toList());
    }

    public List<Building> getBuildingsAllocatedFully(){
        return getBuildings().stream().filter(b -> ((ManagedBuilding)b).isOccupied()).collect(Collectors.toList());
    }

    public List<Building> getBuildingsNotAllocated(){
        return getBuildings().stream().filter(b -> !((ManagedBuilding)b).hasTenants()).collect(Collectors.toList());
    }

    public List<Building> getBuildingsByStateAllocated(State state){
        return getBuildingsByState(state).stream().filter(b -> ((ManagedBuilding)b).hasTenants()).collect(Collectors.toList());
    }

    public List<Building> getBuildingsByStateAllocatedFully(State state){
        return getBuildingsByState(state).stream().filter(b -> ((ManagedBuilding)b).isOccupied()).collect(Collectors.toList());
    }

    public List<Building> getBuildingsByStateNotAllocated(State state){
        return getBuildingsByState(state).stream().filter(b -> !((ManagedBuilding)b).hasTenants()).collect(Collectors.toList());
    }

}
