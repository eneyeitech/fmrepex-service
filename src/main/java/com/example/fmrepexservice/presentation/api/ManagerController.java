package com.example.fmrepexservice.presentation.api;


import com.example.fmrepexservice.announcementmanagement.business.Announcement;
import com.example.fmrepexservice.buildingmanagement.business.*;
import com.example.fmrepexservice.business.*;
import com.example.fmrepexservice.companymanagement.business.Company;
import com.example.fmrepexservice.constant.Address;
import com.example.fmrepexservice.constant.State;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.helper.Validator;
import com.example.fmrepexservice.requestmanagement.business.Category;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Manager;
import com.example.fmrepexservice.usermanagement.business.user.Technician;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;
import com.example.fmrepexservice.workordermanagement.business.WorkOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class ManagerController {
    private APIAuthenticationService authenticationService;
    private APIManagerService managerService;
    private APIBuildingService buildingService;
    @Autowired
    private APIUserService userService;

    @Autowired
    private APIRequestService requestService;

    @Autowired
    private APIWorkOrderService workOrderService;

    @Autowired
    private APICompanyService companyService;

    @Autowired
    private APIAnnouncementService announcementService;
    private Map<String, Object> feedbackMap;
    private List userTypes;

    @Autowired
    public ManagerController(APIAuthenticationService apiAuthenticationService, APIManagerService apiManagerService, APIBuildingService apiBuildingService){
        authenticationService = apiAuthenticationService;
        managerService = apiManagerService;
        buildingService = apiBuildingService;
        feedbackMap = new HashMap<>();
        userTypes = new ArrayList<>(Arrays.asList("admin","tenant", "manager", "technician", "dependant"));
    }

    @PostMapping("api/new/manager")
    public Object registerManager(@RequestBody SignUp user){
        resetFeedback();
        if(user == null){
            return "Incorrect format";
        }
        if(userService.doesUserExist(user.getEmail())){
            feedbackMap.put("Error", "user already exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.CONFLICT);
        }

        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPass();
        String phone = user.getPhone();

        if(!validateFields(name, email, password, phone)){
            new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        return authenticationService.registerUser(name, email, password, phone, UserFactory.getType("manager"));
    }

    @PostMapping("api/manager/new/tenant")
    public Object addTenant(@RequestBody SignUp user){
        resetFeedback();
        if(user == null){
            return "Incorrect format";
        }
        if(userService.doesUserExist(user.getEmail())){
            feedbackMap.put("Error", "user already exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.CONFLICT);
        }

        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPass();
        String phone = user.getPhone();

        if(!validateFields(name, email, password, phone)){
            new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        managerService.registerTenant(name, email, password, phone, UserFactory.getType("tenant"));

        if(userService.doesUserExist(user.getEmail())){
            //feedbackMap.put("message", "Tenant successfully added");
            return new ResponseEntity<>(userService.getUser(user.getEmail()), HttpStatus.OK);
        }else {
            feedbackMap.put("message", "Error occurred adding tenant");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("api/manager/new/technician")
    public Object addTechnician(@RequestBody SignUp user){
        resetFeedback();
        if(user == null){
            return "Incorrect format";
        }
        if(userService.doesUserExist(user.getEmail())){
            feedbackMap.put("Error", "user already exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.CONFLICT);
        }

        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPass();
        String phone = user.getPhone();

        if(!validateFields(name, email, password, phone)){
            new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        managerService.registerTechnician(name, email, password, phone, UserFactory.getType("technician"));

        if(userService.doesUserExist(user.getEmail())){
            //feedbackMap.put("message", "Technician successfully added");
            return new ResponseEntity<>(userService.getUser(user.getEmail()), HttpStatus.OK);
        }else {
            feedbackMap.put("message", "Error occurred adding technician");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
    }


    private boolean validateFields(String name, String email, String password, String phone){
        if(!Validator.validateName(name)){
            feedbackMap.put("Error", "name empty (name should have at least 1 character)");
            return false;
        }
        if(!Validator.validatePassword(password)){
            feedbackMap.put("Error", "password empty (name should have at least 8 character)");
            return false;
        }
        if(!Validator.validateEmail(email)){
            feedbackMap.put("Error", "email empty (email should have at least 3 character)");
            return false;
        }
        if(!Validator.validatePhone(phone)){
            feedbackMap.put("Error", "phone empty (phone should have at least 10 character)");
            return false;
        }
        return true;
    }

    @PostMapping("api/manager/new/building")
    public Object addBuilding(@RequestBody NewBuilding building){
        resetFeedback();
        if(building == null){
            feedbackMap.put("error","incorrect format");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        ManagedBuilding newBuilding = new ManagedBuilding();
        Address address = new Address(building.getHouseNo(), building.getStreetName(), building.getTownName(), State.valueOf(building.getState()));
        Coordinate coordinate = new Coordinate(building.getLongitude(), building.getLatitude());

        newBuilding.setName(building.getName());
        newBuilding.setNoOfFlats(building.getNoOfFlats());
        newBuilding.setAddress(address);
        newBuilding.setCoordinate(coordinate);

        String id = managerService.addBuilding(newBuilding);
        if(id != null && buildingService.doesBuildingExist(id)){
            feedbackMap.put("message", "Building successfully added");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
        }else {
            feedbackMap.put("message", "Error occurred adding building");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("api/manager/assign/tenant/{email}/building/{buildingId}")
    public Object assignBuilding(@RequestBody AssignedLabel label, @PathVariable String email, @PathVariable String buildingId) {
        resetFeedback();
        if (label == null || buildingId == null || buildingId.isEmpty() || email == null || email.isEmpty()) {
            feedbackMap.put("error", "incorrect format");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        if(!userService.doesUserExist(email) || !buildingService.doesBuildingExist(buildingId)){
            feedbackMap.put("error", "Tenant or Building does not exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        ManagedBuilding building = (ManagedBuilding) buildingService.getBuilding(buildingId);
        User user = userService.getUser(email);
        User manager = Helper.retrieveUser();

        if(user.getUserType() != UserType.TENANT){
            feedbackMap.put("error", "User must be a tenant");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        if(!((Manager) manager).getEmail().equalsIgnoreCase(building.getManagerEmail())){
            feedbackMap.put("error", "Invalid request");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }

        if(!((Tenant) user).getManagerEmail().equalsIgnoreCase(building.getManagerEmail())){
            feedbackMap.put("error", "Cannot assign tenant to building");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }

        if (((Tenant) user).hasAnAssignedBuilding()) {
            feedbackMap.put("error", "Tenant has already been assigned a building");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }

        managerService.assignTenent(manager, user, building, label.getLabel());

        User editedTenant = userService.getUser(email);

        if (((Tenant)editedTenant).hasAnAssignedBuilding()) {
            feedbackMap.put("error", "Tenant has been assigned successfully");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
        } else {
            feedbackMap.put("error", "Tenant has been assigned a building already");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("api/manager/unassign/tenant/{email}/building/{buildingId}")
    public Object unAssignBuilding(@RequestBody AssignedLabel label, @PathVariable String email, @PathVariable String buildingId) {
        resetFeedback();
        if (label == null || buildingId == null || buildingId.isEmpty() || email == null || email.isEmpty()) {
            feedbackMap.put("error", "incorrect format");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        if(!userService.doesUserExist(email) || !buildingService.doesBuildingExist(buildingId)){
            feedbackMap.put("error", "Tenant or Building does not exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        ManagedBuilding building = (ManagedBuilding) buildingService.getBuilding(buildingId);
        User user = userService.getUser(email);
        User manager = Helper.retrieveUser();

        if(user.getUserType() != UserType.TENANT){
            feedbackMap.put("error", "User must be a tenant");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        if(!((Manager) manager).getEmail().equalsIgnoreCase(building.getManagerEmail())){
            feedbackMap.put("error", "Invalid request");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }

        if(!((Tenant) user).getManagerEmail().equalsIgnoreCase(building.getManagerEmail())){
            feedbackMap.put("error", "Cannot un-assign tenant to building");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }

        if (!((Tenant) user).hasAnAssignedBuilding()) {
            feedbackMap.put("error", "Tenant doesn't an assigned a building");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }

        managerService.assignTenent(manager, user, building, label.getLabel());

        User editedTenant = userService.getUser(email);

        if (!((Tenant)editedTenant).hasAnAssignedBuilding()) {
            feedbackMap.put("error", "Tenant un-assigned successfully");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
        } else {
            feedbackMap.put("error", "Tenant not un-assigned");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("api/manager/work-order")
    public Object createWorkOrder(@RequestBody NewWorkOrder workOrder) {
        resetFeedback();
        if (workOrder == null) {
            feedbackMap.put("error", "incorrect format");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        if(!userService.doesUserExist(workOrder.getTechnicianEmail()) || !requestService.doesRequestExist(workOrder.getRequestId())){
            feedbackMap.put("error", "technician or request does not exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        if(workOrderService.doesWorkOrderWithRequestExist(workOrder.getRequestId())){
            feedbackMap.put("error", "work order with request does exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        Request request = requestService.getRequest(workOrder.getRequestId());
        User user = userService.getUser(workOrder.getTechnicianEmail());
        User manager = Helper.retrieveUser();

        if(user.getUserType() != UserType.TECHNICIAN){
            feedbackMap.put("error", "User must be a technician");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        if(!((Manager) manager).getEmail().equalsIgnoreCase(((Technician)user).getManagerEmail())){
            feedbackMap.put("error", "Technician cannot be assigned to the work order");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }

        if(!((Manager) manager).getEmail().equalsIgnoreCase(request.getManagerEmail())){
            feedbackMap.put("error", "Invalid request");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }

        WorkOrder createdWorkOrder = new WorkOrder();
        createdWorkOrder.setDescription(workOrder.getDescription());
        createdWorkOrder.setCreatedDateTime(LocalDateTime.now());
        String id = managerService.createWorkOrder(manager, user, createdWorkOrder, request);

        if (workOrderService.doesWorkOrderExist(id)) {
            feedbackMap.put("error", "Work order has been created successfully");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
        } else {
            feedbackMap.put("error", "Work order not created");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }
    }

    /**@GetMapping("api/manager/{email}/building")
    public Object getManagerBuildings(@PathVariable String email){
        resetFeedback();
        User manager = Helper.retrieveUser();
        if(!manager.getEmail().equalsIgnoreCase(email)){
            feedbackMap.put("error", "request not allowed");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }
        return buildingService.getBuildingsByManager(email);
    }

    @GetMapping("api/building")
    public Object getBuildings(){
        return buildingService.getBuildings();
    }
*/

    @PostMapping("api/manager/new/company")
    public Object addCompany(@RequestBody NewCompany company){
        resetFeedback();
        if(company == null){
            feedbackMap.put("error","incorrect format");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        Company newCompany = new Company();
        Address address = new Address(company.getHouseNo(), company.getStreetName(), company.getTownName(), State.valueOf(company.getState()));

        newCompany.setName(company.getName());
        newCompany.setAddress(address);

        String id = managerService.addCompany(newCompany);
        if(id != null && companyService.doesCompanyExist(id)){
            feedbackMap.put("message", "Company successfully added");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
        }else {
            feedbackMap.put("message", "Error occurred adding company");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("api/manager/new/announcement")
    public Object newAnnouncement(@RequestBody NewAnnouncement announcement){
        resetFeedback();
        if(announcement == null){
            feedbackMap.put("error","incorrect format");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        Manager user = (Manager) Helper.retrieveUser();

        Announcement newAnnouncement = new Announcement();

        newAnnouncement.setMessage(announcement.getMessage());
        newAnnouncement.setManagerEmail(user.getEmail());
        newAnnouncement.setCreatedDateTime(LocalDateTime.now());

        String id = managerService.addAnnouncement(newAnnouncement);

        if(announcementService.doesAnnouncementExist(id)){
            //feedbackMap.put("message", "Announcement successfully added");
            return new ResponseEntity<>(announcementService.getAnnouncement(id), HttpStatus.OK);
        }else{
            feedbackMap.put("message", "Error occurred adding announcement");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("api/manager/announcement/{aid}")
    public Object editAnnouncement(@RequestBody NewAnnouncement announcement, @PathVariable String aid){
        resetFeedback();
        if(announcement == null){
            feedbackMap.put("error","incorrect format");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        if(!announcementService.doesAnnouncementExist(aid)){
            feedbackMap.put("message", "Announcement does not exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.NOT_FOUND);
        }

        Manager user = (Manager) Helper.retrieveUser();

        Announcement editedAnnouncement = announcementService.getAnnouncement(aid);

        if(!editedAnnouncement.getManagerEmail().equalsIgnoreCase(user.getEmail())){
            feedbackMap.put("message", "Announcement can not be updated");
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        }

        editedAnnouncement.setMessage(announcement.getMessage());
        editedAnnouncement.setCreatedDateTime(LocalDateTime.now());

        String id = managerService.addAnnouncement(editedAnnouncement);

        if(announcementService.doesAnnouncementExist(id)){
            //feedbackMap.put("message", "Announcement successfully updated");
            return new ResponseEntity<>(announcementService.getAnnouncement(id), HttpStatus.OK);
        }else{
            feedbackMap.put("message", "Error occurred updating announcement");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
    }

    public void resetFeedback(){
        feedbackMap = new HashMap<>();
    }
}
