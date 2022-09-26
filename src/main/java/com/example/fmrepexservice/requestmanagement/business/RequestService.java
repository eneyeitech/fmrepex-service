package com.example.fmrepexservice.requestmanagement.business;

import com.example.fmrepexservice.constant.Action;
import com.example.fmrepexservice.requestmanagement.persistence.DAO;
import com.example.fmrepexservice.requestmanagement.persistence.DAOFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService implements ICrudService<Request>, ISubject{

    private List<IObserver> observerList;
    private DAO requestDAO;
    private DAOFactory storeFactory;

    public RequestService(){
        storeFactory = DAOFactory.getDAOFactory(DAOFactory.STORE);
        requestDAO = storeFactory.getRequestDAO();
        observerList = new ArrayList<>();
    }

    @Override
    public boolean add(Request request) {
        System.out.println("WEeeeeeeeeeeeeee");
        boolean added;
        added = requestDAO.add(request);
        if(added && observerList.size()==1){
            notifyObservers(request, null, Action.ADD);
        }
        /**if(request.hasId()){
            added = update(request);
            if(added && observerList.size()==1){
                notifyObservers(request, null, Action.UPDATE);
            }
        } else {
            added = requestDAO.add(request);
            if(added && observerList.size()==1){
                notifyObservers(request, null, Action.ADD);
            }
        }*/


        return added;
    }

    @Override
    public boolean remove(String id) {
        boolean removed = requestDAO.remove(id);
        if(removed && observerList.size()==1){
            notifyObservers(null, id, Action.REMOVE);
        }
        return removed;
    }

    @Override
    public boolean update(Request request) {
        return requestDAO.update(request);
    }

    @Override
    public Request get(String id) {
        return (Request) requestDAO.get(id);
    }

    @Override
    public List<Request> getAll() {
        return requestDAO.getAll();
    }

    @Override
    public List<Request> getAll(String tenantEmail) {
        return requestDAO.getAll(tenantEmail);
    }

    @Override
    public void registerObserver(IObserver observer) {
        if(observerList.size() >= 1){
            observerList.clear();
            observerList = new ArrayList<>();
            observerList.add(observer);
            return;
        }
        observerList.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observerList.clear();
        observerList = new ArrayList<>();
    }

    @Override
    public void notifyObservers(Request r, String id, Action a) {
        if(observerList.size()!=0){
            observerList.get(0).execute(r,id,a);
        }
    }
}
