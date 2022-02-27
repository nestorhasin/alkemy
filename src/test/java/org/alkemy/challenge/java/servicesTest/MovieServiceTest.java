package org.alkemy.challenge.java.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.DTOs.response.MovieDetailsResponse;
import org.alkemy.challenge.java.DTOs.response.MovieResponse;
import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.repositories.ICharacterRepository;
import org.alkemy.challenge.java.repositories.IGenderRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.services.interfaces.IMovieService;
import org.alkemy.challenge.java.utils.DTOsUtil;
import org.alkemy.challenge.java.utils.EntitiesUtil;
import org.alkemy.challenge.java.utils.ResponseUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MovieServiceTest {
    
    //@Mock
    @MockBean
    IGenderRepository iGenderRepository;

    //Mock
    @MockBean
    ICharacterRepository iCharacterRepository;

    //@Mock
    @MockBean
    IMovieRepository iMovieRepository;

    //@Spy
    //@Mock
    @MockBean
    ModelMapper modelMapper;

    @Autowired
    IMovieService iMovieService;

    @Test
    public void readTest() {
        // Given
        Movie movieOne = EntitiesUtil.MOVIE_ONE;
        Movie movieTwo = EntitiesUtil.MOVIE_TWO;

        List<Movie> movies = Arrays.asList(movieOne, movieTwo);
        
        when(iMovieRepository.findAll()).thenReturn(movies);
        
        // When
        List<MovieResponse> movieResponses = iMovieService.read();
        
        // Then
        assertFalse(movies.isEmpty());
        assertEquals(2, movies.size());
        assertTrue(movieResponses.contains(modelMapper.map(EntitiesUtil.MOVIE_ONE, MovieResponse.class)));
        
        verify(iMovieRepository).findAll();
    }

    @Test
    public void createTest(){
        // Given
        Movie movie = EntitiesUtil.MOVIE_ONE;
            movie.setCharacters(Collections.EMPTY_LIST);

        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_ONE;
            movieDTO.setCharacterDTOs(Collections.EMPTY_LIST);

        when(iMovieRepository.save(any())).thenReturn(movie);    
        when(modelMapper.map(movie, MovieDTO.class)).thenReturn(movieDTO);
        when(modelMapper.map(movieDTO, Movie.class)).thenReturn(movie);
        
        // When
        MovieDTO createdMovie = iMovieService.create(movieDTO);
        
        // Then
        assertEquals(movie.getImage(), createdMovie.getImage());
        assertEquals(movie.getTitle(), createdMovie.getTitle());
        
        verify(iMovieRepository).save(movie);
    }

    @Test
    public void readByIdTest(){
        // Given
        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_THREE;
            movieDTO.setCharacterDTOs(Collections.EMPTY_LIST);
        
        Movie movie = EntitiesUtil.MOVIE_THREE;
            movie.setCharacters(Collections.EMPTY_LIST);
        
        Optional<Movie> optionalMovie = Optional.of(movie);
        
        MovieDetailsResponse movieDetailsResponse = ResponseUtil.MOVIE_DETAILS_RESPONSE_THREE;
            movieDetailsResponse.setCharacterDTOs(Collections.EMPTY_LIST);
        
        when(iMovieRepository.findById(movie.getId())).thenReturn(optionalMovie);
        when(modelMapper.map(movieDTO, Movie.class)).thenReturn(movie);
        when(modelMapper.map(movie, MovieDetailsResponse.class)).thenReturn(movieDetailsResponse);

        // When
        MovieDetailsResponse finalMovieDetailsResponse = iMovieService.readById(movie.getId());
    
        // Then
        assertEquals(movie.getImage(), finalMovieDetailsResponse.getImage());
        assertEquals(movie.getTitle(), finalMovieDetailsResponse.getTitle());
        
        verify(iMovieRepository).findById(movie.getId());
    }

    @Test
    public void readByOrderTest(){
        // Given
        String order = "ASC";
        String sortBy = "creationDate";
        
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();        
        
        Movie movieOne = EntitiesUtil.MOVIE_ONE;
        Movie movieTwo = EntitiesUtil.MOVIE_TWO;
        
        MovieDTO movieDTOOne = DTOsUtil.MOVIE_DTO_ONE;
        MovieDTO movieDTOTwo = DTOsUtil.MOVIE_DTO_TWO;

        List<Movie> movies = Arrays.asList(movieOne, movieTwo);
        
        when(iMovieRepository.findAll(sort)).thenReturn(movies);
        when(modelMapper.map(movieOne, MovieDTO.class)).thenReturn(movieDTOOne);
        when(modelMapper.map(movieTwo, MovieDTO.class)).thenReturn(movieDTOTwo);
        
        // When
        List<MovieDTO> movieDTOs = iMovieService.readByOrder(order);
        
        // Then
        assertFalse(movies.isEmpty());
        assertEquals(2, movies.size());
        assertEquals(movieDTOs.get(0).getTitle(), movies.get(0).getTitle());

        verify(iMovieRepository).findAll(sort);
    }

    @Test
    public void readAllByTitle(){
        // Given
        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_THREE;
            movieDTO.setCharacterDTOs(Collections.EMPTY_LIST);
        
        Movie movie = EntitiesUtil.MOVIE_THREE;
            movie.setCharacters(Collections.EMPTY_LIST);
        
        when(iMovieRepository.findAllByTitle(movie.getTitle())).thenReturn(Collections.singletonList(movie));
        when(modelMapper.map(movie, MovieDTO.class)).thenReturn(movieDTO);
        when(modelMapper.map(movieDTO, Movie.class)).thenReturn(movie);

        // When
        List<MovieDTO> movieDTOs = iMovieService.readAllByTitle(movie.getTitle());
    
        // Then
        assertEquals(movie.getTitle(), movieDTOs.get(0).getTitle());
        assertEquals(movie.getQualification(), movieDTOs.get(0).getQualification());
        
        verify(iMovieRepository).findAllByTitle(movie.getTitle());
    }

    @Test
    public void readAllByGenderTest(){
        // Given
        Gender gender = EntitiesUtil.GENDER_ONE;
            gender.setMovies(Collections.EMPTY_LIST);

        Optional<Gender> optionalGender = Optional.of(gender);

        Movie movie = EntitiesUtil.MOVIE_ONE;
            movie.setCharacters(Collections.EMPTY_LIST);

        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_ONE;
            movieDTO.setCharacterDTOs(Collections.EMPTY_LIST);

        when(iGenderRepository.findById(gender.getId())).thenReturn(optionalGender);
        when(iMovieRepository.findAllByGender(gender)).thenReturn(Collections.singletonList(movie));
        when(modelMapper.map(movie, MovieDTO.class)).thenReturn(movieDTO);

        // When
        List<MovieDTO> finalMovie = iMovieService.readAllByGender(gender.getId());

        // Then
        assertEquals(movie.getTitle(), finalMovie.get(0).getTitle());
        assertEquals(movie.getQualification(), finalMovie.get(0).getQualification());
        assertTrue(finalMovie.size() == 1);

        verify(iMovieRepository).findAllByGender(gender);
    }

    @Test
    public void updateTest(){
        // Given
        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_ONE;
            movieDTO.setCharacterDTOs(Collections.EMPTY_LIST);
        
        Movie movie = EntitiesUtil.MOVIE_ONE;
            movie.setCharacters(Collections.EMPTY_LIST);
        
        when(iMovieRepository.findById(movieDTO.getId())).thenReturn(Optional.of(movie));
        when(iMovieRepository.save(any())).thenReturn(movie);
        when(modelMapper.map(movie, MovieDTO.class)).thenReturn(movieDTO);
        when(modelMapper.map(movieDTO, Movie.class)).thenReturn(movie);
        
        // When
        MovieDTO updatedMovieDTO = iMovieService.update(movieDTO);
        
        // Then
        assertEquals(movie.getImage(), updatedMovieDTO.getImage());
        assertEquals(movie.getTitle(), updatedMovieDTO.getTitle());
    
        verify(iMovieRepository).save(movie);
    }

    @Test
    public void deleteTest(){
        // Given
        Movie movie = EntitiesUtil.MOVIE_ONE;
            movie.setCharacters(Collections.EMPTY_LIST);
        
        when(iMovieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));

        // When
        iMovieService.delete(movie.getId());
        
        // Then
        verify(iMovieRepository).delete(movie);
    }

}
