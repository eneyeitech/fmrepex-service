package com.example.fmrepexservice.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Validator {

    private static List userTypes;

    static {
        userTypes = new ArrayList<>(Arrays.asList("admin","tenant", "manager", "technician", "dependant"));
    }

    public static boolean validateName(String name){
        if(name == null || name.trim().isEmpty() || name.length() < 1){
            return false;
        }
        return true;
    }
    public static boolean validateEmail(String email){
        if(email == null || email.trim().isEmpty() || email.length() < 3){
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String password){
        if(password == null || password.trim().isEmpty() || password.length() < 8){
            return false;
        }
        return true;
    }

    public static boolean validatePhone(String phone){
        if(phone == null || phone.trim().isEmpty() || phone.length() < 10){
            return false;
        }
        return true;
    }

    public static boolean validateType(String type){
        if(type == null || type.trim().isEmpty() || !userTypes.contains(type)){
            return false;
        }
        return true;
    }


}
