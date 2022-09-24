package com.example.fmrepexservice.command;

import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.constant.Status;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.user.Manager;
import com.example.fmrepexservice.usermanagement.business.user.Technician;
import com.example.fmrepexservice.workordermanagement.business.WorkOrder;
import com.example.fmrepexservice.workordermanagement.business.WorkOrderService;

public class WorkOrderCommand extends Command{

    protected User loggedInUser;
    protected Request request;
    protected WorkOrder workOrder;
    private WorkOrderService workOrderService;
    private User technicianAssignedToWorkOrder;
    protected boolean workOrderCreated;
    protected boolean workOrderEdited;
    protected boolean workOrderAssigned;
    protected boolean requestActivated;
    protected boolean requestCompleted;


    public WorkOrderCommand(User loggedInUser, User technicianAssignedToWorkOrder, WorkOrder workOrder, WorkOrderService workOrderService){
        this.loggedInUser = loggedInUser;
        this.technicianAssignedToWorkOrder = technicianAssignedToWorkOrder;
        this.request = workOrder.getRequest();
        this.workOrderService = workOrderService;
        this.workOrder = workOrder;
    }

    private void handleRequest(){
        switch (loggedInUser.getUserType()) {
            case MANAGER:
                Manager manager = (Manager) loggedInUser;
                if (!workOrder.hasId() || !workOrder.hasTechnician()){
                    if (request.hasATenant() && manager.getEmail().equalsIgnoreCase(request.getManagerEmail()) && request.getStatus() == Status.PENDING) {
                        createAndAssignWorkOrder();
                    }
                }else{
                    System.out.println("Invalid work order");
                }
                break;
            case TECHNICIAN:
                Technician technician = (Technician) loggedInUser;
                if(technician.hasAManager() && request.hasAManager() && request.getManagerEmail().equalsIgnoreCase(technician.getManagerEmail()) && request.getStatus() == Status.ACTIVE && workOrder.getStatus()== Status.ACTIVE){{
                    completeWorkOrder();
                    break;
                }}
                if(technician.hasAManager() && request.hasAManager() && request.getManagerEmail().equalsIgnoreCase(technician.getManagerEmail()) && request.getStatus() == Status.PENDING && workOrder.getStatus()== Status.PENDING){
                    acceptWorkOrder();
                }

                break;
            case TENANT:
            case DEPENDANT:
            case ADMINISTRATOR:
            default:
        }
    }

    private void createAndAssignWorkOrder(){
        workOrder.setTechnicianEmail(technicianAssignedToWorkOrder.getEmail());
        //request.setStatus(Status.ACTIVE);
        if(workOrderService.add(workOrder)){
            setSuccessful(true);
            setWorkOrderCreated(true);
        }
    }

    public void acceptWorkOrder(){
        workOrder.setStatus(Status.ACTIVE);

        if(workOrderService.update(workOrder)){
            request.setStatus(Status.ACTIVE);
            setSuccessful(true);
            setRequestActivated(true);
        }
    }

    public void completeWorkOrder(){
        workOrder.setStatus(Status.COMPLETED);
        if(workOrderService.update(workOrder)){
            request.setStatus(Status.COMPLETED);
            setSuccessful(true);
            setRequestCompleted(true);
        }
    }

    @Override
    public void actionRequester() {
        handleRequest();
        notifyObservers();
    }

    public boolean isWorkOrderCreated() {
        return workOrderCreated;
    }

    public void setWorkOrderCreated(boolean workOrderCreated) {
        this.workOrderCreated = workOrderCreated;
    }

    public boolean isWorkOrderEdited() {
        return workOrderEdited;
    }

    public void setWorkOrderEdited(boolean workOrderEdited) {
        this.workOrderEdited = workOrderEdited;
    }

    public boolean isWorkOrderAssigned() {
        return workOrderAssigned;
    }

    public void setWorkOrderAssigned(boolean workOrderAssigned) {
        this.workOrderAssigned = workOrderAssigned;
    }

    public boolean isRequestActivated() {
        return requestActivated;
    }

    public void setRequestActivated(boolean requestActivated) {
        this.requestActivated = requestActivated;
    }

    public boolean isRequestCompleted() {
        return requestCompleted;
    }

    public void setRequestCompleted(boolean requestCompleted) {
        this.requestCompleted = requestCompleted;
    }
}
