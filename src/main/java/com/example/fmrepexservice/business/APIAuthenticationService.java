package com.example.fmrepexservice.business;

import com.example.fmrepexservice.authentication.*;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.YetToLogin;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class APIAuthenticationService {

    public User loginUser(String email, String password){

        UserManagement loginManager = new Login(new YetToLogin(email, password), new UserService());
        new SecurityMonitor(loginManager);
        new GeneralLogger(loginManager);
        new EmailNotifier(loginManager);

        return loginManager.handle();
    }

    public User registerUser(String name, String email, String password, String phoneNumber, UserType type){
        User newUser = UserFactory.getUser(type);

        newUser.setFullName(name.toUpperCase(Locale.ROOT));
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setPassword(password);

        UserManagement registerManager = new Registration(newUser, new UserService());
        new SecurityMonitor(registerManager);
        new GeneralLogger(registerManager);
        new EmailNotifier(registerManager);
        return registerManager.handle();
    }


}
