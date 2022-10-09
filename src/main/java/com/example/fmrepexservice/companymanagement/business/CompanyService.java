package com.example.fmrepexservice.companymanagement.business;

import com.example.fmrepexservice.companymanagement.persistence.DAO;
import com.example.fmrepexservice.companymanagement.persistence.DAOFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService implements ICrudService<Company> {

    private DAO companyDAO;
    private DAOFactory storeFactory;

    public CompanyService(){
        storeFactory = DAOFactory.getDAOFactory(DAOFactory.STORE);
        companyDAO = storeFactory.getCompanyDAO();
    }

    @Override
    public boolean add(Company company) {
        if(company.hasId()){
            return update(company);
        }else {
            return companyDAO.add(company);
        }

    }

    @Override
    public boolean remove(String id) {
        return companyDAO.remove(id);
    }

    @Override
    public boolean update(Company company) {
        return companyDAO.update(company);
    }

    @Override
    public Company get(String id) {
        return (Company) companyDAO.get(id);
    }

    @Override
    public List<Company> getAll() {
        return companyDAO.getAll();
    }

}
