package com.example.fmrepexservice.authentication;

public class GeneralLogger extends UserManagementObserver {

    public GeneralLogger(UserManagement userManagement){
        super(userManagement);
    }
    @Override
    public void doUpdate(UserManagement userManagement) {
        System.out.println("Logging data");
    }
}
