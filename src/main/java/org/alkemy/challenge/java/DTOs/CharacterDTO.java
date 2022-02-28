package org.alkemy.challenge.java.DTOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class CharacterDTO implements Serializable {
    
    //@JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @NotBlank
    @Size(min = 5, message = "[ERROR] Insert a value of at least 5 characters")
    private String image;

    //private byte[] image;
    
    @NotBlank
    @Size(min = 3, message = "[ERROR] Insert a value of at least 3 characters")
    private String name;
    
    @Min(value = 1, message = "[ERROR] The minimun value is 1")
    private Integer age;
    
    @Min(value = 1, message = "[ERROR] The minimun value is 1")
    private Double weight;
    
    @NotEmpty(message = "[ERROR] The value cannot be empty or null")
    private String history;
    
    // LAS DOS VAN JUNTAS...
    //@JsonProperty(access= JsonProperty.Access.READ_ONLY)
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private List<MovieDTO> movieDTOs = new ArrayList<>();

}
