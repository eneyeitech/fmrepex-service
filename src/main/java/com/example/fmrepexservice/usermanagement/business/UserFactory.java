package com.example.fmrepexservice.usermanagement.business;

import com.example.fmrepexservice.usermanagement.business.user.*;

public abstract class UserFactory {
    public static User getUser(UserType userType){
        switch (userType){
            case TENANT:
                return new Tenant();
            case MANAGER:
                return new Manager();
            case DEPENDANT:
                return new Dependant();
            case TECHNICIAN:
                return new Technician();
            case ADMINISTRATOR:
                return new Administrator();
            default:
                return null;
        }
    }

    public static UserType getType(String type){
        switch (type){
            case "tenant":
                return UserType.TENANT;
            case "manager":
                return UserType.MANAGER;
            case "dependant":
                return UserType.DEPENDANT;
            case "technician":
                return UserType.TECHNICIAN;
            case "admin":
                return UserType.ADMINISTRATOR;
            default:
                return null;
        }
    }
}
