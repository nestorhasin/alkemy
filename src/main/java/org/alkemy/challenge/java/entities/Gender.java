package org.alkemy.challenge.java.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "genders")

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Gender implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genero_id", unique = true, nullable = false)
    private Long id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String image;

    @OneToMany(mappedBy = "gender", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Movie> movies = new ArrayList<>();

    // COMUNICACIÓN ACTIVA
    public void setMovies(List<Movie> movies){
        this.movies = movies;
        /*
        for(Movies movie: movies){
            movie.setGender(this);
        }
        */
    }

    public void addMovie(Movie movie){
        if(!this.movies.contains(movie)){
            this.movies.add(movie);
            movie.setGender(this);
        }
    }

    public void removeMovie(Movie movie){
        if(this.movies.contains(movie)){
            this.movies.remove(movie);
            movie.setGender(null);
        }
    }

    // ESTO LO UTILIZAMO SI O SI SIN CASCADE @OneToMany(mappedBy = "genero")
    // @PreRemove
    public void nullification(){
        this.movies.forEach(movie -> movie.setGender(null));
    }

}
