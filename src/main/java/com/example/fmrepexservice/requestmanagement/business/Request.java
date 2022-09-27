package com.example.fmrepexservice.requestmanagement.business;

import com.example.fmrepexservice.constant.Status;
import com.example.fmrepexservice.requestmanagement.helper.RequestIdGenerator;

import java.time.LocalDateTime;

public class Request {
    private String id = null;
    private String description;
    private String asset;
    private Category category;
    private Status status;
    private LocalDateTime createdDateTime;
    private String tenantEmail = null;
    private String buildingId = null;
    private String managerEmail = null;

    private String flatLabel = null;
    private String workOrderId = null;

    private boolean signedOff = false;
    {
        setStatus(Status.PENDING);
    }

    public Request(){

    }

    public Request(RequestIdGenerator requestIdGenerator){
        id = requestIdGenerator.generate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(this.id == null){
            this.id = id;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getTenantEmail() {
        return tenantEmail;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getFlatLabel() {
        return flatLabel;
    }

    public void setFlatLabel(String flatLabel) {
        this.flatLabel = flatLabel;
    }

    public boolean hasATenant(){
        return tenantEmail != null;
    }

    public boolean hasAManager(){
        return managerEmail != null;
    }

    public boolean hasABuilding(){
        return buildingId != null;
    }

    public boolean hasAFlatLabel(){
        return flatLabel != null;
    }

    public boolean isSignedOff() {
        return signedOff;
    }

    public void setSignedOff(boolean signedOff) {
        this.signedOff = signedOff;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public boolean hasWorkOrderId(){
        return workOrderId != null;
    }

    public boolean hasId(){
        return getId() != null;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", asset='" + asset + '\'' +
                ", category=" + category +
                ", status=" + status +
                ", createdDateTime=" + createdDateTime +
                ", tenantEmail='" + tenantEmail + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", managerEmail='" + managerEmail + '\'' +
                ", flatLabel='" + flatLabel + '\'' +
                ", signedOff=" + signedOff +
                '}';
    }
}
