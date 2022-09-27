package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIAuthenticationService;
import com.example.fmrepexservice.business.APIBuildingService;
import com.example.fmrepexservice.business.APIUserService;
import org.springframework.beans.factory.annotation.Autowired;
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


}
