package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIUserService;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.usermanagement.business.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private APIUserService userService;

    @Autowired
    public UserController(APIUserService apiUserService){
        userService = apiUserService;
    }

    @GetMapping("api/users")
    public Object getUsers(){
        return userService.getUsers();
    }

    @GetMapping("api/manager/users")
    public Object getUsersByManager(){
        User user = Helper.retrieveUser();
        return userService.getUsersByManager(user.getEmail());
    }

    @GetMapping("api/manager/{email}/tenant")
    public Object getTenantsByManager(@PathVariable String email){
        return userService.getTenantsByManager(email);
    }

    @GetMapping("api/tenant/{email}/dependant")
    public Object getDependantsByTenant(@PathVariable String email){
        return userService.getDependantsByTenant(email);
    }

    @GetMapping("api/tenant/building/{id}")
    public Object getTenantsByBuilding(@PathVariable String id){
        return userService.getTenantsByBuilding(id);
    }

    @GetMapping("api/dependant/building/{id}")
    public Object getDependantsByBuilding(@PathVariable String id){
        return userService.getDependantsByBuilding(id);
    }

    @GetMapping("api/tenant")
    public Object getTenants(){
        return userService.getTenants();
    }

    @GetMapping("api/dependant")
    public Object getDependants(){
        return userService.getDependants();
    }

    @GetMapping("api/user/{id}")
    public Object getUser(@PathVariable String id){
        return userService.getUser(id);
    }

    @GetMapping("api/manager")
    public Object getManagers(){
        return userService.getManagers();
    }
}
