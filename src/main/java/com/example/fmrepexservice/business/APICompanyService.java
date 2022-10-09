package com.example.fmrepexservice.business;

import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.companymanagement.business.Company;
import com.example.fmrepexservice.constant.State;
import com.example.fmrepexservice.query.BuildingQuery;
import com.example.fmrepexservice.query.CompanyQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class APICompanyService {

    @Autowired
    private CompanyQuery query;

    public Company getCompany(String id){
        return query.getCompany(id);
    }
    public boolean doesCompanyExist(String id){
        return getCompany(id) != null;
    }

    public List<Company> getCompanies(){
        return query.getCompanies();
    }

    public List<Company> getCompaniesByState(State state){
        return query.getCompaniesByState(state);
    }

}
