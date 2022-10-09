package com.example.fmrepexservice.companymanagement.persistence;

import com.example.fmrepexservice.companymanagement.exception.TableException;
import com.example.fmrepexservice.companymanagement.business.Company;

import java.util.List;

public class MYSQLCompanyDAO extends DAO<Company> {
    @Override
    public boolean add(Company company) {
        return false;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public Company get(String id) {
        return null;
    }

    @Override
    public List<Company> getAll() {
        return null;
    }

    @Override
    public boolean update(Company company) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void createTable() throws TableException {

    }

    @Override
    public void dropTable() throws TableException {

    }


}
