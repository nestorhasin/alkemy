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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "characters")

@NoArgsConstructor
@AllArgsConstructor
@Data
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
    
    //@ManyToMany(cascade = CascadeType.ALL)
    //@JsonManagedReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "character_movie",
        joinColumns = @JoinColumn(name = "character_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "movie_id", nullable = false))
    private List<Movie> movies = new ArrayList<>();

}
