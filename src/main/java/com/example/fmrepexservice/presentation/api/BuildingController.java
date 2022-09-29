package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIAuthenticationService;
import com.example.fmrepexservice.business.APIBuildingService;
import com.example.fmrepexservice.business.APIUserService;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.usermanagement.business.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BuildingController {

    private APIBuildingService buildingService;
    @Autowired
    private APIUserService userService;
    private Map<String, Object> feedbackMap;

    @Autowired
    public BuildingController(APIBuildingService apiBuildingService){
        buildingService = apiBuildingService;
        feedbackMap = new HashMap<>();
    }


    @GetMapping("api/manager/building")
    public Object getManagerBuildings(){
        resetFeedback();
        User manager = Helper.retrieveUser();
      /**  if(!manager.getEmail().equalsIgnoreCase(email)){
            feedbackMap.put("error", "request not allowed");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }*/
        return buildingService.getBuildingsByManager(manager.getEmail());
    }

    @GetMapping("api/building/{id}")
    public Object getBuilding(@PathVariable String id){
        return buildingService.getBuilding(id);
    }

    @GetMapping("api/building")
    public Object getBuildings(){
        return buildingService.getBuildings();
    }

    public void resetFeedback(){
        feedbackMap = new HashMap<>();
    }
}
