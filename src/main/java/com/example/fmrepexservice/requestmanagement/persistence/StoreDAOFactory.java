package com.example.fmrepexservice.requestmanagement.persistence;

import com.example.fmrepexservice.requestmanagement.database.RequestStore;
import com.example.fmrepexservice.requestmanagement.database.StoreInstance;

public class StoreDAOFactory extends DAOFactory{

    public static StoreInstance createInstance(){
        return RequestStore.getInstance();
    }

    @Override
    public DAO getRequestDAO() {
        return new StoreRequestDAO();
    }
}
