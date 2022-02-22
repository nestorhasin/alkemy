package org.alkemy.challenge.java.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MovieRepositoryTest {
    
    @Autowired
    private IMovieRepository iMovieRepository;

    @Test
    public void saveTest(){
        Movie movie = new Movie();
            movie.setImage("image movie");
            movie.setTitle("title movie");
            movie.setCreationDate(LocalDate.of(2020, 02, 02));
            movie.setQualification(10);
        Movie movieFinal = iMovieRepository.save(movie);
        assertNotNull(movieFinal);
    }

    @Test
    public void findAllTest(){
        List<Movie> movies = iMovieRepository.findAll();
        Assertions.assertThat(movies).size().isGreaterThan(0);
    }

    @Test
    public void findById(){
        Long id = 1L;
        Optional<Movie> movie = iMovieRepository.findById(id);
        assertTrue(movie.isPresent());
    }

    @Test
    public void findAllByTitleTest(){
        String title = "title 2";
        List<Movie> movie = iMovieRepository.findAllByTitle(title);
        assertTrue(movie.size() >= 1);
    }

    @Test
    public void findAllByGender(){
        Gender gender = new Gender(1L, "name 1", "image 1", null);
        List<Movie> movie = iMovieRepository.findAllByGender(gender);
        Assertions.assertThat(movie).size().isGreaterThan(0);
    }

    @Test
    public void deteleTest(){
        Long id = 1L;
        Optional<Movie> movie = iMovieRepository.findById(id);
        assertTrue(movie.isPresent());
        iMovieRepository.delete(movie.get());
        Optional<Movie> movieFinal = iMovieRepository.findById(id);
        assertFalse(movieFinal.isPresent());
    }

}
