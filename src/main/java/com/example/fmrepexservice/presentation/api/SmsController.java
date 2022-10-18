package com.example.fmrepexservice.presentation.api;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @GetMapping(value = "/sendSMS")
    public ResponseEntity<String> sendSMS() {

        Twilio.init(System.getenv("AC91667badb79e3ae3272129f8a1658d84"), System.getenv("a1543c7f0dcd9b4685369fc634deb23f"));

        Message.creator(new PhoneNumber("+2348051185104"),
                new PhoneNumber("<FROM number - ie your Twilio number"), "Hello from Twilio 📞").create();

        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }
}
