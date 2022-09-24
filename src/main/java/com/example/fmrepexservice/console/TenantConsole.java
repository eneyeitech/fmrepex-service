package com.example.fmrepexservice.console;

import com.example.fmrepexservice.builder.AuthorisedRequestBuilder;
import com.example.fmrepexservice.builder.RequestBuilder;
import com.example.fmrepexservice.builder.UserBuilder;
import com.example.fmrepexservice.command.Command;
import com.example.fmrepexservice.command.RequestCommand;
import com.example.fmrepexservice.command.UserCommand;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.requestmanagement.business.RequestService;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Dependant;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;
import com.example.fmrepexservice.workordermanagement.business.WORequestService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TenantConsole extends Console{
    private RequestService requestService;
    private WORequestService woRequestService;
    private AuthorisedRequestBuilder requestBuilder;
    private UserService userService;

    public TenantConsole(Scanner scanner, User user){
        super(scanner, user);
        requestService = new RequestService();
        woRequestService = new WORequestService(requestService);
        userService = new UserService();
        requestBuilder = new AuthorisedRequestBuilder(requestService, woRequestService);
    }

    public String menu(){
        return "" +
                "1. Add Dependant\n" +
                "2. Remove Dependant\n" +
                "3. List Dependants\n" +
                "4. Tenant details\n" +
                "5. Make request\n" +
                "6. View requests\n" +
                "7. Sign off request\n" +
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
                addDependant();
                return 1;
            case 2:
                removeDependant();
                return 2;
            case 3:
                listDependants();
                return 3;
            case 4:
                tenantDetails();
                return 4;
            case 5:
                newRequest();
                return 5;
            case 6:
                listRequests();
                return 6;
            case 7:
                signOffRequest();
                return 7;
            case 0:
                return 0;
            default:
                return 10;

        }
    }


    public boolean isApproved(){
        return user.isApproved();
    }
    public void addDependant(){
        showPrompt("Add Dependant");
        String type = UserType.DEPENDANT.toString();
        UserBuilder userBuilder = new UserBuilder(scanner);
        User newUser = userBuilder.getUser(type);
        Command command = new UserCommand(user, newUser, userService);
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
    }

    public void removeDependant(){
        showPrompt("Remove a user");
        String email = getEmail("Please enter user email: ");
        User dependant = (User) userService.get(email);
        boolean removed = userService.removeDependantFromTenant(user, dependant);
        if(removed){
            removed = userService.remove(email);
            System.out.println("User removed!");
        } else {
            System.out.println("User does not exist!");
        }
    }

    public void listDependants(){
        boolean isTenant = isTenant();
        if(!isTenant){
            return;
        }
        showPrompt("Dependant list!");
        //List<User> list = (List<User>) userService.getAll();
        List<User> list = ((Tenant) user).getDependantsList();
        int i = 0;
        for(User user:list){
            System.out.printf("%s: %s(%s) - %s | %s.\n",++i, user.getFullName(), user.getUserType().toString(), user.getEmail(), user.isApproved());
        }
    }

    public void tenantDetails(){
        String detail = "Name: %s\n" +
                "Email: %s\n" +
                "Phone number: %s\n" +
                "Type: %s\n" +
                "Approved: %s\n" +
                "";
        System.out.printf(detail, user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getUserType().toString(),user.isApproved());
    }

    private String getEmail(String msg){
        showPrompt(msg);
        String email = scanner.nextLine();
        return email;
    }

    public Request newRequest(){
        showPrompt("Add new request");
        AuthorisedRequestBuilder requestBuilder = new AuthorisedRequestBuilder(scanner);
        Request requestToAdd = requestBuilder.getRequest();
        requestToAdd.setTenantEmail(user.getEmail());
        requestToAdd.setBuildingId(((Tenant)user).getBuildingId());
        requestToAdd.setManagerEmail(((Tenant)user).getManagerEmail());
        requestToAdd.setFlatLabel(((Tenant)user).getFlatNoOrLabel());
        Command command = new RequestCommand(user, requestToAdd,requestService);
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
        return requestToAdd;
    }

    public void removeRequest(){
        Request request = getRequest();
        showPrompt("Remove a request");
        boolean removed = requestService.remove(request.getId());
        if(removed){
            System.out.println("Request removed!");
        } else {
            System.out.println("Request does not exist!");
        }
    }

    public Request getRequest(){
        if(!canMakeRequest()){
            System.out.println("Get request:: not authorised");
        }
        showPrompt("Get a request");
        String requestId = getRequestId();
        Request request = requestBuilder.queryRequest(requestId);
        if (request==null){
            System.out.println("request does not exist!");
        }else{

            if(user.getEmail().equalsIgnoreCase(request.getTenantEmail())){
                System.out.println(request);
            }else {
                System.out.println("Not authorized to get request");
                request = null;
            }
        }
        return request;
    }

    public void signOffRequest(){
        Request request = getRequest();
        if(request == null){
            return;
        }
        Command command = new RequestCommand(user, request, requestService);
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
    }

    public void listRequests(){
        showPrompt("Request list!");
        //List<Request> list = requestService.getAll(user.getEmail());
        List<Request> list = requestBuilder.queryTenantRequests(user.getEmail());
        int i = 0;
        for(Request request:list){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formatDateTime = request.getCreatedDateTime().format(format);
            System.out.printf("%s: (%s) %s(%s) - %s - %s - %s | %s.\n",++i, request.getId(), request.getAsset(), request.getCategory(), request.getStatus(),request.getTenantEmail(), request.getManagerEmail(), formatDateTime);
        }
    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private String getRequestId(){
        return getString("Enter request id: ");
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public boolean isTenant(){
        if(user != null && user.getUserType() != UserType.TENANT){
            System.out.println("User not authorized");
            return false;
        }
        return true;
    }

    public boolean hasBuilding(){
        return ((Tenant) user).hasAnAssignedBuilding();
    }
    public boolean hasManager(){
        return ((Tenant)user).hasAManager();
    }
    public boolean hasFlat(){
        return ((Tenant)user).hasAFlat();
    }
    public boolean canMakeRequest(){
        return isTenant() && hasBuilding() && hasManager() && hasFlat();
    }
}
