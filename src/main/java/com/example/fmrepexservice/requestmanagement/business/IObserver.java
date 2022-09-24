package com.example.fmrepexservice.requestmanagement.business;

import com.example.fmrepexservice.constant.Action;

public interface IObserver {
    public void execute(Request r, String id, Action a);
}
