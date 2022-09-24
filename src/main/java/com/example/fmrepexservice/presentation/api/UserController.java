package com.example.fmrepexservice.presentation.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("api/v1/users")
    public String getUsers(){
        return "Hello Users.";
    }
}
