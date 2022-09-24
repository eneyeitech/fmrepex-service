package com.example.fmrepexservice.authentication;

import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserService;

public class Login extends UserManagement{
    private UserService userService;
    public Login(User userToLogin, UserService userService){
        super(userToLogin);
        this.userService = userService;
    }
    @Override
    public User handle() {
        User loggedInUser = login();
        if(loggedInUser == null){
            setSuccessful(false);
        }else{
            setSuccessful(true);
        }
        notifyObservers();
        return loggedInUser;
    }

    public User login(){
        User user = (User) userService.get(getUser().getEmail());

        if(user != null && getUser().getPassword().equals(user.getPassword())){
            return user;
        }

        return null;
    }
}
