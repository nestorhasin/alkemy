package org.alkemy.challenge.java.DTOs.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CharacterResponse implements Serializable {

    private String image;
    
    private String name;
    
}
