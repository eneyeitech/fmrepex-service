package com.example.fmrepexservice.query;

import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class UserQuery {
    private UserService userService;

    @Autowired
    public UserQuery(UserService service){
        userService = service;
    }

    public List<User> getUsers(){
        return (List<User>) userService.getAll();
    }

    public List<User> getUsersByManager(String email){
        return (List<User>) userService.getAll();
    }
}
