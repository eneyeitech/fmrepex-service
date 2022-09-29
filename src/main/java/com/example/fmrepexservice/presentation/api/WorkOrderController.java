package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIBuildingService;
import com.example.fmrepexservice.business.APIRequestService;
import com.example.fmrepexservice.business.APIUserService;
import com.example.fmrepexservice.business.APIWorkOrderService;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Technician;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WorkOrderController {
    private Map<String, Object> feedbackMap;
    @Autowired
    private APIWorkOrderService workOrderService;
    @Autowired
    private  APIUserService userService;

    @Autowired
    public WorkOrderController(){
        feedbackMap = new HashMap<>();
    }

    @GetMapping("api/work-order")
    public Object getWorkOrders(){
        return workOrderService.getWorkOrders();
    }

    @GetMapping("api/work-order/{id}")
    public Object getWorkOrder(@PathVariable String id){
        return workOrderService.getWorkOrder(id);
    }

    @GetMapping("api/technician/work-order")
    public Object getTechnicianWorkOrder(){
        resetFeedback();
        User technician = Helper.retrieveUser();

        return workOrderService.getWorkOrdersByTechnician(technician.getEmail());
    }

    @GetMapping("api/manager/work-order")
    public Object getWorkOrderByManager(){
        resetFeedback();
        User manager = Helper.retrieveUser();

        return workOrderService.getWorkOrdersByManager(manager.getEmail());
    }

    @GetMapping("api/manager/technician/{email}/work-order")
    public Object getManagerTechnicianWorkOrder(@PathVariable String email){
        resetFeedback();
        User technician = userService.getUser(email);
        if(technician == null || technician.getUserType() != UserType.TECHNICIAN){
            feedbackMap.put("error", "a tenant not provided");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }

        User manager = Helper.retrieveUser();

        if(!manager.getEmail().equalsIgnoreCase(((Technician)technician).getManagerEmail())){
            feedbackMap.put("error", "request not allowed");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }
        return workOrderService.getWorkOrdersByTechnician(email);
    }

    public void resetFeedback(){
        feedbackMap = new HashMap<>();
    }
}
