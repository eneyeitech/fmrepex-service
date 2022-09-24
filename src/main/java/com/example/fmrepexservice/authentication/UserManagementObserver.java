package com.example.fmrepexservice.authentication;

import com.example.fmrepexservice.Observable;
import com.example.fmrepexservice.Observer;

abstract public class UserManagementObserver implements Observer {

    private UserManagement userManagement;

    public UserManagementObserver(UserManagement userManagement){
        this.userManagement = userManagement;
        this.userManagement.attach(this);
    }

    @Override
    public void update(Observable observable) {
        if(observable.equals(userManagement)){
            doUpdate(userManagement);
        }
    }

    abstract public void doUpdate(UserManagement userManagement);
}
