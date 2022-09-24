package com.example.fmrepexservice.builder;

import com.example.fmrepexservice.workordermanagement.business.WorkOrder;
import com.example.fmrepexservice.workordermanagement.business.WorkOrderService;
import com.example.fmrepexservice.workordermanagement.helper.WorkOrderIdGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AuthorisedWorkOrderBuilder {
    private WorkOrderService workOrderService;
    private Scanner scanner;
    private WorkOrder workOrder;
    private String technicianEmail;
    private LocalDateTime createdDateTime;
    private LocalDateTime scheduledDateTime;
    private LocalDateTime assignedDateTime;
    private LocalDateTime completedDateTime;
    private String description;
    public AuthorisedWorkOrderBuilder(Scanner scanner){
        this.scanner = scanner;
        description = getString("Enter brief work description (note to technician): ");
    }

    public AuthorisedWorkOrderBuilder(WorkOrderService workOrderService){
        this.workOrderService = workOrderService;
    }

    public WorkOrder queryWorkOrder(String workOrderId){
        return workOrderService.get(workOrderId);
    }

    public List<WorkOrder> queryWorkOrders(){
        return workOrderService.getAll();
    }

    public List<WorkOrder> queryWorkOrders(String technicianEmail){
        return workOrderService.getAll(technicianEmail);
    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public WorkOrder getWorkOrder(){
        //workOrder = new WorkOrder(new WorkOrderIdGenerator(10));
        workOrder = new WorkOrder();

        workOrder.setDescription(description);
        workOrder.setCreatedDateTime(LocalDateTime.now());
        return workOrder;
    }
}
