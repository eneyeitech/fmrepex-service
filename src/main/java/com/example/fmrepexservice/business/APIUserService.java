package com.example.fmrepexservice.business;


import com.example.fmrepexservice.authentication.*;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.UserType;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class APIUserService {

    public User addAdmin(String name, String email, String password, String phoneNumber){
        User admin = UserFactory.getUser(UserType.ADMINISTRATOR);

        admin.setFullName(name.toUpperCase(Locale.ROOT));
        admin.setEmail(email);
        admin.setPhoneNumber(phoneNumber);
        admin.setPassword(password);

        UserManagement registerManager = new Registration(admin, new UserService());
        new SecurityMonitor(registerManager);
        new GeneralLogger(registerManager);
        new EmailNotifier(registerManager);
        return registerManager.handle();
    }








}
