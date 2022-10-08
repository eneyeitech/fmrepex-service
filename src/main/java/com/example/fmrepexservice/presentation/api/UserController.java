package com.example.fmrepexservice.presentation.api;

import com.example.fmrepexservice.business.APIUserService;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.Technician;
import com.example.fmrepexservice.usermanagement.business.user.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private APIUserService userService;

    private Map<String, Object> feedbackMap;

    @Autowired
    public UserController(APIUserService apiUserService){
        userService = apiUserService;
    }

    @GetMapping("api/users")
    public Object getUsers(){
        return userService.getUsers();
    }

    @DeleteMapping("api/{email}")
    public Object deleteUser(@PathVariable String email){
        feedbackMap = new HashMap<>();
        if(!userService.doesUserExist(email)){
            feedbackMap.put("message", "User does not exist");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
        User loggedInUser = Helper.retrieveUser();
        if(loggedInUser.getUserType() != UserType.MANAGER){
            feedbackMap.put("message", "Action not approved for user");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }
        User userToDelete = userService.getUser(email);

        if(userToDelete.getUserType() == UserType.MANAGER || userToDelete.getUserType() == UserType.ADMINISTRATOR){
            feedbackMap.put("message", "User can not be deleted");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }

        if(userToDelete.getUserType() == UserType.TENANT){
            if(((Tenant)userToDelete).getManagerEmail() != loggedInUser.getEmail()){
                feedbackMap.put("message", "Invalid: User can not be deleted");
                return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
            }
        }

        if(userToDelete.getUserType() == UserType.TECHNICIAN){
            if(((Technician)userToDelete).getManagerEmail() != loggedInUser.getEmail()){
                feedbackMap.put("message", "Invalid: User can not be deleted");
                return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
            }
        }

        userService.deleteUser(email);

        if(userService.doesUserExist(email)){
            feedbackMap.put("message", "User not deleted");
            return new ResponseEntity<>(feedbackMap, HttpStatus.BAD_REQUEST);
        }else{
            feedbackMap.put("message", "User deleted successfully");
            return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
        }
    }

    @GetMapping("api/manager/users")
    public Object getUsersByManager(){
        User user = Helper.retrieveUser();
        return userService.getUsersByManager(user.getEmail());
    }

    @GetMapping("api/tenant/dependants")
    public Object getDependantsByTenantQ(){
        User user = Helper.retrieveUser();
        return userService.getDependantsByTenant(user.getEmail());
    }

    @GetMapping("api/admin/users")
    public Object getAllUsers(){
        User user = Helper.retrieveUser();
        return userService.getUsers().stream().filter(u->!u.getEmail().equalsIgnoreCase(user.getEmail()));
    }

    @GetMapping("api/manager/{email}/tenant")
    public Object getTenantsByManager(@PathVariable String email){
        return userService.getTenantsByManager(email);
    }

    @GetMapping("api/tenant/{email}/dependant")
    public Object getDependantsByTenant(@PathVariable String email){
        return userService.getDependantsByTenant(email);
    }

    @GetMapping("api/tenant/building/{id}")
    public Object getTenantsByBuilding(@PathVariable String id){
        return userService.getTenantsByBuilding(id);
    }

    @GetMapping("api/dependant/building/{id}")
    public Object getDependantsByBuilding(@PathVariable String id){
        return userService.getDependantsByBuilding(id);
    }

    @GetMapping("api/tenant")
    public Object getTenants(){
        return userService.getTenants();
    }

    @GetMapping("api/dependant")
    public Object getDependants(){
        return userService.getDependants();
    }

    @GetMapping("api/user/{id}")
    public Object getUser(@PathVariable String id){
        return userService.getUser(id);
    }

    @GetMapping("api/slug/user")
    public Object getUserBySlug(@RequestParam String slug){
        return userService.getUser(slug);
    }

    @GetMapping("api/manager")
    public Object getManagers(){
        return userService.getManagers();
    }
}
