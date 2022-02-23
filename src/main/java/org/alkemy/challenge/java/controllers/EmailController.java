package org.alkemy.challenge.java.controllers;

import org.alkemy.challenge.java.services.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    
    @Autowired
    private IEmailService iEmailService;

    @GetMapping("/plain/{email}")
    public ResponseEntity<?> sendText(@PathVariable String email){
        iEmailService.sendText(email);
        return new ResponseEntity<>("[INFO] Check your e-mail", HttpStatus.OK);
    }

    @GetMapping("/html/{email}")
    public ResponseEntity<?> sendHTML(@PathVariable String email){
        iEmailService.sendHTML(email);
        return new ResponseEntity<>("[INFO] Check your e-mail", HttpStatus.OK);
    }

}
