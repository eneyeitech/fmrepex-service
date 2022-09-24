package com.example.fmrepexservice.usermanagement.business.user;

import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserType;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User {
    private List<Tenant> tenantsList;
    private List<Technician> techniciansList;

    {
        tenantsList = new ArrayList<>();
        techniciansList = new ArrayList<>();
        this.setUserType(UserType.MANAGER);
    }

    public Manager(){

    }

    public boolean addTenant(Tenant tenant){
        String emailToAdd = tenant.getEmail();
        for(User t: tenantsList){
            if(t.getEmail().equals(emailToAdd)){
                return false;
            }
        }
        tenant.setManagerEmail(this.getEmail());
        tenantsList.add(tenant);
        return true;
    }

    public boolean removeTenant(Tenant tenant){
        if(tenantsList.contains(tenant)){
            return tenantsList.remove(tenant);
        }
        return false;
    }

    public boolean addTechnician(Technician technician){
        String emailToAdd = technician.getEmail();
        for(User t: techniciansList){
            if(t.getEmail().equals(emailToAdd)){
                return false;
            }
        }
        technician.setManagerEmail(this.getEmail());
        techniciansList.add(technician);
        return true;
    }

    public boolean removeTechnician(Technician technician){
        if(techniciansList.contains(technician)){
            return techniciansList.remove(technician);
        }
        return false;
    }

    public List<Tenant> getTenantsList() {
        return new ArrayList<>(tenantsList);
    }

    public void setTenantsList(List<Tenant> tenantsList) {
        this.tenantsList = new ArrayList<>(tenantsList);
    }

    public List<Technician> getTechniciansList() {
        return new ArrayList<>(techniciansList);
    }

    public void setTechniciansList(List<Technician> techniciansList) {
        this.techniciansList = new ArrayList<>(techniciansList);
    }

    public boolean hasTenants(){
        return (tenantsList != null && tenantsList.size() > 0);
    }

    public boolean hasTechnicians(){
        return (techniciansList != null && techniciansList.size() > 0);
    }
}
