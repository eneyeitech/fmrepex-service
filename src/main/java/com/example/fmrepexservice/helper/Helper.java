package com.example.fmrepexservice.helper;

import com.example.fmrepexservice.security.UserDetailsImpl;
import com.example.fmrepexservice.usermanagement.business.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Helper {


    public static User retrieveUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetailsImpl) auth.getPrincipal()).getUser();
    }
}
