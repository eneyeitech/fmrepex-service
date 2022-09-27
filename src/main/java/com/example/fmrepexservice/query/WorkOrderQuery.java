package com.example.fmrepexservice.query;

import com.example.fmrepexservice.workordermanagement.business.WorkOrder;
import com.example.fmrepexservice.workordermanagement.business.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component
public class WorkOrderQuery {
    private WorkOrderService workOrderService;

    @Autowired
    public WorkOrderQuery(WorkOrderService workOrderService){
        this.workOrderService = workOrderService;
    }

    public WorkOrder getWorkOrder(String workOrderId){
        return workOrderService.get(workOrderId);
    }

    public WorkOrder getWorkOrderByRequest(String requestId){
        Optional<WorkOrder> matchingWorkOrder = getWorkOrders().stream().filter(w -> w.getRequest().getId().equalsIgnoreCase(requestId)).findFirst();
        WorkOrder workOrder = null;
        if(matchingWorkOrder.isPresent()){
            workOrder = matchingWorkOrder.get();
        }
        return workOrder;
    }

    public List<WorkOrder> getWorkOrders(){
        return workOrderService.getAll();
    }

    public List<WorkOrder> getWorkOrdersByTechnician(String technicianId){
        return workOrderService.getAll(technicianId);
    }

    public List<WorkOrder> getWorkOrdersByTenant(String tenantEmail){
        return getWorkOrders().stream().filter(w -> w.getRequest().getTenantEmail().equalsIgnoreCase(tenantEmail)).collect(Collectors.toList());
    }

    public List<WorkOrder> getWorkOrdersByManager(String managerEmail){
        return getWorkOrders().stream().filter(w -> w.getRequest().getManagerEmail().equalsIgnoreCase(managerEmail)).collect(Collectors.toList());
    }
}
