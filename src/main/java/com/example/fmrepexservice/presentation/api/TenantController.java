package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.*;
import com.example.fmrepexservice.constant.Status;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.helper.Validator;
import com.example.fmrepexservice.requestmanagement.business.Category;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import com.example.fmrepexservice.usermanagement.business.user.Dependant;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class TenantController {

    private APITenantService tenantService;
    @Autowired
    private APIUserService userService;
    @Autowired
    private APIRequestService requestService;
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
        resetFeedback();
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
            //feedbackMap.put("message", "Dependant successfully added");
            return new ResponseEntity<>(userService.getUser(user.getEmail()), HttpStatus.OK);
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

    @PostMapping("api/tenant/new/request")
    public Object newRequest(@RequestBody NewRequest request){
        resetFeedback();
        if(request == null){
            feedbackMap.put("error","incorrect format");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        Tenant user = (Tenant) Helper.retrieveUser();

        if(!user.hasAnAssignedBuilding()){
            feedbackMap.put("error", "Tenant not assigned to a building");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        Request newRequest = new Request();

        newRequest.setAsset(request.getAsset());
        newRequest.setCategory(Category.valueOf(request.getCategory()));
        newRequest.setDescription(request.getDescription());

        newRequest.setTenantEmail(user.getEmail());
        newRequest.setManagerEmail(user.getManagerEmail());
        newRequest.setFlatLabel(user.getFlatNoOrLabel());
        newRequest.setBuildingId(user.getBuildingId());
        newRequest.setCreatedDateTime(LocalDateTime.now());

        String id = tenantService.addRequest(newRequest);

        if(requestService.doesRequestExist(id)){
            feedbackMap.put("message", "Request successfully added");
            return new ResponseEntity<>(requestService.getRequest(id), HttpStatus.OK);
        }else{
            feedbackMap.put("message", "Error occurred adding request");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("api/tenant/request/{id}/sign-off")
    public Object signOff(@PathVariable String id){
        resetFeedback();
        if(!requestService.doesRequestExist(id)){
            feedbackMap.put("Error", "Invalid request");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        Request request = requestService.getRequest(id);

        if(request.getStatus() != Status.COMPLETED){
            feedbackMap.put("Error", "Only completed request can be signed off");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        if(request.isSignedOff()){
            feedbackMap.put("Error", "Request already signed off");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }


        User user = Helper.retrieveUser();

        if(user.getEmail() != request.getTenantEmail()){
            feedbackMap.put("Error", "Tenant not in scope with request");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        tenantService.signOffRequest(user, request);

        Request editedRequest = requestService.getRequest(id);

        if(editedRequest.isSignedOff()){
            feedbackMap.put("message", "Request signed off");
            return new ResponseEntity<>(editedRequest, HttpStatus.OK);
        } else{
            feedbackMap.put("error", "Request not signed off");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
    }

    public void resetFeedback(){
        feedbackMap = new HashMap<>();
    }
}
