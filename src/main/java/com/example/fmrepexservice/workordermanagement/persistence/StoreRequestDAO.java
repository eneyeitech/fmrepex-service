package com.example.fmrepexservice.workordermanagement.persistence;

import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.workordermanagement.database.RequestStore;
import com.example.fmrepexservice.workordermanagement.exception.TableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StoreRequestDAO extends DAO<Request> {

    public StoreRequestDAO(){
        store = RequestStore.getInstance().getStore();
    }

    public static ConcurrentHashMap<String, List<Request>> convertToManagerKey(Map<String, List<Request>> storageWithTenantKey){
        ConcurrentHashMap<String, List<Request>> storageWithManagerKey = new ConcurrentHashMap<>();
        List<Request> requests;
        for(ConcurrentHashMap.Entry<String, List<Request>> m: storageWithTenantKey.entrySet()){
            requests = new ArrayList<>(m.getValue());
            for(Request request: requests){
                String managerEmail = request.getManagerEmail();
                List<Request> requestsInStore;

                if (storageWithManagerKey.containsKey(managerEmail)){
                    requestsInStore = new ArrayList<>(storageWithManagerKey.get(managerEmail));
                }
                else{
                    requestsInStore = new ArrayList<>();
                }
                requestsInStore.add(request);
                storageWithManagerKey.put(managerEmail, requestsInStore);
            }
        }
        //System.out.println(storageWithManagerKey);
        return storageWithManagerKey;
    }

    public static void update(){
        RequestStore.getInstance().updateStore(StoreRequestDAO.convertToManagerKey(com.example.fmrepexservice.requestmanagement.database.RequestStore.getInstance().getStore()));
    }

    @Override
    public boolean add(Request request) {
        if(request.hasId()){
         //   return update(request);
            System.out.println("WO Store Request DAO");
        }
        System.out.println("WO Store Request DAO 2");
        request.setId(requestIdGenerator.generate());

        String managerEmail = request.getManagerEmail();
        List<Request> requests;
        List<Request> requestsInStore;
        boolean added;
        if(store.containsKey(managerEmail)){
            requests = new ArrayList<>(store.get(managerEmail));
            added = requests.add(request);
            requestsInStore = store.put(managerEmail, requests);
            return added;
        }
        requests = new ArrayList<>();
        added = requests.add(request);
        requestsInStore = store.put(managerEmail, requests);
        return added;
    }

    @Override
    public boolean remove(String id) {
        List<Request> requests;
        for(ConcurrentHashMap.Entry<String, List<Request>> m: store.entrySet()){
            requests = new ArrayList<>(m.getValue());
            for(Request r: requests){
                if(r.getId().equalsIgnoreCase(id)){
                    String mEmail = r.getManagerEmail();
                    boolean removed = requests.remove(r);
                    if(removed){
                        store.put(mEmail, requests);
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
        Collections.sort(list, (o1, o2) -> o1.getManagerEmail().compareTo(o2.getManagerEmail()));
        return list;
    }

    @Override
    public List<Request> getAll(String managerEmail) {
        List<Request> list;
        if(store.containsKey(managerEmail)){
            list = new ArrayList<>(store.get(managerEmail));
            Collections.sort(list, (o1, o2) -> o2.getCreatedDateTime().toLocalDate().compareTo(o1.getCreatedDateTime().toLocalDate()));
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
                    String mEmail = r.getManagerEmail();
                    int index = requests.indexOf(r);
                    boolean removed = requests.remove(r);

                    if(removed && index >= 0){
                        requests.add(index, request);
                        store.put(mEmail, requests);
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
