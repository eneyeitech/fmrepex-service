package com.example.fmrepexservice.usermanagement.business.user;

import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserType;

public class Dependant extends User {

    private String tenantEmail = null;

    {
        this.setUserType(UserType.DEPENDANT);
        this.setApproved(true);
    }

    public String getTenantEmail() {
        return tenantEmail;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }

    public boolean hasAParent(){
        return tenantEmail != null;
    }

    @Override
    public String toString() {
        return "Dependant{" +
                "tenantEmail='" + tenantEmail + '\'' +
                '}';
    }
}
