package com.example.fmrepexservice.usermanagement.business.user;

import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserType;

import java.util.ArrayList;
import java.util.List;

public class Tenant extends User {

    private List<Dependant> dependantsList;
    private String managerEmail = null;
    private String buildingId = null;
    private String flatNoOrLabel = null;

    {
        dependantsList = new ArrayList<>();
        this.setUserType(UserType.TENANT);
        this.setApproved(true);
    }

    public Tenant(){

    }

    public boolean addDependant(Dependant dependant){
        String emailToAdd = dependant.getEmail();
        for(User t: dependantsList){
            if(t.getEmail().equals(emailToAdd)){
                return false;
            }
        }
        dependant.setTenantEmail(this.getEmail());
        dependantsList.add(dependant);
        return true;
    }

    public boolean removeDependant(Dependant dependant){
        if(dependantsList.contains(dependant)){
            return dependantsList.remove(dependant);
        }
        return false;
    }

    public List<User> getDependantsList() {
        return new ArrayList<>(dependantsList);
    }

    public void setDependantsList(List<Dependant> dependantsList) {
        this.dependantsList = new ArrayList<>(dependantsList);
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getFlatNoOrLabel() {
        return flatNoOrLabel;
    }

    public void setFlatNoOrLabel(String flatNoOrLabel) {
        this.flatNoOrLabel = flatNoOrLabel;
    }

    public boolean hasAnAssignedBuilding(){
        return buildingId != null;
    }

    public boolean hasAFlat(){
        return flatNoOrLabel != null;
    }

    public boolean hasAManager(){
        return managerEmail != null;
    }

    public boolean hasDependants(){
        return (dependantsList != null && dependantsList.size() > 0);
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "managerEmail='" + managerEmail + '\'' +
                ", buildingId='" + buildingId + '\'' +
                '}';
    }
}
