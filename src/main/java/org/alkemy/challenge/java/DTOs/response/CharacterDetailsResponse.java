package org.alkemy.challenge.java.DTOs.response;

import java.util.ArrayList;
import java.util.List;

import org.alkemy.challenge.java.DTOs.MovieDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CharacterDetailsResponse {
    
    private Long id;
    
    private String image;
    
    private String name;
    
    private Integer age;
    
    private Double weight;
    
    private String history;
    
    private List<MovieDTO> movieDTOs = new ArrayList<>();

}
