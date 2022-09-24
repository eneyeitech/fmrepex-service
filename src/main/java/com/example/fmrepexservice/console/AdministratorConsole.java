package com.example.fmrepexservice.console;

import com.example.fmrepexservice.builder.UserBuilder;
import com.example.fmrepexservice.command.Command;
import com.example.fmrepexservice.command.UserCommand;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserService;

import java.util.List;
import java.util.Scanner;

public class AdministratorConsole extends Console{
    private UserService userService;
    public AdministratorConsole(Scanner scanner, User user){
        super(scanner, user);
        userService = new UserService();

    }

    public String menu(){
        return "" +
                "1. Approve user\n" +
                "2. List users\n" +
                "0. Back\n" +
                "";
    }

    @Override
    public void menuDisplay() {
        System.out.println(menu());
    }

    @Override
    public int handleSelection() {
        int selection = getSelectedNumber();
        switch (selection){
            case 1:
                approveUser(user);
                return 1;
            case 2:
                listUsers();
                return 2;
            case 0:
                return 0;
            default:
                return 10;
        }
    }

    public User approveUser(User admin){

        String email = getEmail("Please enter user email: ");
        UserBuilder userBuilder = new UserBuilder(userService);
        User userToApprove = userBuilder.queryUser(email);
        if (userToApprove==null){
            System.out.println("User does not exist!");
        }else{
            Command command = new UserCommand(admin, userToApprove, userService);
            new com.example.fmrepexservice.command.EmailNotifier(command);
            command.actionRequester();
        }
        return userToApprove;
    }

    public void listUsers(){
        showPrompt("User list!");
        UserBuilder userBuilder = new UserBuilder(userService);
        List<User> list = userBuilder.queryUsers();
        if(list==null){
            return;
        }
        int i = 0;
        for(User user:list){
            System.out.printf("%s: %s(%s) - %s | %s.\n",++i, user.getFullName(), user.getUserType().toString(), user.getEmail(), user.isApproved());
        }
    }

    private void showPrompt(String prompt){
        System.out.println(prompt);
    }
    private String getEmail(String msg){
        showPrompt(msg);
        String email = scanner.nextLine();
        return email;
    }
}
