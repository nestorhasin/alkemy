package org.alkemy.challenge.java.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.alkemy.challenge.java.annotations.RepositoryTest;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.utils.EntitiesUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
public class MovieRepositoryTest {
    
    @Autowired
    private IMovieRepository iMovieRepository;

    @Test
    public void saveTest(){
        Movie movie = EntitiesUtil.MOVIE_ONE;
        Movie savedMovie = iMovieRepository.save(movie);
        assertNotNull(savedMovie);
    }

    @Test
    public void findAllTest(){
        Movie movieOne = EntitiesUtil.MOVIE_ONE;
        Movie movieTwo = EntitiesUtil.MOVIE_TWO;
        Movie movieThree = EntitiesUtil.MOVIE_THREE;
        iMovieRepository.save(movieOne);
        iMovieRepository.save(movieTwo);
        iMovieRepository.save(movieThree);
        List<Movie> movies = iMovieRepository.findAll();
        Assertions.assertThat(movies).size().isGreaterThan(1);
    }

    @Test
    public void findById(){
        Movie movie = EntitiesUtil.MOVIE_ONE;
        Movie savedMovie = iMovieRepository.save(movie);
        Optional<Movie> optionalMovie = iMovieRepository.findById(savedMovie.getId());
        assertTrue(optionalMovie.isPresent());
    }

    @Test
    public void findAllByTitleTest(){
        Movie movie = EntitiesUtil.MOVIE_ONE;
        Movie savedMovie = iMovieRepository.save(movie);
        List<Movie> movies = iMovieRepository.findAllByTitle(savedMovie.getTitle());
        assertEquals(movies.get(0).getTitle(), movie.getTitle());
    }

    /*
    @Test
    public void findAllByGender(){
        Gender gender = EntitiesUtil.GENDER_ONE;
        Movie movie = EntitiesUtil.MOVIE_ONE;
            movie.setGender(gender);
            //movie.addGender(gender);
        Movie savedMovie = iMovieRepository.save(movie);
        List<Movie> movies = iMovieRepository.findAllByGender(gender);
        assertEquals(movies.get(0).getTitle(), movie.getTitle());
    }
    */

    @Test
    public void deteleTest(){
        Movie movie = EntitiesUtil.MOVIE_TWO;
        Movie savedMovie = iMovieRepository.save(movie);
        Optional<Movie> optionalMovie = iMovieRepository.findById(savedMovie.getId());
        assertTrue(optionalMovie.isPresent());
        iMovieRepository.delete(optionalMovie.get());
        Optional<Movie> finalMovie = iMovieRepository.findById(movie.getId());
        assertFalse(finalMovie.isPresent());
    }

}
