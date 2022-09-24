package com.example.fmrepexservice.command;

import com.example.fmrepexservice.Observable;
import com.example.fmrepexservice.Observer;
import com.example.fmrepexservice.authentication.UserManagement;

abstract public class CommandObserver implements Observer {
    private Command command;

    public CommandObserver(Command command){
        this.command = command;
        this.command.attach(this);
    }
    @Override
    public void update(Observable observable) {
        if(observable.equals(command)){
            doUpdate(command);
        }
    }

    abstract public void doUpdate(Command command);
}
