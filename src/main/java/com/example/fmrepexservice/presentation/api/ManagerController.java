package com.example.fmrepexservice.presentation.api;


import com.example.fmrepexservice.buildingmanagement.business.*;
import com.example.fmrepexservice.business.*;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.helper.Validator;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Manager;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ManagerController {
    private APIAuthenticationService authenticationService;
    private APIManagerService managerService;
    private APIBuildingService buildingService;
    @Autowired
    private APIUserService userService;
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
            feedbackMap.put("message", "Tenant successfully added");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
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
            feedbackMap.put("message", "Technician successfully added");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
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
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
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
            return new ResponseEntity<>(feedbackMap, HttpStatus.FORBIDDEN);
        } else {
            feedbackMap.put("error", "Tenant not un-assigned");
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
    public void resetFeedback(){
        feedbackMap = new HashMap<>();
    }
}
