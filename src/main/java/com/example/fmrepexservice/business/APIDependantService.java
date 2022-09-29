package com.example.fmrepexservice.business;

import com.example.fmrepexservice.command.Command;
import com.example.fmrepexservice.command.RequestCommand;
import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.requestmanagement.business.RequestService;
import com.example.fmrepexservice.security.PasswordEncoderConfig;
import com.example.fmrepexservice.usermanagement.business.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class APIDependantService {

    private PasswordEncoderConfig passwordEncoderConfig;


    @Autowired
    public APIDependantService(PasswordEncoderConfig passwordEncoderConfig){
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    public void signOffRequest(User user, Request request){

        Command command = new RequestCommand(user, request, new RequestService());
        new com.example.fmrepexservice.command.EmailNotifier(command);
        command.actionRequester();
    }
}
