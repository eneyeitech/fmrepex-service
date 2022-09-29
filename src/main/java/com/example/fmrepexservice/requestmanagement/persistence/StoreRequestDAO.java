package com.example.fmrepexservice.requestmanagement.persistence;

import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.requestmanagement.database.RequestStore;
import com.example.fmrepexservice.requestmanagement.exception.TableException;
import com.example.fmrepexservice.requestmanagement.helper.RequestIdGenerator;

import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StoreRequestDAO extends DAO<Request>{

    public StoreRequestDAO(){
        store = RequestStore.getInstance().getStore();
    }

    @Override
    public boolean add(Request request) {
        if(request.hasId()){
            //return update(request);
        }

        request.setId(requestIdGenerator.generate());

        String tenantEmail = request.getTenantEmail();
        List<Request> requests;
        List<Request> requestsInStore;
        boolean added;
        if(store.containsKey(tenantEmail)){
            requests = new ArrayList<>(store.get(tenantEmail));
            added = requests.add(request);
            requestsInStore = store.put(tenantEmail, requests);
            return added;
        }
        requests = new ArrayList<>();
        added = requests.add(request);
        requestsInStore = store.put(tenantEmail, requests);
        return added;
    }

    @Override
    public boolean remove(String id) {
        List<Request> requests;
        for(ConcurrentHashMap.Entry<String, List<Request>> m: store.entrySet()){
            requests = new ArrayList<>(m.getValue());
            for(Request r: requests){
                if(r.getId().equalsIgnoreCase(id)){
                    String tEmail = r.getTenantEmail();
                    boolean removed = requests.remove(r);
                    if(removed){
                        store.put(tEmail, requests);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Request get(String id) {
        List<Request> requests;
        for(ConcurrentHashMap.Entry<String, List<Request>> m: store.entrySet()){
            requests = new ArrayList<>(m.getValue());
            for(Request r: requests){
                if(r.getId().equalsIgnoreCase(id)){
                    return r;
                }
            }
        }
        return null;
    }

    @Override
    public List<Request> getAll() {
        List<Request> list = new ArrayList<>();
        for(ConcurrentHashMap.Entry<String, List<Request>> m: store.entrySet()){
            list.addAll(m.getValue());
        }
        Collections.sort(list, (o1, o2) -> o1.getTenantEmail().compareTo(o2.getTenantEmail()));
        return list;
    }

    @Override
    public List<Request> getAll(String tenantEmail) {
        List<Request> list;
        if(store.containsKey(tenantEmail)){
            list = new ArrayList<>(store.get(tenantEmail));
            Collections.sort(list, (o1, o2) -> o1.getCreatedDateTime().toLocalDate().compareTo(o2.getCreatedDateTime().toLocalDate()));
            return list;
        }
        list = new ArrayList<>();
        return list;
    }

    @Override
    public boolean update(Request request) {
        String requestId = request.getId();
        List<Request> requests;
        for(ConcurrentHashMap.Entry<String, List<Request>> m: store.entrySet()){
            requests = new ArrayList<>(m.getValue());
            for(Request r: requests){
                if(r.getId().equalsIgnoreCase(requestId)){
                    String tEmail = r.getTenantEmail();
                    int index = requests.indexOf(r);
                    boolean removed = requests.remove(r);

                    if(removed && index >= 0){
                        requests.add(index, request);
                        store.put(tEmail, requests);
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
