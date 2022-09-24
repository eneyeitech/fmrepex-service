package com.example.fmrepexservice.console;

import com.example.fmrepexservice.builder.*;
import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.buildingmanagement.business.BuildingService;
import com.example.fmrepexservice.buildingmanagement.business.ManagedBuilding;
import com.example.fmrepexservice.command.BuildingCommand;
import com.example.fmrepexservice.command.Command;
import com.example.fmrepexservice.command.UserCommand;
import com.example.fmrepexservice.command.WorkOrderCommand;
import com.example.fmrepexservice.constant.Status;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.requestmanagement.business.RequestService;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Manager;
import com.example.fmrepexservice.usermanagement.business.user.Technician;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;
import com.example.fmrepexservice.workordermanagement.business.WORequestService;
import com.example.fmrepexservice.workordermanagement.business.WorkOrder;
import com.example.fmrepexservice.workordermanagement.business.WorkOrderService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagerConsole extends Console{

    private UserService userService;
    private BuildingService buildingService;
    private RequestService tenantRequestService;
    private WORequestService managementRequestService;
    private WorkOrderService workOrderService;
    private AuthorisedRequestBuilder authorisedRequestBuilder;

    public ManagerConsole(Scanner scanner, User user){
        super(scanner, user);
        this.userService = new UserService();
        this.buildingService = new BuildingService();
        tenantRequestService = new RequestService();
        managementRequestService = new WORequestService(tenantRequestService);
        workOrderService = new WorkOrderService();
        authorisedRequestBuilder = new AuthorisedRequestBuilder(tenantRequestService, managementRequestService);
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
                addTenant();
                return 1;
            case 2:
                removeTenant();
                return 2;
            case 3:
                listTenants();
                return 3;
            case 4:
                addTechnician();
                return 4;
            case 5:
                removeTechnician();
                return 5;
            case 6:
                listTechnicians();
                return 6;
            case 7:
                managerDetails();
                return 7;
            case 8:
                addBuilding();
                return 8;
            case 9:
                removeBuilding();
                return 9;
            case 10:
                listBuildings();
                return 10;
            case 11:
                assignTenant();
                return 8;
            case 12:
                deAssignTenant();
                return 9;
            case 13:
                listBuildingOccupants();
                return 10;
            case 14:
                listTenantRequests();
                return 8;
            case 15:
                listManagerRequestsView();
                return 9;
            case 16:
                getRequest();
                return 10;
            case 17:
                createWorkOrder();
                return 10;
            case 18:
                listWorkOrders();
                return 10;
            case 19:
                getWorkOrder();
                return 10;
            case 20:
                listAcceptedWorkOrders();
                return 10;
            case 0:
                return 0;
            default:
                return 10;

        }
    }

    private void printMessage(String message){
        System.out.println(message);
    }

    private String menu(){
        return "" +
                "1. Add Tenant\n" +
                "2. Remove Tenant\n" +
                "3. List tenants\n" +
                "4. Add Technician\n" +
                "5. Remove Technician\n" +
                "6. List Technicians\n" +
                "7. Manager details\n" +
                "8. Add Building\n" +
                "9. Remove Building\n" +
                "10. List Buildings\n" +
                "11. Assign tenant to building\n" +
                "12. De-assign tenant to building\n" +
                "13. List building tenants\n" +
                "14. List tenants request\n" +
                "15. List all request\n" +
                "16. View Request\n" +
                "17. Create Work Order\n" +
                "18. List Work Orders\n" +
                "19. View Work Order\n" +
                "20. View Accepted Work Orders\n" +
                "0. Back\n" +
                "";
    }

    protected void addTenant(){
        printMessage("Add Tenant");
        String type = UserType.TENANT.toString();
        UserBuilder userBuilder = new UserBuilder(scanner);
        User newUser = userBuilder.getUser(type);
        Command command = new UserCommand(user, newUser, userService);
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
    }
    protected void removeTenant(){
        printMessage("Remove User!");
    }
    protected void listTenants(){
        List<Tenant> list = ((Manager) user).getTenantsList();
        int i = 0;
        for(Tenant tenant:list){
            System.out.printf("%s: %s(%s) - %s|%s[%s].\n",++i, tenant.getFullName(), tenant.getEmail(), tenant.getBuildingId(),tenant.getManagerEmail(),tenant.getFlatNoOrLabel());
        }
    }
    protected void addTechnician(){
        printMessage("Add Technician");
        String type = UserType.TECHNICIAN.toString();
        UserBuilder userBuilder = new UserBuilder(scanner);
        User newUser = userBuilder.getUser(type);
        Command command = new UserCommand(user, newUser, userService);
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
    }
    protected void removeTechnician(){

    }
    protected void listTechnicians(){
        printMessage("Technician list!");
        List<Technician> list = ((Manager) user).getTechniciansList();
        int i = 0;
        for(Technician user:list){
            System.out.printf("%s: %s(%s) - %s | %s.\n",++i, user.getFullName(), user.getUserType().toString(), user.getEmail(), user.isApproved());
        }
    }
    protected void managerDetails(){
        String detail = "Name: %s\n" +
                "Email: %s\n" +
                "Phone number: %s\n" +
                "Type: %s\n" +
                "Approved: %s\n" +
                "";
        System.out.printf(detail, user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getUserType().toString(),user.isApproved());
    }
    protected void addBuilding(){
        printMessage("Add new building");
        BuildingBuilder buildingBuilder = new BuildingBuilder(scanner);
        ManagedBuilding newBuilding = (ManagedBuilding) buildingBuilder.getBuilding();
        newBuilding.setManagerEmail(user.getEmail());
        Command command = new BuildingCommand(user, null, newBuilding, new BuildingService());
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
    }
    protected void removeBuilding(){
        printMessage("Remove building!");
    }
    protected void listBuildings(){
        printMessage("Building list!");
        BuildingBuilder buildingBuilder = new BuildingBuilder(buildingService);
        List<Building> list =  buildingBuilder.queryBuildings();
        int i = 0;
        for(Building building:list){
            if(addedBuilding((ManagedBuilding) building)){
                System.out.printf("%s: %s(%s) - %s.\n",++i, building.getName(), building.getState().toString(), building.getId());
            }
        }
    }
    private boolean addedBuilding(ManagedBuilding building){
        if(building==null){
            return false;
        }
        return (building.getManagerEmail().equalsIgnoreCase(user.getEmail()));
    }
    public Building getBuilding(){
        printMessage("Get a building");
        String buildingId = getBuildingId();
        BuildingBuilder buildingBuilder = new BuildingBuilder(buildingService);
        Building building =  buildingBuilder.queryBuilding(buildingId);
        if (building==null){
            System.out.println("building does not exist!");
        }else{
            if(addedBuilding((ManagedBuilding) building)){
                return building;
            }
            System.out.println("manager not authorised to get building!");
        }
        return null;
    }
    private String getBuildingId(){
        return getString("Enter building id: ");
    }
    private String getTenantEmail(){
        return getString("Enter tenant email: ");
    }

    private String getFlatNoOrLabel(){
        return getString("Enter flat no or label: ");
    }
    protected void assignTenant(){
        Building building = getBuilding();
        String flatLabel = getFlatNoOrLabel();
        String tenantEmail = getTenantEmail();
        UserBuilder userBuilder = new UserBuilder(userService);
        User tenant = userBuilder.queryUser(tenantEmail);

        if(tenant != null && building != null && tenant.getUserType()==UserType.TENANT){
            ((Tenant)tenant).setFlatNoOrLabel(flatLabel);
            Command command = new BuildingCommand(user, tenant,building, buildingService);
            new com.example.fmrepexservice.command.EmailNotifier(command);
            command.actionRequester();
        } else {
            printMessage("Error assigning tenant");
        }
    }
    protected void deAssignTenant(){
        Building building = getBuilding();
        String tenantEmail = getTenantEmail();
        UserBuilder userBuilder = new UserBuilder(userService);
        User tenant = userBuilder.queryUser(tenantEmail);

        if(tenant != null && building != null && tenant.getUserType()==UserType.TENANT){
            Command command = new BuildingCommand(user, tenant,building, buildingService);
            new com.example.fmrepexservice.command.EmailNotifier(command);
            command.actionRequester();
        } else {
            printMessage("Error de assigning tenant");
        }
    }
    protected void listBuildingOccupants(){
        String buildingId = getBuildingId();
        BuildingBuilder buildingBuilder = new BuildingBuilder(buildingService);
        List<Tenant> list = buildingBuilder.queryBuildingTenants(buildingId);
        if(list == null || list.size() == 0){
            System.out.println("No occupants");
            return;
        }
        int i = 0;
        printMessage("List of building occupants!");
        for(Tenant tenant: list){
            System.out.printf("%s: %s(%s) - %s|%s[%s].\n",++i, tenant.getFullName(), tenant.getEmail(), tenant.getBuildingId(),tenant.getManagerEmail(),tenant.getFlatNoOrLabel());
        }
    }
    protected void listTenantRequests(){
        printMessage("Tenant request list!");
        String tenantEmail = getTenantEmail();
        UserBuilder userBuilder = new UserBuilder(userService);
        User tenant = userBuilder.queryUser(tenantEmail);
        List<Request> list = new ArrayList<>();
        if(tenant != null && tenant.getUserType()==UserType.TENANT && user.getEmail().equalsIgnoreCase(((Tenant)tenant).getManagerEmail())){
            //AuthorisedRequestBuilder authorisedRequestBuilder = new AuthorisedRequestBuilder(tenantRequestService, managementRequestService);
            list = authorisedRequestBuilder.queryTenantRequests(tenantEmail);
        }
        int i = 0;
        for(Request request:list){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formatDateTime = request.getCreatedDateTime().format(format);
            System.out.printf("%s: (%s) %s(%s) - %s - %s - %s | %s.\n",++i, request.getId(), request.getAsset(), request.getCategory(), request.getStatus(),request.getTenantEmail(), request.getManagerEmail(), formatDateTime);
        }
    }
    protected void listManagerRequestsView(){
       printMessage("All request list");
        List<Request> list;

        //AuthorisedRequestBuilder authorisedRequestBuilder = new AuthorisedRequestBuilder(tenantRequestService, managementRequestService);
        list = authorisedRequestBuilder.queryManagerRequests(user.getEmail());
        if(list == null){
            return;
        }
        int i = 0;
        for(Request request:list){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formatDateTime = request.getCreatedDateTime().format(format);
            System.out.printf("%s: (%s) %s(%s) - %s - %s - %s | %s.\n",++i, request.getId(), request.getAsset(), request.getCategory(), request.getStatus(),request.getTenantEmail(), request.getManagerEmail(), formatDateTime);
        }
    }
    protected Request getRequest(){
        printMessage("Get a request");
        String requestId = getString("Enter request id: ");
        //AuthorisedRequestBuilder authorisedRequestBuilder = new AuthorisedRequestBuilder(tenantRequestService, managementRequestService);
        Request request = authorisedRequestBuilder.queryRequest(requestId);
        if(request == null){
            printMessage("Invalid request id");
            return null;
        }
       if(user.getEmail().equalsIgnoreCase(request.getManagerEmail())){
                System.out.println(request);
       }else {
                System.out.println("Not authorized to get request");
           return null;
       }
       return request;
    }
    protected WorkOrder createWorkOrder(){
        printMessage("Create a new work order");

        Request requestToAssign = getRequest();
        if(requestToAssign == null){
            return null;
        }
        String technicianEmail = getString("Enter technician email: ");
        UserBuilder userBuilder = new UserBuilder(userService);
        User technician = userBuilder.queryUser(technicianEmail);

        if(technician == null || technician.getUserType()!=UserType.TECHNICIAN){
            printMessage("Technician not found");
            return null;
        }

        AuthorisedWorkOrderBuilder workOrderBuilder = new AuthorisedWorkOrderBuilder(scanner);
        WorkOrder workOrderToAssign = workOrderBuilder.getWorkOrder();
        workOrderToAssign.setRequest(requestToAssign);
        workOrderToAssign.setTechnicianEmail(technician.getEmail());
        Command command = new WorkOrderCommand(user, technician, workOrderToAssign, workOrderService);
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
        return workOrderToAssign;
    }
    protected void listWorkOrders(){
        printMessage("List of work orders!");
        String technicianEmail = getString("Enter technician email (leave blank to return all work orders)");
        List<WorkOrder> list;
        AuthorisedWorkOrderBuilder workOrderBuilder = new AuthorisedWorkOrderBuilder(workOrderService);
        if(technicianEmail == null || technicianEmail.isEmpty()){
            list = workOrderBuilder.queryWorkOrders();
        }else{
            list = workOrderBuilder.queryWorkOrders(technicianEmail);
        }
        int i = 0;
        for(WorkOrder workOrder:list){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formatDateTime = workOrder.getCreatedDateTime().format(format);
            System.out.printf("%s: (%s) %s - %s.\n", ++i, workOrder.getId(), workOrder.getRequest().getAsset(), formatDateTime);
        }
    }
    protected WorkOrder getWorkOrder(){
        printMessage("Get a work order");
        String workOrderId = getWorkOrderId();
        AuthorisedWorkOrderBuilder workOrderBuilder = new AuthorisedWorkOrderBuilder(workOrderService);
        WorkOrder workOrder = workOrderBuilder.queryWorkOrder(workOrderId);
        if (workOrder==null){
            System.out.println("Work order does not exist!");
        }else{
            System.out.println(workOrder);
        }
        return workOrder;
    }
    protected void listAcceptedWorkOrders(){
        printMessage("List of accepted work orders!");
        String technicianEmail = getString("Enter technician email (leave blank to return all work orders)");
        List<WorkOrder> list;
        AuthorisedWorkOrderBuilder workOrderBuilder = new AuthorisedWorkOrderBuilder(workOrderService);
        if(technicianEmail ==null || technicianEmail.isEmpty()){
            list = workOrderBuilder.queryWorkOrders();
        }else{
            list = workOrderBuilder.queryWorkOrders(technicianEmail);
        }
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
        printMessage(msg);
        return scanner.nextLine();
    }
    private String getWorkOrderId(){
        return getString("Enter work order id: ");
    }
}
