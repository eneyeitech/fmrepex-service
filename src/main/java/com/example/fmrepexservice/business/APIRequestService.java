package com.example.fmrepexservice.business;

import com.example.fmrepexservice.query.RequestQuery;
import com.example.fmrepexservice.requestmanagement.business.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class APIRequestService {

    @Autowired
    private RequestQuery query;

    public Request getRequest(String id){
        return query.getRequest(id);
    }

    public boolean doesRequestExist(String id){
        return getRequest(id) != null;
    }

    public List<Request> getRequests(){
        return query.getRequests();
    }

    public List<Request> getRequestsByTenant(String email){
        return query.getRequestsByTenant2(email);
    }

    public List<Request> getRequestsByManager(String email){
        return query.getRequestsByManager2(email);
    }

    public List<Request> getRequestsByBuilding(String id){
        return query.getRequestsByBuildingId(id);
    }
}
