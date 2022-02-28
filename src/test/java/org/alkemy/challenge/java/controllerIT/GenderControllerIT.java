package org.alkemy.challenge.java.controllerIT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.entities.Movie;
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
public class GenderControllerIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IGenderRepository iGenderRepository;

    @Autowired
    private IMovieRepository iMovieRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void getGendersIT() throws Exception {
        // Given
        Gender genderOne = EntitiesUtil.GENDER_ONE;
        Gender genderTwo = EntitiesUtil.GENDER_TWO;
        Gender genderThree = EntitiesUtil.GENDER_THREE;

        iGenderRepository.save(genderOne);
        iGenderRepository.save(genderTwo);
        iGenderRepository.save(genderThree);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/genders").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("nameOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("nameTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("nameThree"));
    }

    @Test
    public void getGenderByIdTest() throws Exception {
        // Given
        Gender genderOne = EntitiesUtil.GENDER_ONE;
        Gender genderTwo = EntitiesUtil.GENDER_TWO;
        Gender genderThree = EntitiesUtil.GENDER_THREE;

        iGenderRepository.save(genderOne);
        iGenderRepository.save(genderTwo);
        iGenderRepository.save(genderThree);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/genders/2").contentType(MediaType.APPLICATION_JSON))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("nameTwo"));
    }

    @Test
    @WithMockUser
    public void createGenderIT() throws JsonProcessingException, Exception {
        // Given
        GenderDTO genderDTO = DTOsUtil.GENDER_DTO_ONE;

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/genders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(genderDTO)))
                
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
        Gender gender = EntitiesUtil.GENDER_ONE;

        Movie movie = EntitiesUtil.MOVIE_ONE;

        iGenderRepository.save(gender);
        
        iMovieRepository.save(movie);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/genders/1/movie/1")
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
        Gender gender = EntitiesUtil.GENDER_ONE;

        iGenderRepository.save(gender);

        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_ONE;

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/genders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDTO)))
                // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieDTOs.[0].title").value("titleOne"));
    }

    @Test
    @WithMockUser
    public void updateGenderIT() throws JsonProcessingException, Exception{
        // Given
        Gender gender = EntitiesUtil.GENDER_ONE;

        iGenderRepository.save(gender);

        GenderDTO genderDTO = DTOsUtil.GENDER_DTO_ONE;
            genderDTO.setImage("imageTest");

        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/genders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(genderDTO)))
    
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("imageTest"));
    }

    @Test
    @WithMockUser
    public void deleteGenderIT() throws JsonProcessingException, Exception{
        // Given
        Gender gender = EntitiesUtil.GENDER_ONE;

        iGenderRepository.save(gender);

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/genders/1")
                .contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
