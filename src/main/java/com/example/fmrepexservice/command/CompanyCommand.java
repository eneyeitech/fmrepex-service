package com.example.fmrepexservice.command;

import com.example.fmrepexservice.companymanagement.business.Company;
import com.example.fmrepexservice.companymanagement.business.CompanyService;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.user.Manager;

public class CompanyCommand extends Command{

    protected User loggedInUser;
    protected Company company;
    private CompanyService companyService;
    private UserService userService;

    protected boolean companyCreated;
    protected boolean companyEdited;


    public CompanyCommand(User loggedInUser, Company company, CompanyService companyService, UserService userService){
        this.loggedInUser = loggedInUser;
        this.company = company;
        this.companyService = companyService;
        this.userService = userService;
    }

    private void handleRequest(){
        switch (loggedInUser.getUserType()){
            case MANAGER:
                Manager manager = (Manager) loggedInUser;
                if(!manager.hasCompany()){
                    if(createCompany()){
                        manager.setCompanyId(company.getId());
                        userService.update(manager);
                    }
                }else{
                    if(manager.getCompanyId().equalsIgnoreCase(company.getId())){
                        editCompany();
                    }
                }
                break;
            case TENANT:
            case DEPENDANT:
            case TECHNICIAN:
            case ADMINISTRATOR:
            default:
        }
    }

    private boolean createCompany(){
        if(companyService.add(company)){
            setSuccessful(true);
            setCompanyCreated(true);
            return true;
        }
        return false;
    }

    public boolean editCompany(){
        if(companyService.update(company)){
            setSuccessful(true);
            setCompanyEdited(true);
            return true;
        }
        return false;
    }

    @Override
    public void actionRequester() {
        handleRequest();
        notifyObservers();
    }

    public boolean isCompanyCreated() {
        return companyCreated;
    }

    public void setCompanyCreated(boolean companyCreated) {
        this.companyCreated = companyCreated;
    }

    public boolean isCompanyEdited() {
        return companyEdited;
    }

    public void setCompanyEdited(boolean companyEdited) {
        this.companyEdited = companyEdited;
    }
}
