package com.example.fmrepexservice.authentication;


public class EmailNotifier extends UserManagementObserver {

    public EmailNotifier(UserManagement userManagement){
        super(userManagement);
    }
    @Override
    public void doUpdate(UserManagement userManagement) {
        if(userManagement.isSuccessful()){
            if(userManagement instanceof Login){
                System.out.println("Successfully logged in");
            } else if(userManagement instanceof Registration){
                System.out.println("Successfully registered");
            }
            System.out.println("Sending email to user!");
        }
    }
}
