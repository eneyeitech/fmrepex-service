package com.example.fmrepexservice.usermanagement.business.user;

import com.example.fmrepexservice.usermanagement.business.User;

public class YetToLogin extends User {
    public YetToLogin(String username, String password){
        setEmail(username);
        setPassword(password);
    }
}
