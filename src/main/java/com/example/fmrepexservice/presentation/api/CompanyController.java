package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APICompanyService;
import com.example.fmrepexservice.business.APIUserService;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CompanyController {

    private Map<String, Object> feedbackMap;
    @Autowired
    private APICompanyService companyService;
    @Autowired
    private APIUserService userService;

    @Autowired
    public CompanyController(){
        feedbackMap = new HashMap<>();
    }

    @GetMapping("api/manager/company")
    public Object getManagerCompany(){
        resetFeedback();
        User user = Helper.retrieveUser();
        if(user.getUserType() == UserType.MANAGER && ((Manager) user).hasCompany()){
            return companyService.getCompany(((Manager) user).getCompanyId());
        } else {
            feedbackMap.put("message", "company information unavailable");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }
    }

    public void resetFeedback(){
        feedbackMap = new HashMap<>();
    }
}
