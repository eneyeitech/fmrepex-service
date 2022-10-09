package com.example.fmrepexservice.announcementmanagement.business;

import com.example.fmrepexservice.announcementmanagement.persistence.DAO;
import com.example.fmrepexservice.announcementmanagement.persistence.DAOFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService implements ICrudService<Announcement> {

    private DAO announcementDAO;
    private DAOFactory storeFactory;

    public AnnouncementService(){
        storeFactory = DAOFactory.getDAOFactory(DAOFactory.STORE);
        announcementDAO = storeFactory.getAnnouncementDAO();
    }

    @Override
    public boolean add(Announcement building) {
        if(building.hasId()){
            return update(building);
        }else {
            return announcementDAO.add(building);
        }

    }

    @Override
    public boolean remove(String id) {
        return announcementDAO.remove(id);
    }

    @Override
    public boolean update(Announcement building) {
        return announcementDAO.update(building);
    }

    @Override
    public Announcement get(String id) {
        return (Announcement) announcementDAO.get(id);
    }

    @Override
    public List<Announcement> getAll() {
        return announcementDAO.getAll();
    }



}
