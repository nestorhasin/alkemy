package org.alkemy.challenge.java.securities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
//@Data
@Getter
@Setter
public class JWTAuthResponseDTO {

    private String tokenAccess;
    
    private String tokenType = "Bearer";

    public JWTAuthResponseDTO(String token){
        setTokenAccess(token);
    }

}
