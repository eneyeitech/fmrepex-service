package com.example.fmrepexservice.business;

import com.example.fmrepexservice.command.Command;
import com.example.fmrepexservice.command.RequestCommand;
import com.example.fmrepexservice.command.UserCommand;
import com.example.fmrepexservice.helper.Helper;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.requestmanagement.business.RequestService;
import com.example.fmrepexservice.requestmanagement.helper.RequestIdGenerator;
import com.example.fmrepexservice.security.Group;
import com.example.fmrepexservice.security.PasswordEncoderConfig;
import com.example.fmrepexservice.security.UserDetailsImpl;
import com.example.fmrepexservice.usermanagement.business.User;
import com.example.fmrepexservice.usermanagement.business.UserFactory;
import com.example.fmrepexservice.usermanagement.business.UserService;
import com.example.fmrepexservice.usermanagement.business.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class APITenantService {

    private PasswordEncoderConfig passwordEncoderConfig;


    @Autowired
    public APITenantService(PasswordEncoderConfig passwordEncoderConfig){
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    public void registerDependant(String name, String email, String password, String phoneNumber, UserType type){

        User newUser = UserFactory.getUser(type);

        newUser.addUserGroups(UserFactory.getGroup(type));

        newUser.setFullName(name.toUpperCase(Locale.ROOT));
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setPassword(password);

        newUser.setPassword(passwordEncoderConfig.getEncoder().encode(newUser.getPassword()));
        newUser.setLocked(true);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = null;
        if (auth != null) {
            loggedInUser = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        }
        if(loggedInUser != null) {
            Command command = new UserCommand(loggedInUser, newUser, new UserService());
            new com.example.fmrepexservice.command.EmailNotifier(command);
            command.actionRequester();
        }
    }

    public String addRequest(Request newRequest){
        User user = Helper.retrieveUser();

        newRequest.setId((new RequestIdGenerator(10)).generate());

        Command command = new RequestCommand(user, newRequest,new RequestService());
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();

        return newRequest.getId();
    }


}
