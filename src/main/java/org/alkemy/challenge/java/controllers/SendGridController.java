package org.alkemy.challenge.java.controllers;

import java.io.IOException;

import com.sendgrid.Response;

import org.alkemy.challenge.java.DTOs.SendGridDTO;
import org.alkemy.challenge.java.services.SendGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
@RequestMapping("/email")
public class SendGridController {
    
    @Autowired
    private SendGridService emailService;

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody SendGridDTO emailRequest) throws IOException{
        String response= emailService.sendEmail(emailRequest);
        return new ResponseEntity<>("envio de email exitoso", HttpStatus.OK);
    }

}
