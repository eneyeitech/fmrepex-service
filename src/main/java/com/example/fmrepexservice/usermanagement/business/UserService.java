package com.example.fmrepexservice.usermanagement.business;

import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.usermanagement.business.user.Dependant;
import com.example.fmrepexservice.usermanagement.business.user.Manager;
import com.example.fmrepexservice.usermanagement.business.user.Technician;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;
import com.example.fmrepexservice.usermanagement.exception.TableException;
import com.example.fmrepexservice.usermanagement.persistence.DAO;
import com.example.fmrepexservice.usermanagement.persistence.DAOFactory;

public class UserService {
    private DAO userDao;
    private DAOFactory storeFactory;

    public UserService(){
        storeFactory = DAOFactory.getDAOFactory(DAOFactory.STORE);
        userDao = storeFactory.getUserDAO();
    }

    public boolean add(User user){
        return userDao.add(user);
    }

    public boolean remove(String id){
        return userDao.remove(id);
    }
    public boolean update(User user) {
        return userDao.update(user);
    }
    public Object get(String id){
        return userDao.get(id);
    }
    public Object getAll(){
        return userDao.getAll();
    }
    public boolean exists(String id){
        return get(id) != null;
    }

    public boolean addTenantToManager(User manager, User tenant){
        if(manager.getUserType()!=UserType.MANAGER){
            System.out.println("User not authorized");
            return false;
        }
        if(tenant.getUserType()!=UserType.TENANT){
            System.out.println("User not a tenant");
            return false;
        }

        boolean added =  ((Manager) manager).addTenant((Tenant) tenant);
        if(added){
            return true;
        }
        System.out.println("Tenant already added");
        return false;
    }

    public boolean removeTenantFromManager(User manager, User tenant){
        if(manager.getUserType()!=UserType.MANAGER){
            System.out.println("User not authorized");
            return false;
        }
        if(tenant.getUserType()!=UserType.TENANT){
            System.out.println("User not a tenant");
            return false;
        }

        boolean removed =  ((Manager) manager).removeTenant((Tenant) tenant);
        if(removed){
            return true;
        }
        System.out.println("Tenant not removed");
        return false;
    }

    public boolean addTechnicianToManager(User manager, User technician){
        if(manager.getUserType()!=UserType.MANAGER){
            System.out.println("User not authorized");
            return false;
        }
        if(technician.getUserType()!=UserType.TECHNICIAN){
            System.out.println("User not a technician");
            return false;
        }

        boolean added =  ((Manager) manager).addTechnician((Technician) technician);
        if(added){
            return true;
        }
        System.out.println("Technician already added");
        return false;
    }

    public boolean removeTechnicianFromManager(User manager, User technician){
        if(manager.getUserType()!=UserType.MANAGER){
            System.out.println("User not authorized");
            return false;
        }
        if(technician.getUserType()!=UserType.TECHNICIAN){
            System.out.println("User not a technician");
            return false;
        }

        boolean removed =  ((Manager) manager).removeTechnician((Technician) technician);
        if(removed){
            return true;
        }
        System.out.println("Technician not removed");
        return false;
    }


    public boolean addDependantToTenant(User tenant, User dependant){
        if(tenant.getUserType()!=UserType.TENANT){
            System.out.println("User not authorized");
            return false;
        }
        if(dependant.getUserType()!=UserType.DEPENDANT){
            System.out.println("User not a dependant");
            return false;
        }

        boolean added =  ((Tenant) tenant).addDependant((Dependant) dependant);
        if(added){
            return true;
        }
        System.out.println("Dependant already added");
        return false;
    }

    public boolean removeDependantFromTenant(User tenant, User dependant){
        if(tenant.getUserType()!=UserType.TENANT){
            System.out.println("User not authorized");
            return false;
        }
        if(dependant.getUserType()!=UserType.DEPENDANT){
            System.out.println("User not a dependant");
            return false;
        }

        boolean removed =  ((Tenant) tenant).removeDependant((Dependant) dependant);
        if(removed){
            return true;
        }
        System.out.println("Dependant not removed");
        return false;
    }


    public void createTable(){
        try {
            userDao.createTable();
        } catch (TableException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropTable(){
        try {
            userDao.dropTable();
        } catch (TableException e) {
            throw new RuntimeException(e);
        }
    }



}
