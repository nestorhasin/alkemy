package org.alkemy.challenge.java.repositories;

import java.io.Serializable;
import java.util.List;

import org.alkemy.challenge.java.entities.Character;
import org.alkemy.challenge.java.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICharacterRepository extends JpaRepository<Character, Serializable> {
    List<Character> findAllByName(String name);
    List<Character> findAllByAge(Integer age);
    List<Character> findAllByWeight(Double weight);
    List<Character> findAllByMovies(Movie movie);
}
