package org.alkemy.challenge.java.DTOs.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.GenderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieDetailsResponse {

    private Long id;
    
    private String image;
    
    private String title;
    
    private LocalDate creationDate;
    
    private Integer qualification;
    
    private List<CharacterDTO> characterDTOs = new ArrayList<>();
    
    private GenderDTO genderDTO;

}
