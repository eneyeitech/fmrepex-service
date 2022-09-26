package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class AdministratorController {

    @Autowired
    APIUserService apiUserService;


    public Object listUsers(){
        return apiUserService.getUsers();
    }
}
