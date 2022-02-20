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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movies")

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Movie implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id", unique = true, nullable = false)
    private Long id;
    
    @NotBlank
    private String image;
    
    @NotBlank
    private String title;
    
    private LocalDate creationDate;
    
    private Integer qualification;
    
    //@JsonBackReference
    @ManyToMany(mappedBy = "movies")
    private List<Character> characters = new ArrayList<>();

    // SIEMPRE EN @ManyToOne PONER fetch = FetchType.LAZY PORQUE POR DEFECTO ES fetch = FetchType.EAGER
    //@JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id")
    private Gender gender;

}
