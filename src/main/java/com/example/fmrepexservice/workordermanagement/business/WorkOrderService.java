package com.example.fmrepexservice.workordermanagement.business;

import com.example.fmrepexservice.workordermanagement.persistence.DAO;
import com.example.fmrepexservice.workordermanagement.persistence.DAOFactory;

import java.util.List;

public class WorkOrderService implements ICrudService<WorkOrder>{


    private DAO workOrderDAO;
    private DAOFactory storeFactory;

    public WorkOrderService(){
        storeFactory = DAOFactory.getDAOFactory(DAOFactory.STORE);
        workOrderDAO = storeFactory.getWorkOrderDAO();
    }

    @Override
    public boolean add(WorkOrder workOrder) {
        if(workOrder.hasId()){
            return update(workOrder);
        }else {
            return workOrderDAO.add(workOrder);
        }
    }

    @Override
    public boolean remove(String id) {
        return workOrderDAO.remove(id);
    }

    @Override
    public boolean update(WorkOrder workOrder) {
        return workOrderDAO.update(workOrder);
    }

    @Override
    public WorkOrder get(String id) {
        return (WorkOrder) workOrderDAO.get(id);
    }

    @Override
    public List<WorkOrder> getAll() {
        return workOrderDAO.getAll();
    }

    @Override
    public List<WorkOrder> getAll(String email) {
        return workOrderDAO.getAll(email);
    }
}
