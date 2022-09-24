package com.example.fmrepexservice.authentication;

public class SecurityMonitor extends UserManagementObserver {

    public SecurityMonitor(UserManagement userManagement){
        super(userManagement);
    }

    @Override
    public void doUpdate(UserManagement userManagement) {
        if(userManagement.isSuccessful()){
            System.out.println("Sending email to admin!");
        }
    }
}
