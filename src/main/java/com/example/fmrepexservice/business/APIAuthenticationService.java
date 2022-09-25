package com.example.fmrepexservice.business;

import com.example.fmrepexservice.authentication.*;
import com.example.fmrepexservice.builder.UserBuilder;
import com.example.fmrepexservice.security.Group;
import com.example.fmrepexservice.security.PasswordEncoderConfig;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.YetToLogin;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class APIAuthenticationService {

    private List<Group> groupList;
    private PasswordEncoderConfig passwordEncoderConfig;
    public APIAuthenticationService(PasswordEncoderConfig passwordEncoderConfig){
        this.passwordEncoderConfig = passwordEncoderConfig;
        groupList = new ArrayList<>();
        groupList.add(new Group("ROLE_MANAGER", "Manager Group"));
        groupList.add(new Group("ROLE_USER", "User Group"));
        groupList.add(new Group("ROLE_ADMINISTRATOR", "Administrator Group"));
        groupList.add(new Group("ROLE_TENANT", "Tenant Group"));
        groupList.add(new Group("ROLE_TECHNICIAN", "Technician Group"));
        groupList.add(new Group("ROLE_DEPENDANT", "Dependant Group"));
    }

    public User loginUser(String email, String password){

        UserManagement loginManager = new Login(new YetToLogin(email, password), new UserService());
        new SecurityMonitor(loginManager);
        new GeneralLogger(loginManager);
        new EmailNotifier(loginManager);

        return loginManager.handle();
    }

    public User registerUser(String name, String email, String password, String phoneNumber, UserType type){

        User newUser = UserFactory.getUser(type);

        newUser.addUserGroups(UserFactory.getGroup(type));

        newUser.setFullName(name.toUpperCase(Locale.ROOT));
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setPassword(password);

        newUser.setPassword(passwordEncoderConfig.getEncoder().encode(newUser.getPassword()));
        newUser.setLocked(true);

        UserManagement registerManager = new Registration(newUser, new UserService());
        new SecurityMonitor(registerManager);
        new GeneralLogger(registerManager);
        new EmailNotifier(registerManager);
        return registerManager.handle();
    }

    public User updateUser(User userToUpdate){
        UserManagement registerManager = new Registration(userToUpdate, new UserService());
        new SecurityMonitor(registerManager);
        new GeneralLogger(registerManager);
        new EmailNotifier(registerManager);
        return registerManager.handle();
    }



    public User verifyUser(String email){

        UserBuilder builder = new UserBuilder(new UserService());
        User user = builder.queryUser(email);
        if(user != null && !user.isVerified()){
            user.setVerified(true);
            user.setLocked(false);
            return updateUser(user);
        }
        return null;
    }

    public User approveUser(String email){

        UserBuilder builder = new UserBuilder(new UserService());
        User user = builder.queryUser(email);
        if(user != null && !user.isApproved()){
            user.setApproved(true);
            return updateUser(user);
        }
        return null;
    }

    public User lockUnlockUser(String email){

        UserBuilder builder = new UserBuilder(new UserService());
        User user = builder.queryUser(email);
        if(user != null){
            user.setLocked(!user.getLocked());
            return updateUser(user);
        }
        return null;
    }
}
