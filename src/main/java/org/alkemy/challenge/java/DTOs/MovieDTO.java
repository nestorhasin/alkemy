package org.alkemy.challenge.java.DTOs;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieDTO implements Serializable{
    
    //@JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @NotBlank
    @Size(min = 5, message = "[ERROR] Insert a value of at least 5 characters")
    private String image;
    
    @NotBlank
    @Size(min = 3, message = "[ERROR] Insert a value of at least 3 characters")
    private String title;
    
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    //@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDate creationDate;
    
    @Min(value = 1, message = "[ERROR] The minimun value is 1")
    private Integer qualification;
    
    // LAS DOS VAN JUNTAS...
    //@JsonProperty(access= JsonProperty.Access.READ_ONLY)
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private List<CharacterDTO> characterDTOs = new ArrayList<>();
    
    @JsonIgnore
    private GenderDTO genderDTO;

    public Boolean addCharacter(CharacterDTO characterDTO){
        return this.characterDTOs.add(characterDTO);
    }

}
