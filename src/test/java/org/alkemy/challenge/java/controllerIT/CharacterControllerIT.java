package org.alkemy.challenge.java.controllerIT;

import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.entities.Character;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.repositories.ICharacterRepository;
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
public class CharacterControllerIT {
  
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ICharacterRepository iCharacterRepository;

    @Autowired
    private IMovieRepository iMovieRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void getCharactersIT() throws Exception {
        // Given
        Character characterOne = EntitiesUtil.CHARACTER_ONE;
        Character characterTwo = EntitiesUtil.CHARACTER_TWO;
        Character characterThree = EntitiesUtil.CHARACTER_THREE;

        iCharacterRepository.save(characterOne);
        iCharacterRepository.save(characterTwo);
        iCharacterRepository.save(characterThree);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/characters").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("nameOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("nameTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("nameThree"));
    }

    @Test
    public void getCharactersByNameIT() throws Exception {
        // Given
        Character characterOne = EntitiesUtil.CHARACTER_ONE;
        Character characterTwo = EntitiesUtil.CHARACTER_TWO;
        Character characterThree = EntitiesUtil.CHARACTER_THREE;

        iCharacterRepository.save(characterOne);
        iCharacterRepository.save(characterTwo);
        iCharacterRepository.save(characterThree);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/characters?name=nameTwo").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("nameTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageTwo"));
    }

    @Test
    public void getCharactersByAgeIT() throws Exception {
        // Given
        Character characterOne = EntitiesUtil.CHARACTER_ONE;
        Character characterTwo = EntitiesUtil.CHARACTER_TWO;
        Character characterThree = EntitiesUtil.CHARACTER_THREE;

        iCharacterRepository.save(characterOne);
        iCharacterRepository.save(characterTwo);
        iCharacterRepository.save(characterThree);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/characters?age=3").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("nameThree"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageThree"));
    }

    @Test
    public void getCharactersByMovieIT() throws Exception {
        // Given
        Movie movie = EntitiesUtil.MOVIE_ONE;

        Character characterOne = EntitiesUtil.CHARACTER_ONE;
        Character characterTwo = EntitiesUtil.CHARACTER_TWO;
        Character characterThree = EntitiesUtil.CHARACTER_THREE;
            characterThree.setMovies(Collections.singletonList(movie));
            
            movie.setCharacters(Collections.singletonList(characterThree));

        iCharacterRepository.save(characterOne);
        iCharacterRepository.save(characterTwo);
        iCharacterRepository.save(characterThree);

        iMovieRepository.save(movie);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/characters?movie=1").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("nameThree"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageThree"));
    }

    @Test
    public void getCharactersByWeightIT() throws Exception {
        // Given
        Character characterOne = EntitiesUtil.CHARACTER_ONE;
        Character characterTwo = EntitiesUtil.CHARACTER_TWO;
        Character characterThree = EntitiesUtil.CHARACTER_THREE;

        iCharacterRepository.save(characterOne);
        iCharacterRepository.save(characterTwo);
        iCharacterRepository.save(characterThree);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/characters?weight=2").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("nameTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageTwo"));
    }

    @Test
    public void getCharactersByIdIT() throws Exception {
        // Given
        Character characterOne = EntitiesUtil.CHARACTER_ONE;
        Character characterTwo = EntitiesUtil.CHARACTER_TWO;
        Character characterThree = EntitiesUtil.CHARACTER_THREE;

        iCharacterRepository.save(characterOne);
        iCharacterRepository.save(characterTwo);
        iCharacterRepository.save(characterThree);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/characters/2").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("nameTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("imageTwo"));
    }

    @Test
    @WithMockUser
    public void createCharacterIT() throws JsonProcessingException, Exception {
        // Given
        CharacterDTO characterDTO = DTOsUtil.CHARACTER_DTO_ONE;

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/characters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(characterDTO)))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("nameOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("imageOne"));
    }

    @Test
    @WithMockUser
    public void linkWithMovieIT() throws JsonProcessingException, Exception{
        // Given
        Character character = EntitiesUtil.CHARACTER_ONE;

        Movie movie = EntitiesUtil.MOVIE_ONE;

        iCharacterRepository.save(character);
        
        iMovieRepository.save(movie);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/characters/1/movie/1")
                .contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieDTOs.[0].title").value("titleOne"));
    }

    @Test
    @WithMockUser
    public void addMovieIT() throws JsonProcessingException, Exception{
        // Given
        Character character = EntitiesUtil.CHARACTER_ONE;

        iCharacterRepository.save(character);

        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_ONE;

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/characters/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDTO)))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieDTOs.[0].title").value("titleOne"));
    }

    @Test
    @WithMockUser
    public void updateCharacterIT() throws JsonProcessingException, Exception{
        // Given
        Character character = EntitiesUtil.CHARACTER_ONE;

        iCharacterRepository.save(character);

        CharacterDTO characterDTO = DTOsUtil.CHARACTER_DTO_ONE;
            characterDTO.setImage("imageTest");

        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/characters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(characterDTO)))
    
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("imageTest"));
    }

    @Test
    @WithMockUser
    public void deleteCharacterIT() throws JsonProcessingException, Exception{
        // Given
        Character character = EntitiesUtil.CHARACTER_ONE;

        iCharacterRepository.save(character);

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/characters/1")
                .contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
