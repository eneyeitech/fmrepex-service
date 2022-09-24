package com.example.fmrepexservice.console;

import com.example.fmrepexservice.usermanagement.business.User;

import java.util.Scanner;

public class ConsoleManager {
    private Scanner scanner;
    private User user;
    private Console console;

    public ConsoleManager(Scanner scanner, User user) {
        this.scanner = scanner;
        this.user = user;
        setConsole();
    }

    public Console getConsole() {
        if (user == null) {
            return null;
        }
        System.out.println(user);
        switch (user.getUserType()) {
            case ADMINISTRATOR:
                return new AdministratorConsole(scanner, user);
            case TECHNICIAN:
                return new TechnicianConsole(scanner, user);
            case DEPENDANT:
                return new DependantConsole(scanner, user);
            case MANAGER:
                return new ManagerConsole(scanner, user);
            case TENANT:
                return new TenantConsole(scanner, user);
            default:
                return null;
        }
    }

    public void setConsole() {
        System.out.println(user);
        switch (user.getUserType()) {
            case ADMINISTRATOR:
                console = new AdministratorConsole(scanner, user);
                break;
            case TECHNICIAN:
                console = new TechnicianConsole(scanner, user);
                break;
            case DEPENDANT:
                console = new DependantConsole(scanner, user);
                break;
            case MANAGER:
                console = new ManagerConsole(scanner, user);
                break;
            case TENANT:
                console = new TenantConsole(scanner, user);
                break;
            default:
                console = null;
        }
    }
    public void execute(){
        int c = 20;
        do{
            console.menuDisplay();
            c = console.handleSelection();
        }while (c!=0);
    }
}
