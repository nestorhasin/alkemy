package org.alkemy.challenge.java.controllers;

import java.util.Collections;

import org.alkemy.challenge.java.DTOs.LoginDTO;
import org.alkemy.challenge.java.DTOs.RegisterDTO;
import org.alkemy.challenge.java.entities.Role;
import org.alkemy.challenge.java.entities.User;
import org.alkemy.challenge.java.repositories.IRoleRepository;
import org.alkemy.challenge.java.repositories.IUserRepository;
import org.alkemy.challenge.java.securities.JWTAuthResponseDTO;
import org.alkemy.challenge.java.securities.JwtTokenProvider;
import org.alkemy.challenge.java.services.EmailServiceImpl;
import org.alkemy.challenge.java.services.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IRoleRepository iRoleRepository;

    @Autowired
    private IEmailService iEmailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
            System.out.println("AUTHENTICATION: " + authentication.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
            System.out.println("TOKEN: " + token);
        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
        if(iUserRepository.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>("[FAILURE] User already registered with username", HttpStatus.BAD_REQUEST);
        }
        if(iUserRepository.existsByEmail(registerDTO.getEmail())){
            return new ResponseEntity<>("[FAILURE] User already registered with email", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        Role role = iRoleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));
        User userRegister = iUserRepository.save(user);
        iEmailService.sendEmail(userRegister);
        //return new ResponseEntity<>(userRegister, HttpStatus.OK);
        return new ResponseEntity<>("[SUCCESS] Registration successfull", HttpStatus.OK);
    }

}
