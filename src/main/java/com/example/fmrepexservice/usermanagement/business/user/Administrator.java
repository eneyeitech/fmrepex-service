package com.example.fmrepexservice.usermanagement.business.user;

import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserType;

public class Administrator extends User {
    {
        this.setUserType(UserType.ADMINISTRATOR);
        this.setApproved(true);
    }
}
