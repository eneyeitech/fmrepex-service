package com.example.fmrepexservice.usermanagement.business;

public class AuthenticationService {

    UserService userService;

    public AuthenticationService(UserService userService){
        this.userService = userService;
    }

    public User login(String email, String password){
        User user = (User) userService.get(email);

        if(user != null && password.equals(user.getPassword())){
            return user;
        }

        return null;
    }

    public boolean signup(User user){
        return userService.add(user);
    }
}
