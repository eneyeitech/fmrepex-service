package com.example.fmrepexservice.builder;

import com.example.fmrepexservice.requestmanagement.business.Category;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.requestmanagement.business.RequestService;
import com.example.fmrepexservice.requestmanagement.helper.RequestIdGenerator;
import com.example.fmrepexservice.workordermanagement.business.WORequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AuthorisedRequestBuilder {
    private RequestService requestService;
    private WORequestService woRequestService;
    private Scanner scanner;
    private Request request;
    private String description;
    private String asset;
    private Category category;
    private LocalDateTime createdDateTime;

    public AuthorisedRequestBuilder(Scanner scanner){
        this.scanner = scanner;
        asset = getString("Enter asset needing maintenance (bulb, tap etc...): ");
        description = getString("Enter brief description of problem: ");

        Category[] categories = Category.values();
        Random rand = new Random();

        int n = rand.nextInt(categories.length);
        category = categories[n];
    }
    public AuthorisedRequestBuilder(RequestService requestService, WORequestService woRequestService){
        this.requestService = requestService;
        this.woRequestService = woRequestService;
    }

    public Request queryRequest(String requestId){
        return requestService.get(requestId);
    }

    public List<Request> queryRequests(){
        return requestService.getAll();
    }

    public List<Request> queryTenantRequests(String tenantEmail){
        return requestService.getAll(tenantEmail);
    }
    public List<Request> queryManagerRequests(String managerEmail){
        return woRequestService.getAll(managerEmail);
    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public Request getRequest(){
        //request = new Request(new RequestIdGenerator(10));
        request = new Request();
        request.setAsset(asset);
        request.setDescription(description);
        request.setCategory(category);
        request.setCreatedDateTime(LocalDateTime.now());
        return request;
    }
}
