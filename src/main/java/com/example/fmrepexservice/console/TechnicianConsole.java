package com.example.fmrepexservice.console;

import com.example.fmrepexservice.builder.AuthorisedWorkOrderBuilder;
import com.example.fmrepexservice.command.Command;
import com.example.fmrepexservice.command.WorkOrderCommand;
import com.example.fmrepexservice.constant.Status;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.workordermanagement.business.WorkOrder;
import com.example.fmrepexservice.workordermanagement.business.WorkOrderService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TechnicianConsole extends Console{
    private WorkOrderService workOrderService;
    public TechnicianConsole(Scanner scanner, User user){
        super(scanner, user);
        workOrderService = new WorkOrderService();
    }

    public String menu(){
        return "" +
                "1. View Work Orders\n" +
                "2. View Work Order\n" +
                "3. Accept Work Order\n" +
                "4. View Status of a Work Order\n" +
                "5. View accepted Work Orders\n" +
                "6. Mark Work Order Complete\n" +
                "0. Back\n" +
                "";
    }
    @Override
    public void menuDisplay() {
        System.out.println(menu());
    }

    @Override
    public int handleSelection() {
        int selection = getSelectedNumber();
        switch (selection){
            case 1:
                listWorkOrders();
                return 1;
            case 2:
                getWorkOrder();
                return 2;
            case 3:
                acceptWorkOrder();
                return 3;
            case 4:
                getWorkOrderStatus();
                return 4;
            case 5:
                listAcceptedWorkOrders();
                return 5;
            case 6:
                markWorkOrderAsComplete();
                return 6;
            case 0:
                return 0;
            default:
                return 99;
        }
    }

    public void acceptWorkOrder(){
        showPrompt("Accept a work order");
        WorkOrder workOrder = getWorkOrder();
        if(workOrder == null){
            return;
        }
        Command command = new WorkOrderCommand(user, null, workOrder, workOrderService);
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
    }

    public void markWorkOrderAsComplete(){
        showPrompt("Accept a work order");
        WorkOrder workOrder = getWorkOrder();
        if(workOrder == null){
            return;
        }

        Command command = new WorkOrderCommand(user, null, workOrder, workOrderService);
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
    }

    public void getWorkOrderStatus(){
        WorkOrder workOrder = getWorkOrder();
        if(workOrder == null){
            return;
        }
        System.out.println(workOrder.getStatus().toString());
    }

    public WorkOrder getWorkOrder(){
        if (!isTechnician()){
            return null;
        }
        showPrompt("Get a work order");
        String workOrderId = getWorkOrderId();
        AuthorisedWorkOrderBuilder workOrderBuilder = new AuthorisedWorkOrderBuilder(workOrderService);
        WorkOrder workOrder = workOrderBuilder.queryWorkOrder(workOrderId);

        if (workOrder==null){
            System.out.println("Work order does not exist!");
        }else{
            if(!assignedWorkOrder(workOrder)){
                System.out.println("Technician not allowed");
                return null;
            }
            System.out.println(workOrder);
        }
        return workOrder;
    }

    public void listWorkOrders(){
        if (!isTechnician()){
            return;
        }
        showPrompt("List of work orders!");
        List<WorkOrder> list;
        AuthorisedWorkOrderBuilder workOrderBuilder = new AuthorisedWorkOrderBuilder(workOrderService);
        list = workOrderBuilder.queryWorkOrders(user.getEmail());
        if(list == null){
            return;
        }
        int i = 0;
        for(WorkOrder workOrder:list){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formatDateTime = workOrder.getCreatedDateTime().format(format);
            System.out.printf("%s: (%s) %s - %s.\n", ++i, workOrder.getId(), workOrder.getRequest().getAsset(), formatDateTime);
        }
    }

    public void listAcceptedWorkOrders(){
        if (!isTechnician()){
            return;
        }
        showPrompt("List of work orders!");
        List<WorkOrder> list;
        AuthorisedWorkOrderBuilder workOrderBuilder = new AuthorisedWorkOrderBuilder(workOrderService);
        list = workOrderBuilder.queryWorkOrders(user.getEmail());
        int i = 0;
        for(WorkOrder workOrder:list){
            if(workOrder.getStatus() == Status.ACTIVE) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String formatDateTime = workOrder.getCreatedDateTime().format(format);
                System.out.printf("%s: (%s) %s - %s.\n", ++i, workOrder.getId(), workOrder.getRequest().getAsset(), formatDateTime);
            }
        }
    }


    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private String getWorkOrderId(){
        return getString("Enter work order id: ");
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    private boolean isTechnician(){
        if(user != null && user.getUserType() != UserType.TECHNICIAN){
            System.out.println("User not a technician");
            return false;
        }
        return true;
    }

    private boolean assignedWorkOrder(WorkOrder workOrder){
        if(user.getEmail() == workOrder.getTechnicianEmail()){
            return true;
        }
        return false;
    }
}
