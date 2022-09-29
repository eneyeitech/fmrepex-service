package com.example.fmrepexservice.business;

import com.example.fmrepexservice.command.Command;
import com.example.fmrepexservice.command.WorkOrderCommand;
import com.example.fmrepexservice.security.PasswordEncoderConfig;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.workordermanagement.business.WorkOrder;
import com.example.fmrepexservice.workordermanagement.business.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class APITechnicianService {

    private PasswordEncoderConfig passwordEncoderConfig;


    @Autowired
    public APITechnicianService(PasswordEncoderConfig passwordEncoderConfig){
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    public void acceptWorkOrder(User user, WorkOrder workOrder){

        Command command = new WorkOrderCommand(user, null, workOrder, new WorkOrderService());
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
    }


    public void markWorkOrderAsComplete(User user, WorkOrder workOrder){

        Command command = new WorkOrderCommand(user, null, workOrder, new WorkOrderService());
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
    }

    public void getWorkOrderStatus(){

    }
}
