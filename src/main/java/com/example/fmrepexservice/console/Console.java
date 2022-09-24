package com.example.fmrepexservice.console;

import com.example.fmrepexservice.usermanagement.business.User;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class Console {

    protected Scanner scanner;
    protected User user;

    public Console(Scanner scanner, User user){
        this.scanner = scanner;
        this.user = user;
    }

    protected int getSelectedNumber(){

        int c = 99;
        try {
            c = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("Enter a valid number");
        }

        return c;
    }

    abstract public void menuDisplay();
    abstract public int handleSelection();
}
