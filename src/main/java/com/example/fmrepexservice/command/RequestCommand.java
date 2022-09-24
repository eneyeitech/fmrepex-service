package com.example.fmrepexservice.command;

import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.requestmanagement.business.RequestService;
import com.example.fmrepexservice.constant.Status;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.user.Dependant;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;

public class RequestCommand extends Command{

    protected User loggedInUser;
    protected Request request;
    private RequestService requestService;

    protected boolean requestCreated;
    protected boolean requestEdited;


    public RequestCommand(User loggedInUser, Request request, RequestService requestService){
        this.loggedInUser = loggedInUser;
        this.request = request;
        this.requestService = requestService;
    }

    private void handleRequest(){
        switch (loggedInUser.getUserType()){
            case TENANT:
                Tenant tenant = (Tenant) loggedInUser;
                if(tenant.hasAnAssignedBuilding()){
                    if(request.hasATenant() && request.getTenantEmail().equalsIgnoreCase(tenant.getEmail()) && !request.isSignedOff() &&  request.getStatus() == Status.COMPLETED){
                        signOffRequest();
                        break;
                    }
                    System.out.println(request);
                    request.setManagerEmail(tenant.getManagerEmail());
                    createRequest();
                }else{
                    System.out.println("Tenant not assigned a building");
                }
                break;
            case DEPENDANT:
                Dependant dependant = (Dependant) loggedInUser;
                if(dependant.hasAParent() && request.hasATenant() && request.getTenantEmail().equalsIgnoreCase(dependant.getTenantEmail()) && !request.isSignedOff() &&  request.getStatus() == Status.COMPLETED){
                    signOffRequest();
                }
                break;
            case MANAGER:
            case TECHNICIAN:
            case ADMINISTRATOR:
            default:
        }
    }

    private boolean createRequest(){
        if(requestService.add(request)){
            setSuccessful(true);
            setRequestCreated(true);
            return true;
        }
        return false;
    }

    public void signOffRequest(){
        if(!request.isSignedOff()){
            request.setSignedOff(true);
            setSuccessful(true);
            setRequestEdited(true);
        }
    }

    @Override
    public void actionRequester() {
        handleRequest();
        notifyObservers();
    }

    public boolean isRequestCreated() {
        return requestCreated;
    }

    public void setRequestCreated(boolean requestCreated) {
        this.requestCreated = requestCreated;
    }

    public boolean isRequestEdited() {
        return requestEdited;
    }

    public void setRequestEdited(boolean requestEdited) {
        this.requestEdited = requestEdited;
    }
}
