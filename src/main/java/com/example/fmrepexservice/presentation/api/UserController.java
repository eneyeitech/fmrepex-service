package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIUserService;
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

    @GetMapping("api/manager/{email}")
    public Object getUsersByManager(@PathVariable String email){
        return userService.getTenantsByManager(email);
    }

    @GetMapping("api/manager")
    public Object getManagers(){
        return userService.getManagers();
    }
}
