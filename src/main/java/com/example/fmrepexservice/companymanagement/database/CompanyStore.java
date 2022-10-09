package com.example.fmrepexservice.companymanagement.database;

import com.example.fmrepexservice.companymanagement.business.Company;

import java.util.concurrent.ConcurrentHashMap;

public class CompanyStore extends StoreInstance {
    private static CompanyStore instance;
    private ConcurrentHashMap<String, Company> store = new ConcurrentHashMap<>();

    private CompanyStore(){

    }

    public static StoreInstance getInstance(){
        if(instance == null){
            instance = new CompanyStore();
        }
        return instance;
    }

    public ConcurrentHashMap<String, Company> getStore(){
        return store;
    }
}
