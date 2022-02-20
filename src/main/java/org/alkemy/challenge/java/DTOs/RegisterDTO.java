package org.alkemy.challenge.java.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
//@Data
@Getter
@Setter
public class RegisterDTO {
    
    private String name;

    private String username;

    private String email;

    private String password;

}
