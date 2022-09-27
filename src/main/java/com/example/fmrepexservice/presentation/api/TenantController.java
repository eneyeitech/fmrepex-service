package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.*;
import com.example.fmrepexservice.helper.Validator;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class TenantController {

    private APITenantService tenantService;
    @Autowired
    private APIUserService userService;
    private Map<String, Object> feedbackMap;
    private List userTypes;

    @Autowired
    public TenantController(APITenantService apiTenantService){
        tenantService = apiTenantService;
        feedbackMap = new HashMap<>();
        userTypes = new ArrayList<>(Arrays.asList("admin","tenant", "manager", "technician", "dependant"));
    }

    @PostMapping("api/tenant/new/dependant")
    public Object addDependant(@RequestBody SignUp user){

        if(user == null){
            return "Incorrect format";
        }
        if(userService.doesUserExist(user.getEmail())){
            feedbackMap.put("Error", "user already exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.CONFLICT);
        }

        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPass();
        String phone = user.getPhone();

        if(!validateFields(name, email, password, phone)){
            new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        tenantService.registerDependant(name, email, password, phone, UserFactory.getType("dependant"));

        if(userService.doesUserExist(user.getEmail())){
            feedbackMap.put("message", "Dependant successfully added");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
        }else {
            feedbackMap.put("message", "Error occurred adding dependant");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
    }


    private boolean validateFields(String name, String email, String password, String phone){
        if(!Validator.validateName(name)){
            feedbackMap.put("Error", "name empty (name should have at least 1 character)");
            return false;
        }
        if(!Validator.validatePassword(password)){
            feedbackMap.put("Error", "password empty (name should have at least 8 character)");
            return false;
        }
        if(!Validator.validateEmail(email)){
            feedbackMap.put("Error", "email empty (email should have at least 3 character)");
            return false;
        }
        if(!Validator.validatePhone(phone)){
            feedbackMap.put("Error", "phone empty (phone should have at least 10 character)");
            return false;
        }
        return true;
    }

}
