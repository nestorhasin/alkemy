package org.alkemy.challenge.java.DTOs.response;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieResponse implements Serializable {
    
    private String image;
    private String title;
    private LocalDate creationDate;

}
