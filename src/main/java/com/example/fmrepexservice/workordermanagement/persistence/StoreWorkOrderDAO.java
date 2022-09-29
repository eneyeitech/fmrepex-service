package com.example.fmrepexservice.workordermanagement.persistence;

import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.workordermanagement.business.WorkOrder;
import com.example.fmrepexservice.workordermanagement.database.WorkOrderStore;
import com.example.fmrepexservice.workordermanagement.exception.TableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StoreWorkOrderDAO extends DAO<WorkOrder>{

    public StoreWorkOrderDAO(){
        store = WorkOrderStore.getInstance().getStore();
    }

    @Override
    public boolean add(WorkOrder workOrder) {
        if(workOrder.hasId()){
           // return update(workOrder);
        }

        workOrder.setId(workOrderIdGenerator.generate());

        String technicianEmail = workOrder.getTechnicianEmail();
        List<WorkOrder> workOrders;
        boolean added;
        if(store.containsKey(technicianEmail)){
            workOrders = new ArrayList<>(store.get(technicianEmail));
            added = workOrders.add(workOrder);
            store.put(technicianEmail, workOrders);
            return added;
        }
        workOrders = new ArrayList<>();
        added = workOrders.add(workOrder);
        store.put(technicianEmail, workOrders);
        return added;
    }

    @Override
    public boolean remove(String id) {
        List<WorkOrder> workOrders;
        for(ConcurrentHashMap.Entry<String, List<WorkOrder>> m: store.entrySet()){
            workOrders = new ArrayList<>(m.getValue());
            for(WorkOrder workOrder: workOrders){
                if(workOrder.getId().equalsIgnoreCase(id)){
                    String technicianEmail = workOrder.getTechnicianEmail();
                    boolean removed = workOrders.remove(workOrder);
                    if(removed){
                        store.put(technicianEmail, workOrders);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public WorkOrder get(String id) {
        List<WorkOrder> workOrders;
        for(ConcurrentHashMap.Entry<String, List<WorkOrder>> m: store.entrySet()){
            workOrders = new ArrayList<>(m.getValue());
            for(WorkOrder workOrder: workOrders){
                if(workOrder.getId().equalsIgnoreCase(id)){
                    return workOrder;
                }
            }
        }
        return null;
    }

    @Override
    public List<WorkOrder> getAll() {
        List<WorkOrder> list = new ArrayList<>();
        for(ConcurrentHashMap.Entry<String, List<WorkOrder>> m: store.entrySet()){
            list.addAll(m.getValue());
        }
        Collections.sort(list, (o1, o2) -> o1.getTechnicianEmail().compareTo(o2.getTechnicianEmail()));
        return list;
    }

    @Override
    public List<WorkOrder> getAll(String email) {
        List<WorkOrder> list;
        if(store.containsKey(email)){
            list = new ArrayList<>(store.get(email));
            Collections.sort(list, (o1, o2) -> o2.getCreatedDateTime().toLocalDate().compareTo(o1.getCreatedDateTime().toLocalDate()));
            return list;
        }
        list = new ArrayList<>();
        return list;
    }

    @Override
    public boolean update(WorkOrder workOrder) {
        String workOrderId = workOrder.getId();
        List<WorkOrder> workOrders;
        for(ConcurrentHashMap.Entry<String, List<WorkOrder>> m: store.entrySet()){
            workOrders = new ArrayList<>(m.getValue());
            for(WorkOrder workOrder1: workOrders){
                if(workOrder1.getId().equalsIgnoreCase(workOrderId)){
                    String technicianEmail = workOrder1.getTechnicianEmail();
                    int index = workOrders.indexOf(workOrder1);
                    boolean removed = workOrders.remove(workOrder1);

                    if(removed && index >= 0){
                        workOrders.add(index, workOrder);
                        store.put(technicianEmail, workOrders);
                    }
                    return true;
                }
            }
        }

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
