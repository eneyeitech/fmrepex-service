package com.example.fmrepexservice.usermanagement.persistence;

public abstract class DAOFactory {
    // List of DAO types supported by the factory
    public static final int SQLITE3 = 1;
    public static final int MYSQL = 2;
    public static final int STORE = 3;

    // There will be a method for each DAO that can be
    // created. The concrete factories will have to
    // implement these methods.

    public abstract DAO getUserDAO();


    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case SQLITE3:
                return new MYSQLDAOFactory();
            case MYSQL:
                return null;
            case STORE:
                return new StoreDAOFactory();
            default:
                return null;
        }
    }
}
