package com.example.fmrepexservice.requestmanagement.business;

import com.example.fmrepexservice.constant.Action;

public interface ISubject {
    public void registerObserver(IObserver observer);
    public void removeObserver(IObserver observer);
    public void notifyObservers(Request r, String id, Action a);
}
