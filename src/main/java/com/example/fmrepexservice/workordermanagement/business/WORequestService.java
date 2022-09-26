package com.example.fmrepexservice.workordermanagement.business;


import com.example.fmrepexservice.constant.Action;
import com.example.fmrepexservice.requestmanagement.business.IObserver;
import com.example.fmrepexservice.requestmanagement.business.ISubject;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.workordermanagement.persistence.DAO;
import com.example.fmrepexservice.workordermanagement.persistence.DAOFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WORequestService implements ICrudService<Request>, IObserver {
    private ISubject requestService;
    private DAO requestDAO;
    private DAOFactory storeFactory;

    public WORequestService(){
        storeFactory = DAOFactory.getDAOFactory(DAOFactory.STORE);
        requestDAO = storeFactory.getRequestDAO();
    }
    public WORequestService(ISubject requestService){
        storeFactory = DAOFactory.getDAOFactory(DAOFactory.STORE);
        requestDAO = storeFactory.getRequestDAO();
        this.requestService=requestService;
        this.requestService.registerObserver(this);
    }


    @Override
    public boolean add(Request request) {
      return   requestDAO.add(request);
    }

    @Override
    public boolean remove(String id) {
        return requestDAO.remove(id);
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
    public List<Request> getAll(String managerEmail) {
        return requestDAO.getAll(managerEmail);
    }

    @Override
    public void execute(Request r, String id, Action a) {
        switch (a){
            case ADD:
                System.out.println("ADD");
                add(r);
                break;
            case REMOVE:
                remove(id);
                System.out.println("REMOVE");
                break;
            case UPDATE:
                update(r);
                System.out.println("UPDATE");
                break;
            default:
        }
    }
}
