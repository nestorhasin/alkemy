package org.alkemy.challenge.java.repositories;

import java.io.Serializable;
import java.util.List;

import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Serializable>{
    List<Movie> findAllByTitle(String title);
    List<Movie> findAllByGender(Gender gender);
}
