package com.example.fmrepexservice.query;

import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Dependant;
import com.example.fmrepexservice.usermanagement.business.user.Technician;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class UserQuery {
    private UserService userService;

    @Autowired
    public UserQuery(UserService service){
        userService = service;
    }

    public User getUser(String id){
        return (User) userService.get(id);
    }
    public List<User> getUsers(){
        return (List<User>) userService.getAll();
    }

    public List<User> getTenants(){
        List<User> users = (List<User>) userService.getAll();

        return users.stream().filter(u -> u.getUserType() == UserType.TENANT).collect(Collectors.toList());
    }

    public List<User> getManagers(){
        List<User> users = (List<User>) userService.getAll();

        return users.stream().filter(u -> u.getUserType() == UserType.MANAGER).collect(Collectors.toList());
    }

    public List<User> getTechnicians(){
        List<User> users = (List<User>) userService.getAll();

        return users.stream().filter(u -> u.getUserType() == UserType.TECHNICIAN).collect(Collectors.toList());
    }

    public List<User> getDependants(){
        List<User> users = (List<User>) userService.getAll();

        return users.stream().filter(u -> u.getUserType() == UserType.DEPENDANT).collect(Collectors.toList());
    }

    public List<User> getAdmins(){
        List<User> users = (List<User>) userService.getAll();

        return users.stream().filter(u -> u.getUserType() == UserType.ADMINISTRATOR).collect(Collectors.toList());
    }

    public List<User> getTenantsByManager(String managerEmail){
        return getTenants().stream().filter(u -> ((Tenant)u).getManagerEmail().equalsIgnoreCase(managerEmail)).collect(Collectors.toList());
    }

    public List<User> getDependantsByTenant(String tenantEmail){
        return getDependants().stream().filter(u -> ((Dependant)u).getTenantEmail().equalsIgnoreCase(tenantEmail)).collect(Collectors.toList());
    }

    public List<User> getTechniciansByManager(String managerEmail){
        return getTechnicians().stream().filter(u -> ((Technician)u).getManagerEmail().equalsIgnoreCase(managerEmail)).collect(Collectors.toList());
    }


}
