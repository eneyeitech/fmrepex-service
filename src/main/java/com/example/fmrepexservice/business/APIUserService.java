package com.example.fmrepexservice.business;


import com.example.fmrepexservice.authentication.*;
import com.example.fmrepexservice.query.UserQuery;
import com.example.fmrepexservice.security.PasswordEncoderConfig;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@Component
public class APIUserService {

    private UserQuery query;
    private UserService userService;
    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;
    @Autowired
    public APIUserService(UserQuery userQuery, UserService userService){
        query = userQuery;
        this.userService = userService;
    }

    public User addAdmin(String name, String email, String password, String phoneNumber){
        User admin = UserFactory.getUser(UserType.ADMINISTRATOR);

        admin.addUserGroups(UserFactory.getGroup(UserType.ADMINISTRATOR));

        admin.setFullName(name.toUpperCase(Locale.ROOT));
        admin.setEmail(email);
        admin.setPhoneNumber(phoneNumber);
        admin.setPassword(password);

        admin.setPassword(passwordEncoderConfig.getEncoder().encode(admin.getPassword()));
        admin.setLocked(false);


        UserManagement registerManager = new Registration(admin, new UserService());
        new SecurityMonitor(registerManager);
        new GeneralLogger(registerManager);
        new EmailNotifier(registerManager);
        return registerManager.handle();
    }

    public User getUser(String email){
        return query.getUser(email);
    }
    public boolean doesUserExist(String email){
        return getUser(email) != null;
    }
    public List<User> getUsers(){
        return query.getUsers();
    }

    public List<User> getUsersByManager(String email){
        return query.getUsersByManager(email);
    }

    public List<User> getTenantsByManager(String email){
        return query.getTenantsByManager(email);
    }

    public List<User> getDependantsByTenant(String email){
        return query.getDependantsByTenant(email);
    }

    public List<User> getTenantsByBuilding(String id){
        return query.getTenantsByBuilding(id);
    }

    public List<User> getDependantsByBuilding(String id){
        return query.getDependantsByBuilding(id);
    }

    public List<User> getTenants(){
        return query.getTenants();
    }

    public List<User> getDependants(){
        return query.getDependants();
    }

    public List<User> getManagers(){
        return query.getManagers();
    }

    public void deleteUser(String email){
        userService.remove(email);
    }
}
