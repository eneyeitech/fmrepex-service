package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIBuildingService;
import com.example.fmrepexservice.business.APIDependantService;
import com.example.fmrepexservice.business.APIRequestService;
import com.example.fmrepexservice.business.APIUserService;
import com.example.fmrepexservice.constant.Status;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Dependant;
import com.example.fmrepexservice.workordermanagement.business.WorkOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DependantController {


    private Map<String, Object> feedbackMap;
    @Autowired
    private APIRequestService requestService;
    @Autowired
    private APIDependantService dependantService;

    @Autowired
    public DependantController(){
        feedbackMap = new HashMap<>();
    }


    @PutMapping("api/dependant/request/{id}/sign-off")
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

        if(user.getUserType() != UserType.DEPENDANT){
            feedbackMap.put("Error", "User not a dependant");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        if(((Dependant)user).getTenantEmail() != request.getTenantEmail()){
            feedbackMap.put("Error", "Dependant not in scope with request");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        dependantService.signOffRequest(user, request);

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
