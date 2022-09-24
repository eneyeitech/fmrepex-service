package com.example.fmrepexservice.requestmanagement.database;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class StoreInstance<T> {
    abstract public ConcurrentHashMap<String, T> getStore();
}
