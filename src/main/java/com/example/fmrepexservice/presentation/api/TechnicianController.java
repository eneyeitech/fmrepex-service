package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIBuildingService;
import com.example.fmrepexservice.business.APIUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TechnicianController {
    private Map<String, Object> feedbackMap;

    @Autowired
    public TechnicianController(){
        feedbackMap = new HashMap<>();
    }
}
