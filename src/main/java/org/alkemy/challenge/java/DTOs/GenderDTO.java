package org.alkemy.challenge.java.DTOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenderDTO implements Serializable{
    
    //@JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @NotEmpty(message = "[ERROR] The value cannot be empty or null")
    private String name;
    
    @NotBlank
    @Size(min = 5, message = "[ERROR] Insert a value of at least 5 characters")
    private String image;
    
    // LAS DOS VAN JUNTAS...
    //@JsonProperty(access= JsonProperty.Access.READ_ONLY)
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private List<MovieDTO> movieDTOs = new ArrayList<>();

}
