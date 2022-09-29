package com.example.fmrepexservice.business;

import com.example.fmrepexservice.query.WorkOrderQuery;
import com.example.fmrepexservice.workordermanagement.business.WorkOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class APIWorkOrderService {

    @Autowired
    private WorkOrderQuery query;

    public WorkOrder getWorkOrder(String id){
        return query.getWorkOrder(id);
    }

    public WorkOrder getWorkOrderWithRequest(String id){
        return query.getWorkOrderByRequest(id);
    }

    public boolean doesWorkOrderExist(String id){
        return getWorkOrder(id) != null;
    }

    public boolean doesWorkOrderWithRequestExist(String id){
        return getWorkOrderWithRequest(id) != null;
    }

    public List<WorkOrder> getWorkOrders(){
        return query.getWorkOrders();
    }

    public List<WorkOrder> getWorkOrdersByTechnician(String email){
        return query.getWorkOrdersByTechnician(email);
    }

    public List<WorkOrder> getWorkOrdersByManager(String email){
        return query.getWorkOrdersByManager(email);
    }

    public List<WorkOrder> getWorkOrdersByTenant(String email){
        return query.getWorkOrdersByTenant(email);
    }
}
