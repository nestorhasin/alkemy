package org.alkemy.challenge.java.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movies")

@NoArgsConstructor
@AllArgsConstructor
//@Data
@Getter
@Setter
public class Movie implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id", unique = true, nullable = false)
    private Long id;
    
    @NotBlank
    private String image;

    //@Lob
    //private byte[] image;
    
    @NotBlank
    private String title;
    
    private LocalDate creationDate;
    
    private Integer qualification;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "movie_character",
        joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "character_id", referencedColumnName = "character_id"))
    @JsonManagedReference
    private List<Character> characters = new ArrayList<>();

    // SIEMPRE EN @ManyToOne PONER fetch = FetchType.LAZY PORQUE POR DEFECTO ES fetch = FetchType.EAGER
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id")
    @JsonBackReference
    private Gender gender;

    // [GENDER] ACTIVE COMMUNICATION
    public void addGender(Gender gender){
        if(this.gender == null){
            setGender(gender);
            gender.addMovie(this);
        }
    }

    // [CHARACTER] ACTIVE COMMUNICATION
    public void addCharacter(Character character){
        if(!this.characters.contains(character)){
            this.characters.add(character);
            character.addMovie(this);
        }
    }

    // [GENDER] ACTIVE COMMUNICATION
    public void removeGender(Gender gender){
        if(this.gender.equals(gender)){
            this.gender = null;
            gender.removeMovie(this);
        }
    }

    // [CHARACTER] ACTIVE COMMUNICATION
    public void removeCharacter(Character character){
        if(this.characters.contains(character)){
            this.characters.remove(character);
            character.removeMovie(this);
        }
    }

}
