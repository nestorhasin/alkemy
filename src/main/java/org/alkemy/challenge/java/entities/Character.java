package org.alkemy.challenge.java.entities;

import java.io.Serializable;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "characters")

@NoArgsConstructor
@AllArgsConstructor
//@Data
@Getter
@Setter
public class Character implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id", unique = true, nullable = false)
    private Long id;

    private String image;
    
    private String name;
    
    private Integer age;
    
    private Double weight;
    
    private String history;
    
    @ManyToMany(mappedBy = "characters", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Movie> movies = new ArrayList<>();

    // ACTIVE COMMUNICATION
    public void addMovie(Movie movie){
        if(!this.movies.contains(movie)){
            this.movies.add(movie);
            movie.addCharacter(this);
        }
    }

    // ACTIVE COMMUNICATION
    public void removeMovie(Movie movie){
        if(this.movies.contains(movie)){
            this.movies.remove(movie);
            movie.removeCharacter(this);
        }
    }

}
