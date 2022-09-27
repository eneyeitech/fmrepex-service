package com.example.fmrepexservice.command;

import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.buildingmanagement.business.BuildingService;
import com.example.fmrepexservice.buildingmanagement.business.ManagedBuilding;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.user.Manager;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;

public class BuildingCommand extends Command{

    protected User loggedInUser;
    protected User tenantToAdd;
    protected Building building;
    private BuildingService buildingService;
    protected boolean buildingCreated;
    protected boolean buildingEdited;
    protected boolean buildingAssigned;
    protected boolean buildingDeAssigned;



    public BuildingCommand(User loggedInUser, User tenantToAdd, Building building, BuildingService buildingService){
        this.loggedInUser = loggedInUser;
        this.tenantToAdd = tenantToAdd;
        this.building = building;
        this.buildingService = buildingService;
    }

    private void handleRequest(){
        switch (loggedInUser.getUserType()){
            case MANAGER:
                Manager manager = (Manager) loggedInUser;
                if(manager.isApproved()){
                    if(building.hasId()){
                        ManagedBuilding managedBuilding = (ManagedBuilding) building;
                        if(tenantToAdd == null){
                            if(managedBuilding.getManagerEmail().equalsIgnoreCase(manager.getEmail())) {
                                if(buildingService.get(building.getId()) == null){
                                    createBuilding();
                                } else {
                                    editBuilding();
                                }

                            }
                        }else{
                            Tenant tenant = (Tenant) tenantToAdd;
                            if(tenant.hasAnAssignedBuilding() && tenant.getManagerEmail().equalsIgnoreCase(manager.getEmail()) && managedBuilding.getManagerEmail().equalsIgnoreCase(manager.getEmail()) && tenant.getBuildingId().equalsIgnoreCase(managedBuilding.getId())){
                                deAssignBuilding();
                                break;
                            }

                            if(!tenant.hasAnAssignedBuilding() && tenant.getManagerEmail().equalsIgnoreCase(manager.getEmail()) && managedBuilding.getManagerEmail().equalsIgnoreCase(manager.getEmail())){
                                assignBuilding();
                                break;
                            }

                        }
                    }else {
                        createBuilding();
                    }
                }else{
                    System.out.println("Manager not approved");
                }
                break;
            case TENANT:
            case DEPENDANT:
            case TECHNICIAN:
            case ADMINISTRATOR:
            default:
        }
    }

    private void createBuilding(){
        if(buildingService.add(building)){
            setSuccessful(true);
            setBuildingCreated(true);
        }
    }
    private void assignBuilding(){
        if(buildingService.assignTenantToBuilding(building, tenantToAdd)){
            setSuccessful(true);
            setBuildingAssigned(true);
        }
    }

    private void deAssignBuilding(){
        if(buildingService.deAssignTenantToBuilding(building, tenantToAdd)){
            setSuccessful(true);
            setBuildingDeAssigned(true);
        }
    }

    private void editBuilding(){
        if(buildingService.add(building)){
            setSuccessful(true);
            setBuildingEdited(true);
        }
    }


    @Override
    public void actionRequester() {
        handleRequest();
        notifyObservers();
    }

    public boolean isBuildingCreated() {
        return buildingCreated;
    }

    public void setBuildingCreated(boolean buildingCreated) {
        this.buildingCreated = buildingCreated;
    }

    public boolean isBuildingEdited() {
        return buildingEdited;
    }

    public void setBuildingEdited(boolean buildingEdited) {
        this.buildingEdited = buildingEdited;
    }

    public boolean isBuildingAssigned() {
        return buildingAssigned;
    }

    public void setBuildingAssigned(boolean buildingAssigned) {
        this.buildingAssigned = buildingAssigned;
    }

    public boolean isBuildingDeAssigned() {
        return buildingDeAssigned;
    }

    public void setBuildingDeAssigned(boolean buildingDeAssigned) {
        this.buildingDeAssigned = buildingDeAssigned;
    }
}
