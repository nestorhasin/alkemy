package org.alkemy.challenge.java.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
//@Data
@Getter
@Setter
public class LoginDTO {

    private String usernameOrEmail;
    
    private String password;
    
}
