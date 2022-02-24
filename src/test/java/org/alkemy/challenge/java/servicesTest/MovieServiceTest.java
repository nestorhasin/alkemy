package org.alkemy.challenge.java.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.DTOs.response.MovieResponse;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.repositories.ICharacterRepository;
import org.alkemy.challenge.java.repositories.IGenderRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.services.interfaces.IMovieService;
import org.alkemy.challenge.java.utils.EntitiesUtil;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class MovieServiceTest {
    
    // @Mock
    @MockBean
    IGenderRepository iGenderRepository;

    @MockBean
    ICharacterRepository iCharacterRepository;

    // @Mock
    @MockBean
    IMovieRepository iMovieRepository;

    @MockBean
    ModelMapper modelMapper;

    @Autowired
    IMovieService iMovieService;

    @Test
    public void readTest() {
        // Given
        List<Movie> movies = Arrays.asList(EntitiesUtil.MOVIE_ONE, EntitiesUtil.MOVIE_TWO);
        when(iMovieRepository.findAll()).thenReturn(movies);
        // When
        List<MovieResponse> movieResponses = iMovieService.read();
        // Then
        assertFalse(movies.isEmpty());
        assertEquals(2, movies.size());
        assertTrue(movieResponses.contains(modelMapper.map(EntitiesUtil.MOVIE_ONE, MovieResponse.class)));
        verify(iGenderRepository).findAll();
    }

    @Test
    public void createTest(){
        // Given
        MovieDTO movieTest = new MovieDTO(null, "imageTest", "titleTest", LocalDate.now(), 10, null, null);
        when(iMovieRepository.save(any())).then(invocation -> {
            Movie test = invocation.getArgument(0);
            test.setId(10L);
            return test;
        });
        // When
        MovieDTO movieDTO = iMovieService.create(movieTest);
        // Then
        assertEquals("imageTest", movieDTO.getImage());
        assertEquals("nameTest", movieDTO.getTitle());
        verify(iMovieRepository).save(any());
    }

}
