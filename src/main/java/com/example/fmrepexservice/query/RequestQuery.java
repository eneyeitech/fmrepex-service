package com.example.fmrepexservice.query;

import com.example.fmrepexservice.constant.Status;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.requestmanagement.business.RequestService;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.workordermanagement.business.WORequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class RequestQuery {
    private User user;

    private RequestService requestService;
    private WORequestService woRequestService;

    @Autowired
    public RequestQuery(RequestService service1){
        this.requestService = service1;
        this.woRequestService = new WORequestService(requestService);
    }

    public Request getRequest(String requestId){
        return requestService.get(requestId);
    }
    public List<Request> getRequests(){
        return requestService.getAll();
    }

    public List<Request> getRequestsByTenant1(String tenantEmail){
        return getRequests().stream().filter(r -> r.getTenantEmail().equalsIgnoreCase(tenantEmail)).collect(Collectors.toList());
    }

    public List<Request> getRequestsByTenant2(String tenantEmail){
        return requestService.getAll(tenantEmail);
    }
    public List<Request> getRequestsByManager1(String managerEmail){
        return getRequests().stream().filter(r -> r.getManagerEmail().equalsIgnoreCase(managerEmail)).collect(Collectors.toList());
    }

    public List<Request> getRequestsByManager2(String managerEmail){
        return woRequestService.getAll(managerEmail);
    }

    public List<Request> getRequestsByDependant(String tenantEmail){
        return getRequestsByTenant1(tenantEmail);
    }

    public List<Request> getRequestsByWorkOrder(String workOrderId){
        return getRequests().stream().filter(r -> r.getWorkOrderId().equalsIgnoreCase(workOrderId)).collect(Collectors.toList());
    }

    public List<Request> getRequestsByBuildingId(String buildingId){
        return getRequests().stream().filter(r -> r.getBuildingId().equalsIgnoreCase(buildingId)).collect(Collectors.toList());
    }

    public List<Request> getRequestsByStatus(Status status){
        return getRequests().stream().filter(r -> r.getStatus() == status).collect(Collectors.toList());
    }
}
