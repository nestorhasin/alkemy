package org.alkemy.challenge.java.controllerIT;

import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.entities.Character;
import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.repositories.ICharacterRepository;
import org.alkemy.challenge.java.repositories.IGenderRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.utils.DTOsUtil;
import org.alkemy.challenge.java.utils.EntitiesUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
//@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieControllerIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IMovieRepository iMovieRepository;

    @Autowired
    private ICharacterRepository iCharacterRepository;

    @Autowired
    private IGenderRepository iGenderRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void getMoviesIT() throws Exception{
        // Given
        Movie movieOne = EntitiesUtil.MOVIE_ONE;
        Movie movieTwo = EntitiesUtil.MOVIE_TWO;
        Movie movieThree = EntitiesUtil.MOVIE_THREE;

        iMovieRepository.save(movieOne);
        iMovieRepository.save(movieTwo);
        iMovieRepository.save(movieThree);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/movies").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("titleOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("titleTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value("titleThree"));
    }

    @Test
    public void getMoviesByNameIT() throws Exception{
        // Given
        Movie movieOne = EntitiesUtil.MOVIE_ONE;
        Movie movieTwo = EntitiesUtil.MOVIE_TWO;
        Movie movieThree = EntitiesUtil.MOVIE_THREE;

        iMovieRepository.save(movieOne);
        iMovieRepository.save(movieTwo);
        iMovieRepository.save(movieThree);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/movies?name=titleThree").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("titleThree"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageThree"));
    }

    @Test
    public void getMoviesByGenderIT() throws Exception {
        // Given
        Gender gender = EntitiesUtil.GENDER_ONE;

        Movie movieOne = EntitiesUtil.MOVIE_ONE;
        Movie movieTwo = EntitiesUtil.MOVIE_TWO;
        Movie movieThree = EntitiesUtil.MOVIE_THREE;
            movieThree.setGender(gender);

            gender.setMovies(Collections.singletonList(movieTwo));

        iMovieRepository.save(movieOne);
        iMovieRepository.save(movieTwo);
        iMovieRepository.save(movieThree);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/movies?gender=1").contentType(MediaType.APPLICATION_JSON))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("titleThree"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageThree"));
    }

    @Test
    public void getMoviesByOrderIT() throws Exception {
        // Given
        Movie movieOne = EntitiesUtil.MOVIE_ONE;
        Movie movieTwo = EntitiesUtil.MOVIE_TWO;
        Movie movieThree = EntitiesUtil.MOVIE_THREE;

        iMovieRepository.save(movieOne);
        iMovieRepository.save(movieTwo);
        iMovieRepository.save(movieThree);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/movies?order=DESC").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("titleThree"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].image").value("imageOne"));
    }

    @Test
    public void getMovieByIdIT() throws Exception {
        // Given
        Movie movieOne = EntitiesUtil.MOVIE_ONE;
        Movie movieTwo = EntitiesUtil.MOVIE_TWO;
        Movie movieThree = EntitiesUtil.MOVIE_THREE;

        iMovieRepository.save(movieOne);
        iMovieRepository.save(movieTwo);
        iMovieRepository.save(movieThree);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/movies/2").contentType(MediaType.APPLICATION_JSON))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("titleTwo"));
    }

    @Test
    @WithMockUser
    public void createMovieIT() throws JsonProcessingException, Exception {
        // Given
        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_ONE;
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDTO)))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("imageOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("titleOne"));
    }

    @Test
    @WithMockUser
    public void linkWithCharacterIT() throws JsonProcessingException, Exception{
        // Given
        Movie movie = EntitiesUtil.MOVIE_ONE;

        Character character = EntitiesUtil.CHARACTER_ONE;

        iMovieRepository.save(movie);

        iCharacterRepository.save(character);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/1/character/1")
                .contentType(MediaType.APPLICATION_JSON))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.characterDTOs.[0].name").value("nameOne"));
    }

    @Test
    @WithMockUser
    public void addCharacterIT() throws JsonProcessingException, Exception{
        // Given
        Movie movie = EntitiesUtil.MOVIE_ONE;

        iMovieRepository.save(movie);

        CharacterDTO characterDTO = DTOsUtil.CHARACTER_DTO_ONE;

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/1/addCharacter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(characterDTO)))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.characterDTOs.[0].name").value("nameOne"));
    }

    @Test
    @WithMockUser
    public void linkWithGenderIT() throws JsonProcessingException, Exception{
        // Given
        Movie movie = EntitiesUtil.MOVIE_ONE;

        Gender gender = EntitiesUtil.GENDER_ONE;

        iMovieRepository.save(movie);

        iGenderRepository.save(gender);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/1/gender/1")
                .contentType(MediaType.APPLICATION_JSON))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genderDTO.name").value("nameOne"));
    }

    @Test
    @WithMockUser
    public void addGenderIT() throws JsonProcessingException, Exception{
        // Given
        Movie movie = EntitiesUtil.MOVIE_ONE;

        iMovieRepository.save(movie);

        GenderDTO genderDTO = DTOsUtil.GENDER_DTO_ONE;

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/1/addGender")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(genderDTO)))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genderDTO.name").value("nameOne"));
    }

    @Test
    @WithMockUser
    public void updateMovieIT() throws JsonProcessingException, Exception{
        // Given
        Movie movie = EntitiesUtil.MOVIE_ONE;

        iMovieRepository.save(movie);

        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_ONE;
            movieDTO.setTitle("titleTest");

        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDTO)))
    
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("titleTest"));
    }

    @Test
    @WithMockUser
    public void deleteMovieIT() throws JsonProcessingException, Exception{
        // Given
        Movie movie = EntitiesUtil.MOVIE_ONE;

        iMovieRepository.save(movie);

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/movies/1")
                .contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
