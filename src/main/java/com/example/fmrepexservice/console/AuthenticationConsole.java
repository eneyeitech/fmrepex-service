package com.example.fmrepexservice.console;

import com.example.fmrepexservice.authentication.*;
import com.example.fmrepexservice.builder.UserBuilder;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.UserType;
import com.example.fmrepexservice.usermanagement.business.user.YetToLogin;

import java.util.Scanner;

public class AuthenticationConsole extends Console{

    private UserService userService;
    public AuthenticationConsole(Scanner scanner, User user){
        super(scanner, user);
        userService = new UserService();
    }
    @Override
    public void menuDisplay() {
        System.out.println(menu());
    }

    public String menu(){
        return "" +
                "1. Login\n" +
                "2. Manager Sign-up\n" +
                "0. Exit\n" +
                "";
    }

    @Override
    public int handleSelection() {
        return 0;
    }

    public User handle(){
        int selection = getSelectedNumber();
        switch (selection){
            case 1:
                return loginUser();
            case 2:
               managerSignup();
            case 0:
            default:
                return null;
        }
    }

    public void managerSignup(){
        showPrompt("Manager Sign-up");
        String type = UserType.MANAGER.toString();
        UserBuilder userBuilder = new UserBuilder(scanner);
        User newUser = userBuilder.getUser(type);

        UserManagement registerManager = new Registration(newUser, userService);
        new EmailNotifier(registerManager);
        User user = registerManager.handle();

        if(user!=null){
            System.out.println("Manager added!");
        } else {
            System.out.println("User exist");
        }
    }


    public User loginUser(){
        showPrompt("User login!");
        String email = getEmail("Enter email: ");
        String password = getPassword("Enter password: ");

        UserManagement loginManager = new Login(new YetToLogin(email, password), userService);
        new SecurityMonitor(loginManager);
        new GeneralLogger(loginManager);
        new EmailNotifier(loginManager);

        User user = loginManager.handle();

        if(user == null){
            System.out.println("Credentials not available");
        }else{
            System.out.println("Login successful!");
        }
        return user;
    }

    private void showPrompt(String prompt){
        System.out.println(prompt);
    }
    private String getEmail(String msg){
        showPrompt(msg);
        String email = scanner.nextLine();
        return email;
    }

    private String getPassword(String msg){
        showPrompt(msg);
        String password = scanner.nextLine();
        return password;
    }
}
