package com.example.fmrepexservice.builder;

import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.UserType;

import java.util.List;
import java.util.Scanner;

public class UserBuilder {
    private UserService userService;
    private Scanner scanner;
    private String email;
    private String phoneNumber;
    private String fullName;
    private String password;
    private String type = null;

    public UserBuilder(Scanner scanner){
        this.scanner = scanner;
        fullName = getString("Enter full name: ");
        email = getString("Enter email address: ");
        phoneNumber = getString("Enter phone number: ");
        password = getString("Enter password: ");
    }

    public UserBuilder(Scanner scanner, String nothing){
        this.scanner = scanner;
        fullName = getString("Enter first name: ");
        email = getString("Enter email address: ");
        phoneNumber = getString("Enter phone number: ");
        password = getString("Enter password: ");
        type = getType();
    }

    public UserBuilder(UserService userService){
        this.userService = userService;
    }

    public User queryUser(String email){
        return (User) userService.get(email);
    }

    public List<User> queryUsers(){
        return (List<User>) userService.getAll();
    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public User getUser(String userType){
        User newUser = UserFactory.getUser(UserType.valueOf(userType));
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setFullName(fullName);
        return newUser;
    }

    public User getUser(){
        if(type == null){
            type = getType();
        }
        User newUser = UserFactory.getUser(UserType.valueOf(type));
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setFullName(fullName);
        return newUser;
    }

    private String getType(){
        showPrompt("Enter one of the following admin|tenant|dependant|manager|technician");
        String type = scanner.nextLine();
        switch (type){
            case "admin":
                return UserType.ADMINISTRATOR.name();
            case "tenant":
                return UserType.TENANT.name();
            case "dependant":
                return UserType.DEPENDANT.name();
            case "manager":
                return UserType.MANAGER.name();
            case "technician":
                return UserType.TECHNICIAN.name();
            default:
                return "";
        }
    }
}
