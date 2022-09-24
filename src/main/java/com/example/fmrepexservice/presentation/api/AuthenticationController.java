package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIAuthenticationService;
import com.example.fmrepexservice.business.SignIn;
import com.example.fmrepexservice.business.SignUp;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AuthenticationController {

    private APIAuthenticationService authenticationService;
    private Map<String, Object> errorsMap;
    private List userTypes;

    @Autowired
    public AuthenticationController(APIAuthenticationService apiAuthenticationService){
        authenticationService = apiAuthenticationService;
        errorsMap = new HashMap<>();
        userTypes = new ArrayList<>(Arrays.asList("admin","tenant", "manager", "technician", "dependant"));
    }


    @PostMapping("api/auth/signup")
    public Object register(@RequestBody SignUp user, @RequestParam String type){
        if(user == null){
            return "Incorrect format";
        }
        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPass();
        String phone = user.getPhone();
        if(type == null || type.trim().isEmpty() || !userTypes.contains(type)){
            errorsMap.put("Error", "user type not valid");
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }
        if(name == null || name.trim().isEmpty() || name.length() < 1){
            errorsMap.put("Error", "name empty (name should have at least 1 character)");
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }
        if(password == null || password.trim().isEmpty() || password.length() < 8){
            errorsMap.put("Error", "password empty (name should have at least 8 character)");
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }
        if(email == null || email.trim().isEmpty() || email.length() < 3){
            errorsMap.put("Error", "email empty (email should have at least 3 character)");
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }
        if(phone == null || phone.trim().isEmpty() || phone.length() < 10){
            errorsMap.put("Error", "phone empty (phone should have at least 10 character)");
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }
        return authenticationService.registerUser(name, email, password, phone, UserFactory.getType(type));
    }

    @PostMapping("api/auth/signin")
    public Object login(@RequestBody SignIn user){
        if(user == null){
            return "Incorrect format";
        }

        String email = user.getEmail();
        String password = user.getPass();


        if(password == null || password.trim().isEmpty() || password.length() < 6){
            errorsMap.put("Error", "password empty (name should have at least 6 character)");
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }
        if(email == null || email.trim().isEmpty() || email.length() < 3){
            errorsMap.put("Error", "email empty (email should have at least 3 character)");
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }


        return authenticationService.loginUser(email, password);
    }


}
