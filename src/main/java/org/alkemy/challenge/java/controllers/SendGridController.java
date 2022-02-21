package org.alkemy.challenge.java.controllers;

import java.io.IOException;

import org.alkemy.challenge.java.DTOs.SendGridDTO;
import org.alkemy.challenge.java.services.SendGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/email")
public class SendGridController {
    
    @Autowired
    private SendGridService emailService;

    @PostMapping
    public ResponseEntity<String> sendEmail(@org.springframework.web.bind.annotation.RequestBody SendGridDTO emailRequest) throws IOException{
        String response= emailService.sendEmail(emailRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
