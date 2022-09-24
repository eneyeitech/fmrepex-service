package com.example.fmrepexservice.buildingmanagement.business;

import com.example.fmrepexservice.buildingmanagement.helper.BuildingIdGenerator;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;

import java.util.ArrayList;
import java.util.List;

public class ManagedBuilding extends Building {
    private String managerEmail = null;
    private List<Tenant> buildingOccupants;

    public ManagedBuilding(){
        buildingOccupants = new ArrayList<>();
    }

    public ManagedBuilding(BuildingIdGenerator buildingIdGenerator){
        id = buildingIdGenerator.generate();
        buildingOccupants = new ArrayList<>();
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public boolean addTenant(Tenant tenant){
        if(getNoOfOccupants()==0){
            buildingOccupants = new ArrayList<>(getBuildingFlatCapacity());
        }
        String emailToAdd = tenant.getEmail();
        for(User t: buildingOccupants){
            if(t.getEmail().equals(emailToAdd)){
                return false;
            }
        }
        tenant.setBuildingId(getId());
        buildingOccupants.add(tenant);
        return true;

    }

    public boolean removeTenant(Tenant tenant){
        if(buildingOccupants.contains(tenant)){
            if(buildingOccupants.remove(tenant)){
                tenant.setBuildingId(null);
                tenant.setFlatNoOrLabel(null);
                return true;
            }
        }
        return false;
    }
    public List<Tenant> getBuildingOccupants() {
        return new ArrayList<>(buildingOccupants);
    }

    public void setBuildingOccupants(List<Tenant> buildingOccupants) {
        this.buildingOccupants = new ArrayList<>(buildingOccupants);
    }

    public int getBuildingFlatCapacity(){
        return getNoOfFlats();
    }

    public int getNoOfOccupants(){
        return buildingOccupants.size();
    }

    public int getNoOfFlatsAvailable(){
        if(isOccupied()){
            return 0;
        }
        return getBuildingFlatCapacity() - getNoOfOccupants();
    }

    public boolean isOccupied(){
        return getNoOfOccupants() == getBuildingFlatCapacity();
    }

    public boolean hasAManager(){
        return managerEmail != null;
    }

    public boolean hasTenants(){
        return (buildingOccupants != null && buildingOccupants.size() > 0);
    }

    @Override
    public String toString() {
        return "ManagedBuilding{" +
                "managerEmail='" + managerEmail + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", noOfFlats=" + noOfFlats +
                ", address=" + address +
                ", coordinate=" + coordinate +
                '}';
    }
}
