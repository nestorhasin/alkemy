package org.alkemy.challenge.java.controllersTest;

import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.DTOs.response.GenderDetailsResponse;
import org.alkemy.challenge.java.annotations.ControllerTest;
import org.alkemy.challenge.java.services.interfaces.IGenderService;
import org.alkemy.challenge.java.utils.DTOsUtil;
import org.alkemy.challenge.java.utils.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

//@WebMvcTest(CharacterController.class)
//@ExtendWith(SpringExtension.class)
@ControllerTest
public class GenderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IGenderService iGenderService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void getGendersTest() throws Exception {
        // Given
        when(iGenderService.read())
                .thenReturn(Arrays.asList(DTOsUtil.GENDER_DTO_ONE, DTOsUtil.GENDER_DTO_TWO, DTOsUtil.GENDER_DTO_THREE));
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/genders").contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("nameOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("nameTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("nameThree"));
        verify(iGenderService).read();
    }

    @Test
    public void getGenderByIdTest() throws Exception {
        // Given
        when(iGenderService.readById(1L)).thenReturn(ResponseUtil.GENDER_DETAILS_RESPONSE_ONE);
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/genders/1").contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("nameOne"));
        verify(iGenderService).readById(1L);
    }

    @Test
    @WithMockUser
    public void createGenderTest() throws JsonProcessingException, Exception {
        // Given
        GenderDTO genderDTO = DTOsUtil.GENDER_DTO_ONE;
                genderDTO.setMovieDTOs(Collections.EMPTY_LIST);
        when(iGenderService.create(any())).thenReturn(genderDTO);
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/genders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(genderDTO)))
                // Then
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("nameOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("imageOne"));
        verify(iGenderService).create(genderDTO);
    }

    @Test
    @WithMockUser
    public void linkWithMovieTest() throws JsonProcessingException, Exception{
        // Given
        GenderDetailsResponse genderDetailsResponse = ResponseUtil.GENDER_DETAILS_RESPONSE_ONE;
                genderDetailsResponse.setMovieDTOs(Arrays.asList(DTOsUtil.MOVIE_DTO_ONE));
        when(iGenderService.linkWithMovie(anyLong(), anyLong())).thenReturn(genderDetailsResponse);
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/genders/1/movie/1")
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieDTOs.[0].title").value("titleOne"));
        verify(iGenderService).linkWithMovie(anyLong(), anyLong());
    }

    @Test
    @WithMockUser
    public void addMovieTest() throws JsonProcessingException, Exception{
        // Given
        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_ONE;
                movieDTO.setCharacterDTOs(Collections.EMPTY_LIST);
        GenderDetailsResponse genderDetailsResponse = ResponseUtil.GENDER_DETAILS_RESPONSE_ONE;
                genderDetailsResponse.setMovieDTOs(Arrays.asList(movieDTO));
        when(iGenderService.addMovie(anyLong(), eq(movieDTO))).thenReturn(genderDetailsResponse);
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/genders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDTO)))
                // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieDTOs.[0].title").value("titleOne"));
        verify(iGenderService).addMovie(1L, movieDTO);
    }

    @Test
    @WithMockUser
    public void updateGenderTest() throws JsonProcessingException, Exception{
        // Given
        GenderDTO genderDTO = DTOsUtil.GENDER_DTO_ONE;
                genderDTO.setMovieDTOs(Collections.EMPTY_LIST);
        when(iGenderService.update(genderDTO)).thenReturn(genderDTO);
        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/genders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(genderDTO)))
                // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("imageOne"));
        verify(iGenderService).update(genderDTO);
    }

}
