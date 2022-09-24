package com.example.fmrepexservice;

import com.example.fmrepexservice.business.APIUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private APIUserService apiUserService;

    @Autowired
    public DataLoader(APIUserService userService) {
        //this.droneService = droneService;
        //createTables();
        apiUserService = userService;
        initAdmin();
    }

    private void createTables() {
        try {
            System.out.println("Creating tables... ...");
            //droneService.createDroneTable();
            //droneService.createMedicationTable();
        } catch (Exception e) {
            System.out.println("Error occurred creating tables.");
        }
    }

    public void initAdmin(){
        apiUserService.addAdmin("Abdulmumin","admin@gmail.com", "pxstar", "08051185104");
    }
}
