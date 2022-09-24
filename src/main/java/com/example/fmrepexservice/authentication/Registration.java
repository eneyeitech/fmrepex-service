package com.example.fmrepexservice.authentication;

import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserService;

public class Registration extends UserManagement {
    private UserService userService;

    public Registration(User userToRegister, UserService userService){
        super(userToRegister);
        this.userService = userService;
    }

    private boolean register(){
        return userService.add(getUser());
    }

    @Override
    public User handle() {
        if(register()){
            setSuccessful(true);
        }else{
            setSuccessful(false);
        }
        notifyObservers();
        return getUser();
    }
}
