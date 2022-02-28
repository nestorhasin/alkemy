package org.alkemy.challenge.java.DTOs.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.alkemy.challenge.java.DTOs.MovieDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GenderDetailsResponse implements Serializable {
    
    private Long id;
    
    private String name;
    
    private String image;
    
    private List<MovieDTO> movieDTOs = new ArrayList<>();

}
