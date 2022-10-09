package com.example.fmrepexservice.query;

import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.buildingmanagement.business.BuildingService;
import com.example.fmrepexservice.buildingmanagement.business.ManagedBuilding;
import com.example.fmrepexservice.companymanagement.business.Company;
import com.example.fmrepexservice.companymanagement.business.CompanyService;
import com.example.fmrepexservice.constant.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class CompanyQuery {


    private CompanyService companyService;
    @Autowired
    public CompanyQuery(CompanyService companyService){
        this.companyService = companyService;
    }

    public Company getCompany(String companyId){
        return companyService.get(companyId);
    }

    public List<Company> getCompanies(){
        return companyService.getAll();
    }

    public List<Company> getCompaniesByState(State state){
        return getCompanies().stream().filter(c -> (c.getState() == state)).collect(Collectors.toList());
    }


}
