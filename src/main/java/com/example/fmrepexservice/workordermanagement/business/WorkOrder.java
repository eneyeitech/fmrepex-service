package com.example.fmrepexservice.workordermanagement.business;

import com.example.fmrepexservice.constant.Status;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.workordermanagement.helper.WorkOrderIdGenerator;

import java.time.LocalDateTime;

public class WorkOrder {
    private String id = null;
    private Request request;
    private String technicianEmail;
    private LocalDateTime createdDateTime;
    private LocalDateTime scheduledDateTime;
    private LocalDateTime assignedDateTime;
    private LocalDateTime completedDateTime;
    private String description;
    private Status status;


    {
        setStatus(Status.PENDING);
    }
    public WorkOrder(){

    }

    public WorkOrder(WorkOrderIdGenerator workOrderIdGenerator){
        id = workOrderIdGenerator.generate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(this.id == null){
            this.id = id;
        }
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getTechnicianEmail() {
        return technicianEmail;
    }

    public void setTechnicianEmail(String technicianEmail) {
        this.technicianEmail = technicianEmail;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getScheduledDateTime() {
        return scheduledDateTime;
    }

    public void setScheduledDateTime(LocalDateTime scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
    }

    public LocalDateTime getAssignedDateTime() {
        return assignedDateTime;
    }

    public void setAssignedDateTime(LocalDateTime assignedDateTime) {
        this.assignedDateTime = assignedDateTime;
    }

    public LocalDateTime getCompletedDateTime() {
        return completedDateTime;
    }

    public void setCompletedDateTime(LocalDateTime completedDateTime) {
        this.completedDateTime = completedDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean hasTechnician(){
        return technicianEmail != null;
    }

    public boolean hasId(){
        return getId() != null;
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
                "id='" + id + '\'' +
                ", request=" + request +
                ", technicianEmail='" + technicianEmail + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", scheduledDateTime=" + scheduledDateTime +
                ", assignedDateTime=" + assignedDateTime +
                ", completedDateTime=" + completedDateTime +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
