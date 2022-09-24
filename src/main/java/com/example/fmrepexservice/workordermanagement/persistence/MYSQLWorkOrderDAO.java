package com.example.fmrepexservice.workordermanagement.persistence;

import com.example.fmrepexservice.workordermanagement.business.WorkOrder;
import com.example.fmrepexservice.workordermanagement.exception.TableException;

import java.util.List;

public class MYSQLWorkOrderDAO extends DAO<WorkOrder>{
    @Override
    public boolean add(WorkOrder workOrder) {
        return false;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public WorkOrder get(String id) {
        return null;
    }

    @Override
    public List<WorkOrder> getAll() {
        return null;
    }

    @Override
    public List<WorkOrder> getAll(String tenantEmail) {
        return null;
    }

    @Override
    public boolean update(WorkOrder workOrder) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void createTable() throws TableException {

    }

    @Override
    public void dropTable() throws TableException {

    }
}
