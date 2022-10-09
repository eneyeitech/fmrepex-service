package com.example.fmrepexservice.companymanagement.persistence;

import com.example.fmrepexservice.companymanagement.exception.TableException;
import com.example.fmrepexservice.companymanagement.business.Company;
import com.example.fmrepexservice.companymanagement.database.CompanyStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StoreCompanyDAO extends DAO<Company> {

    public StoreCompanyDAO(){
        store = CompanyStore.getInstance().getStore();
    }

    @Override
    public boolean add(Company company) {
        if(company.hasId()){
            return update(company);
        }
        company.setId(companyIdGenerator.generate());
        Company buildingInStore = store.put(company.getId(), company);
        if(buildingInStore == null){
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String id) {
        Company company = store.remove(id);
        if(company == null){
            return false;
        }
        return true;
    }

    @Override
    public Company get(String id) {
        return store.get(id);
    }

    @Override
    public List<Company> getAll() {
        List<Company> list = new ArrayList<>();
        for(ConcurrentHashMap.Entry<String, Company> m: store.entrySet()){
            list.add(m.getValue());
        }
        Collections.sort(list, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        return list;
    }

    @Override
    public boolean update(Company company) {
        Company companyInStore = store.put(company.getId(), company);
        if(companyInStore == null){
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        store.clear();
    }

    @Override
    public void createTable() throws TableException {

    }

    @Override
    public void dropTable() throws TableException {

    }


}
