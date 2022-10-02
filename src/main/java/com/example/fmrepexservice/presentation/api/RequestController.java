package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIBuildingService;
import com.example.fmrepexservice.business.APIRequestService;
import com.example.fmrepexservice.business.APIUserService;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RequestController {

    private Map<String, Object> feedbackMap;
    @Autowired
    private APIRequestService requestService;
    @Autowired
    private  APIUserService userService;

    @Autowired
    public RequestController(){
        feedbackMap = new HashMap<>();
    }

    @GetMapping("api/tenant/request")
    public Object getTenantRequest(){
        resetFeedback();
        User tenant = Helper.retrieveUser();

        return requestService.getRequestsByTenant(tenant.getEmail());
    }

    @GetMapping("api/manager/tenant/{email}/request")
    public Object getManagerTenantRequest(@PathVariable String email){
        resetFeedback();
        User tenant = userService.getUser(email);
        if(tenant == null || tenant.getUserType() != UserType.TENANT){
            feedbackMap.put("error", "a tenant not provided");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }

        User manager = Helper.retrieveUser();

        if(!manager.getEmail().equalsIgnoreCase(((Tenant)tenant).getManagerEmail())){
            feedbackMap.put("error", "request not allowed");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }
        return requestService.getRequestsByTenant(email);
    }

    @GetMapping("api/manager/request")
    public Object getManagerRequest(){
        resetFeedback();
        User manager = Helper.retrieveUser();

        return requestService.getRequestsByManager(manager.getEmail());
    }

    @GetMapping("api/building/{bid}/request")
    public Object getBuildingRequest(@PathVariable String bid){
        resetFeedback();

        return requestService.getRequestsByBuilding(bid);
    }

    @GetMapping("api/slug/request")
    public Object editBuilding(@RequestParam String slug){
        return requestService.getRequest(slug);
    }

    @GetMapping("api/request")
    public Object getRequests(){
        return requestService.getRequests();
    }

    @GetMapping("api/request/{id}")
    public Object getRequest(@PathVariable String id){
        return requestService.getRequest(id);
    }

    public void resetFeedback(){
        feedbackMap = new HashMap<>();
    }
}
