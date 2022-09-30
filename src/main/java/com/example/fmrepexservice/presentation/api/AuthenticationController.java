package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIAuthenticationService;
import com.example.fmrepexservice.business.APIUserService;
import com.example.fmrepexservice.business.SignIn;
import com.example.fmrepexservice.business.SignUp;
import com.example.fmrepexservice.helper.Validator;
import com.example.fmrepexservice.security.UserDetailsImpl;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AuthenticationController {

    private APIAuthenticationService authenticationService;
    @Autowired
    private APIUserService userService;
    private Map<String, Object> feedbackMap;
    private List userTypes;

    @Autowired
    public AuthenticationController(APIAuthenticationService apiAuthenticationService){
        authenticationService = apiAuthenticationService;
        feedbackMap = new HashMap<>();
        userTypes = new ArrayList<>(Arrays.asList("admin","tenant", "manager", "technician", "dependant"));
    }


    @PostMapping("api/auth/signup")
    public Object register(@RequestBody SignUp user, @RequestParam String type){

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

        if(!validateFields(name, email, password, phone, type)){
            new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        return authenticationService.registerUser(name, email, password, phone, UserFactory.getType(type));
    }

    private boolean validateFields(String name, String email, String password, String phone, String type){
        if(!Validator.validateType(type)){
            feedbackMap.put("Error", "user type not valid");
            return false;
        }
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

    @PostMapping("api/auth/signin")
    public Object login(@RequestBody SignIn user){
        if(user == null){
            return "Incorrect format";
        }

        String email = user.getEmail();
        String password = user.getPass();
System.out.println(password);

        if(password == null || password.trim().isEmpty() || password.length() < 4){
            feedbackMap.put("Error", "password empty (password should have at least 4 character)");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
        if(email == null || email.trim().isEmpty() || email.length() < 3){
            feedbackMap.put("Error", "email empty (email should have at least 3 character)");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        User signedInUser = authenticationService.loginUser(email, password);
        feedbackMap = new HashMap<>();
        if (signedInUser != null){
            feedbackMap.put("user", signedInUser);
            feedbackMap.put("status", "User login successful.");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
        }else{
            feedbackMap.put("error", "invalid credentials");
            return new ResponseEntity<>(feedbackMap, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("api/auth/{email}/verify")
    public Object verify(@PathVariable String email){
        User userToVerify = userService.getUser(email);
        if(userToVerify == null){
            feedbackMap.put("Error", "user does not exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.NOT_FOUND);
        }
        if(!userToVerify.isVerified()){
            return authenticationService.verifyUser(email);
        }
        feedbackMap.put("Error", "user is already verified");
        return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("api/auth/{email}/lock")
    public Object lock(@PathVariable String email){
        User userToLock = userService.getUser(email);
        if(userToLock == null){
            feedbackMap.put("Error", "user does not exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.NOT_FOUND);
        }
        if(!userToLock.getLocked()){
            return authenticationService.lockUnlockUser(email);
        }
        feedbackMap.put("Error", "user is already locked");
        return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("api/auth/{email}/unlock")
    public Object unlock(@PathVariable String email){
        User userToUnLock = userService.getUser(email);
        if(userToUnLock == null){
            feedbackMap.put("Error", "user does not exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.NOT_FOUND);
        }
        if(userToUnLock.getLocked()){
            return authenticationService.lockUnlockUser(email);
        }
        feedbackMap.put("Error", "user is already unlocked");
        return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
    }

    @PutMapping ("api/auth/{email}/approve")
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


}
