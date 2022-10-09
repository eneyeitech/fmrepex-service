package com.example.fmrepexservice.companymanagement.persistence;


import com.example.fmrepexservice.companymanagement.business.Company;
import com.example.fmrepexservice.companymanagement.database.CompanyStore;
import com.example.fmrepexservice.companymanagement.database.StoreInstance;

public class StoreDAOFactory extends DAOFactory {

    public static StoreInstance createInstance(){
        return CompanyStore.getInstance();
    }

    @Override
    public DAO<Company> getCompanyDAO() {
        return new StoreCompanyDAO();
    }
}
