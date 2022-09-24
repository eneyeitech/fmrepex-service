package com.example.fmrepexservice.buildingmanagement.business;

import com.example.fmrepexservice.buildingmanagement.persistence.DAO;
import com.example.fmrepexservice.buildingmanagement.persistence.DAOFactory;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Manager;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;

import java.util.List;

public class BuildingService implements ICrudService<Building>{

    private DAO buildingDAO;
    private DAOFactory storeFactory;

    public BuildingService(){
        storeFactory = DAOFactory.getDAOFactory(DAOFactory.STORE);
        buildingDAO = storeFactory.getBuildingDAO();
    }

    @Override
    public boolean add(Building building) {
        if(building.hasId()){
            return update(building);
        }else {
            return buildingDAO.add(building);
        }

    }

    @Override
    public boolean remove(String id) {
        return buildingDAO.remove(id);
    }

    @Override
    public boolean update(Building building) {
        return buildingDAO.update(building);
    }

    @Override
    public Building get(String id) {
        return (Building) buildingDAO.get(id);
    }

    @Override
    public List<Building> getAll() {
        return buildingDAO.getAll();
    }

    public List<Tenant> getOccupants(String buildingId) {

        Building building = get(buildingId);

        if(building == null){
            return null;
        }

        return ((ManagedBuilding) building).getBuildingOccupants();
    }

    public boolean assignTenantToBuilding(Building building, User tenant){
        if(((ManagedBuilding) building).isOccupied()){
            System.out.println("Building is full");
            return false;
        }
        if(tenant.getUserType()!=UserType.TENANT){
            System.out.println("User not a tenant");
            return false;
        }

        boolean added =  ((ManagedBuilding) building).addTenant((Tenant) tenant);
        if(added){
            return true;
        }
        System.out.println("Tenant assigned successful");
        return false;
    }
    public boolean deAssignTenantToBuilding(Building building, User tenant){

        if(tenant.getUserType()!=UserType.TENANT){
            System.out.println("User not a tenant");
            return false;
        }

        boolean removed =  ((ManagedBuilding) building).removeTenant((Tenant) tenant);
        if(removed){
            return true;
        }
        System.out.println("Tenant not removed");
        return false;
    }

    public int getBuildingCapacity(String buildingId){
        Building building = get(buildingId);

        if(building == null){
            return 0;
        }

        return ((ManagedBuilding) building).getBuildingFlatCapacity();
    }

    public int getNoOfFlatsAvailable(String buildingId){
        Building building = get(buildingId);

        if(building == null){
            return 0;
        }

        return ((ManagedBuilding) building).getNoOfFlatsAvailable();
    }

    public int getNoOfOccupants(String buildingId){
        Building building = get(buildingId);

        if(building == null){
            return 0;
        }

        return ((ManagedBuilding) building).getNoOfOccupants();
    }

    public boolean isOccupied(String buildingId){
        Building building = get(buildingId);

        if(building == null){
            return false;
        }

        return ((ManagedBuilding) building).isOccupied();
    }
}
