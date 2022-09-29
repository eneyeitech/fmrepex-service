package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIBuildingService;
import com.example.fmrepexservice.business.APITechnicianService;
import com.example.fmrepexservice.business.APIUserService;
import com.example.fmrepexservice.business.APIWorkOrderService;
import com.example.fmrepexservice.constant.Status;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.usermanagement.business.User;
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
public class TechnicianController {
    private Map<String, Object> feedbackMap;

    @Autowired
    private APIWorkOrderService workOrderService;

    @Autowired
    private APITechnicianService technicianService;

    @Autowired
    public TechnicianController(){
        feedbackMap = new HashMap<>();
    }


    @PutMapping("api/technician/work-order/{id}/accept")
    public Object acceptWorkOrder(@PathVariable String id){
        resetFeedback();
        if(!workOrderService.doesWorkOrderExist(id)){
            feedbackMap.put("Error", "Invalid work order");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        WorkOrder workOrder = workOrderService.getWorkOrder(id);

        if(workOrder.getStatus() != Status.PENDING){
            feedbackMap.put("Error", "Only pending work order can be accepted");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        User user = Helper.retrieveUser();

        if(user.getEmail() != workOrder.getTechnicianEmail()){
            feedbackMap.put("Error", "Technician not assigned to work order");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        technicianService.acceptWorkOrder(user, workOrder);

        WorkOrder editedWorkOrder = workOrderService.getWorkOrder(id);

        if(editedWorkOrder.getStatus() == Status.ACTIVE){
            feedbackMap.put("message", "Work order accepted");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
        } else{
            feedbackMap.put("error", "Work order not accepted");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("api/technician/work-order/{id}/complete")
    public Object completeWorkOrder(@PathVariable String id){
        resetFeedback();
        if(!workOrderService.doesWorkOrderExist(id)){
            feedbackMap.put("Error", "Invalid work order");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        WorkOrder workOrder = workOrderService.getWorkOrder(id);

        if(workOrder.getStatus() != Status.ACTIVE){
            feedbackMap.put("Error", "Only an active work order can be marked as complete");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        User user = Helper.retrieveUser();

        if(user.getEmail() != workOrder.getTechnicianEmail()){
            feedbackMap.put("Error", "Technician not assigned to work order");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        technicianService.markWorkOrderAsComplete(user, workOrder);

        WorkOrder editedWorkOrder = workOrderService.getWorkOrder(id);

        if(editedWorkOrder.getStatus() == Status.COMPLETED){
            feedbackMap.put("message", "Work order completed");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
        } else{
            feedbackMap.put("error", "Work order not accepted");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
    }

    public void resetFeedback(){
        feedbackMap = new HashMap<>();
    }
}
