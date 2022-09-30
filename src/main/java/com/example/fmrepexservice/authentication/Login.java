package com.example.fmrepexservice.authentication;

import com.example.fmrepexservice.security.PasswordEncoderConfig;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Login extends UserManagement{
    private UserService userService;
    private PasswordEncoderConfig passwordEncoderConfig;
    public Login(User userToLogin, UserService userService){
        super(userToLogin);
        this.userService = userService;
        passwordEncoderConfig = new PasswordEncoderConfig();
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

        if(user != null && passwordEncoderConfig.getEncoder().matches(getUser().getPassword(), user.getPassword())){
            return user;
        }

        return null;
    }
}
