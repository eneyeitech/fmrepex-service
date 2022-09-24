package com.example.fmrepexservice.command;

import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.user.Dependant;
import com.example.fmrepexservice.usermanagement.business.user.Manager;
import com.example.fmrepexservice.usermanagement.business.user.Technician;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;

public class UserCommand extends Command{
    protected User loggedInUser;
    protected User userToRegister;
    private UserService userService;
    protected boolean added;
    protected boolean approved;

    public UserCommand(User loggedInUser, User userToRegister, UserService userService){
        this.loggedInUser=loggedInUser;
        this.userToRegister = userToRegister;
        this.userService = userService;
        added = false;
    }

    private void handleRegister(){
        switch (loggedInUser.getUserType()){
            case MANAGER:
                if(loggedInUser.isApproved()) {
                    switch (userToRegister.getUserType()) {
                        case TENANT:
                            registerTenant();
                            break;
                        case TECHNICIAN:
                            registerTechnician();
                            break;
                        default:
                    }
                } else {
                    System.out.println("Manager not approved");
                }
                break;
            case TENANT:
                switch (userToRegister.getUserType()){
                    case DEPENDANT:
                        registerDependant();
                        break;
                    default:
                }
                break;
            case DEPENDANT:
            case TECHNICIAN:
            case ADMINISTRATOR:
                switch (userToRegister.getUserType()){
                    case MANAGER:
                        approveManager();
                        break;
                    default:
                }
                break;
            default:
        }
    }

    private void approveManager(){
        Manager manager = (Manager) userToRegister;

        if(!manager.isApproved()){
            manager.setApproved(true);
            setApproved(true);
            setSuccessful(true);
        }
    }

    private void registerTenant(){
        ((Tenant) userToRegister).setManagerEmail(loggedInUser.getEmail());
        if(userService.add(userToRegister)){
            if(userService.addTenantToManager(loggedInUser, userToRegister)){
                setAdded(true);
                setSuccessful(true);
            }
        }
    }

    private void registerTechnician(){
        ((Technician) userToRegister).setManagerEmail(loggedInUser.getEmail());
        if(userService.add(userToRegister)){
            if(userService.addTechnicianToManager(loggedInUser, userToRegister)){
                setAdded(true);
                setSuccessful(true);
            }
        }
    }

    private void registerDependant(){
        ((Dependant) userToRegister).setTenantEmail(loggedInUser.getEmail());
        if(userService.add(userToRegister)){
            if(userService.addDependantToTenant(loggedInUser, userToRegister)){
                setAdded(true);
                setSuccessful(true);
            }
        }
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    @Override
    public void actionRequester() {
        handleRegister();
        notifyObservers();
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
