package com.example.fmrepexservice.authentication;

import com.example.fmrepexservice.Observable;
import com.example.fmrepexservice.Observer;
import com.example.fmrepexservice.usermanagement.business.User;


import java.util.ArrayList;
import java.util.List;

abstract public class UserManagement implements Observable {
    private User user;
    private List<Observer> observers;
    private boolean successful;

    protected UserManagement(User user){
        this.user = user;
        observers = new ArrayList<>();
    }

    abstract public User handle();


    protected User getUser() {
        return user;
    }

    protected boolean isSuccessful() {
        return successful;
    }

    protected void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    @Override
    public void attach(Observer observer) {
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer: observers){
            observer.update(this);
        }
    }
}
