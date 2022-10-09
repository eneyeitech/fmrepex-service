package com.example.fmrepexservice.business;

import com.example.fmrepexservice.announcementmanagement.business.Announcement;
import com.example.fmrepexservice.announcementmanagement.business.AnnouncementService;
import com.example.fmrepexservice.announcementmanagement.helper.AnnouncementIdGenerator;
import com.example.fmrepexservice.authentication.*;
import com.example.fmrepexservice.authentication.EmailNotifier;
import com.example.fmrepexservice.buildingmanagement.business.Building;
import com.example.fmrepexservice.buildingmanagement.business.BuildingService;
import com.example.fmrepexservice.buildingmanagement.business.ManagedBuilding;
import com.example.fmrepexservice.buildingmanagement.helper.BuildingIdGenerator;
import com.example.fmrepexservice.command.*;
import com.example.fmrepexservice.companymanagement.business.Company;
import com.example.fmrepexservice.companymanagement.business.CompanyService;
import com.example.fmrepexservice.companymanagement.helper.CompanyIdGenerator;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.security.PasswordEncoderConfig;
import com.example.fmrepexservice.security.UserDetailsImpl;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;
import com.example.fmrepexservice.workordermanagement.business.WorkOrder;
import com.example.fmrepexservice.workordermanagement.business.WorkOrderService;
import com.example.fmrepexservice.workordermanagement.helper.WorkOrderIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class APIManagerService {
    private PasswordEncoderConfig passwordEncoderConfig;


    @Autowired
    public APIManagerService(PasswordEncoderConfig passwordEncoderConfig){
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    public void registerTenant(String name, String email, String password, String phoneNumber, UserType type){

        User newUser = UserFactory.getUser(type);

        newUser.addUserGroups(UserFactory.getGroup(type));

        newUser.setFullName(name.toUpperCase(Locale.ROOT));
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setPassword(password);

        newUser.setPassword(passwordEncoderConfig.getEncoder().encode(newUser.getPassword()));
        newUser.setLocked(true);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = null;
        if (auth != null) {
            loggedInUser = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        }
        if(loggedInUser != null) {
            Command command = new UserCommand(loggedInUser, newUser, new UserService());
            new com.example.fmrepexservice.command.EmailNotifier(command);
            command.actionRequester();
        }
    }

    public void registerTechnician(String name, String email, String password, String phoneNumber, UserType type){

        User newUser = UserFactory.getUser(type);

        newUser.addUserGroups(UserFactory.getGroup(type));

        newUser.setFullName(name.toUpperCase(Locale.ROOT));
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setPassword(password);

        newUser.setPassword(passwordEncoderConfig.getEncoder().encode(newUser.getPassword()));
        newUser.setLocked(true);

        /**Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = null;
        if (auth != null) {
            loggedInUser = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        }*/

        User loggedInUser = Helper.retrieveUser();

        if(loggedInUser != null) {
            Command command = new UserCommand(loggedInUser, newUser, new UserService());
            new com.example.fmrepexservice.command.EmailNotifier(command);
            command.actionRequester();
        }
    }

    public String addBuilding(ManagedBuilding newBuilding){
        User loggedInUser = Helper.retrieveUser();

        newBuilding.setId((new BuildingIdGenerator(10)).generate());
        if(loggedInUser != null) {
            newBuilding.setManagerEmail(loggedInUser.getEmail());

            Command command = new BuildingCommand(loggedInUser, null, newBuilding, new BuildingService());
            new com.example.fmrepexservice.command.EmailNotifier(command);
            command.actionRequester();
        }

        return newBuilding.getId();
    }

    public User updateUser(User userToUpdate){
        UserManagement registerManager = new Registration(userToUpdate, new UserService());
        new SecurityMonitor(registerManager);
        new GeneralLogger(registerManager);
        new EmailNotifier(registerManager);
        return registerManager.handle();
    }

    public void assignTenent(User user, User tenant, Building building, String flatLabel){
        ((Tenant)tenant).setFlatNoOrLabel(flatLabel);
        Command command = new BuildingCommand(user, tenant,building, new BuildingService());
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
    }

    public String createWorkOrder(User user, User technician, WorkOrder workOrderToAssign, Request requestToAssign){

        workOrderToAssign.setId((new WorkOrderIdGenerator(10)).generate());
        workOrderToAssign.setRequest(requestToAssign);
        workOrderToAssign.setTechnicianEmail(technician.getEmail());
        Command command = new WorkOrderCommand(user, technician, workOrderToAssign, new WorkOrderService());
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();

        return workOrderToAssign.getId();
    }

    public String addAnnouncement(Announcement newAnnouncement){
        User loggedInUser = Helper.retrieveUser();

        newAnnouncement.setId((new AnnouncementIdGenerator(10)).generate());
        if(loggedInUser != null) {

            Command command = new AnnouncementCommand(loggedInUser, newAnnouncement, new AnnouncementService());
            new com.example.fmrepexservice.command.EmailNotifier(command);
            command.actionRequester();

        }

        return newAnnouncement.getId();
    }

    public String addCompany(Company newCompany){
        User loggedInUser = Helper.retrieveUser();

        newCompany.setId((new CompanyIdGenerator(10)).generate());
        if(loggedInUser != null) {
            Command command = new CompanyCommand(loggedInUser, newCompany, new CompanyService(), new UserService());
            new com.example.fmrepexservice.command.EmailNotifier(command);
            command.actionRequester();
        }

        return newCompany.getId();
    }
}
