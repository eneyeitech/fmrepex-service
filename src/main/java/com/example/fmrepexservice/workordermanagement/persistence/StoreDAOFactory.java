package com.example.fmrepexservice.workordermanagement.persistence;

import com.example.fmrepexservice.workordermanagement.database.RequestStore;
import com.example.fmrepexservice.workordermanagement.database.StoreInstance;

public class StoreDAOFactory extends DAOFactory {

    public static StoreInstance createInstance(){
        return RequestStore.getInstance();
    }

    @Override
    public DAO getRequestDAO() {
        return new StoreRequestDAO();
    }

    @Override
    public DAO getWorkOrderDAO() {
        return new StoreWorkOrderDAO();
    }
}
