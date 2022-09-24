package com.example.fmrepexservice.usermanagement.persistence;

import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.database.StoreInstance;
import com.example.fmrepexservice.usermanagement.database.UserStore;

public class StoreDAOFactory extends DAOFactory{

    public static StoreInstance createInstance(){
        return UserStore.getInstance();
    }

    @Override
    public DAO<User> getUserDAO() {
        return new StoreUserDAO();
    }
}
