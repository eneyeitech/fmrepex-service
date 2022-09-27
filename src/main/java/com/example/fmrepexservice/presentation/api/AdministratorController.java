package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIAuthenticationService;
import com.example.fmrepexservice.business.APIUserService;
import com.example.fmrepexservice.logger.LogStore;
import com.example.fmrepexservice.usermanagement.business.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class AdministratorController {

    private APIAuthenticationService authenticationService;
    @Autowired
    private APIUserService userService;
    private Map<String, Object> feedbackMap;
    private List userTypes;

    @Autowired
    public AdministratorController(APIAuthenticationService apiAuthenticationService){
        authenticationService = apiAuthenticationService;
        feedbackMap = new HashMap<>();
        userTypes = new ArrayList<>(Arrays.asList("admin","tenant", "manager", "technician", "dependant"));
    }


    @PutMapping("api/admin/{email}/approve")
    public Object approve(@PathVariable String email){
        User userToLock = userService.getUser(email);
        if(userToLock == null){
            feedbackMap.put("Error", "user does not exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.NOT_FOUND);
        }
        if(!userToLock.isApproved()){
            return authenticationService.approveUser(email);
        }
        feedbackMap.put("Error", "user is already approved");
        return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("api/admin/log")
    public Object logs(){
        LogStore logStore = LogStore.getInstance();
        return logStore.getLogs();
    }

    @GetMapping("api/admin/user")
    public Object getUsers(){
        return userService.getUsers();
    }
}
