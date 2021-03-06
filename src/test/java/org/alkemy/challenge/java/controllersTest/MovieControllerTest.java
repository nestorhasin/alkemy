package org.alkemy.challenge.java.controllersTest;

import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.DTOs.response.MovieDetailsResponse;
import org.alkemy.challenge.java.annotations.ControllerTest;
import org.alkemy.challenge.java.services.interfaces.IMovieService;
import org.alkemy.challenge.java.utils.DTOsUtil;
import org.alkemy.challenge.java.utils.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMovieService iMovieService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void getMoviesTest() throws Exception {
        // Given
        when(iMovieService.read()).thenReturn(Arrays.asList(ResponseUtil.MOVIE_RESPONSE_ONE,
                ResponseUtil.MOVIE_RESPONSE_TWO, ResponseUtil.MOVIE_RESPONSE_THREE));
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/movies").contentType(MediaType.APPLICATION_JSON))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].image").value("imageTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].image").value("imageThree"));
        verify(iMovieService).read();
    }

    @Test
    public void getMoviesByNameTest() throws Exception {
        // Given
        when(iMovieService.readAllByTitle("nameTwo")).thenReturn(Collections.singletonList(DTOsUtil.MOVIE_DTO_TWO));
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/movies?name=nameTwo").contentType(MediaType.APPLICATION_JSON))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("titleTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageTwo"));
        verify(iMovieService).readAllByTitle("nameTwo");
    }

    @Test
    public void getMoviesByGenderTest() throws Exception {
        // Given
        when(iMovieService.readAllByGender(1L)).thenReturn(Collections.singletonList(DTOsUtil.MOVIE_DTO_THREE));
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/movies?gender=1").contentType(MediaType.APPLICATION_JSON))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("titleThree"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageThree"));
        verify(iMovieService).readAllByGender(1L);
    }

    @Test
    public void getMoviesByOrderTest() throws Exception {
        // Given
        when(iMovieService.readByOrder("DESC"))
                .thenReturn(Arrays.asList(DTOsUtil.MOVIE_DTO_THREE, DTOsUtil.MOVIE_DTO_TWO, DTOsUtil.MOVIE_DTO_ONE));
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/movies?order=DESC").contentType(MediaType.APPLICATION_JSON))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("titleThree"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].image").value("imageOne"));
        verify(iMovieService).readByOrder("DESC");
    }

    @Test
    public void getMovieByIdTest() throws Exception {
        // Given
        when(iMovieService.readById(1L)).thenReturn(ResponseUtil.MOVIE_DETAILS_RESPONSE_ONE);
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/movies/1").contentType(MediaType.APPLICATION_JSON))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("titleOne"));
        verify(iMovieService).readById(1L);
    }

    @Test
    @WithMockUser
    public void createMovieTest() throws JsonProcessingException, Exception {
        // Given
        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_ONE;
                movieDTO.setCharacterDTOs(Collections.EMPTY_LIST);
        when(iMovieService.create(any())).thenReturn(movieDTO);
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDTO)))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("imageOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("titleOne"));
        verify(iMovieService).create(movieDTO);
    }

    @Test
    @WithMockUser
    public void linkWithCharacterTest() throws JsonProcessingException, Exception{
        // Given
        MovieDetailsResponse movieDetailsResponse = ResponseUtil.MOVIE_DETAILS_RESPONSE_ONE;
                movieDetailsResponse.setCharacterDTOs(Arrays.asList(DTOsUtil.CHARACTER_DTO_ONE));
        when(iMovieService.linkWithCharacter(anyLong(), anyLong())).thenReturn(movieDetailsResponse);
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/1/character/1")
                .contentType(MediaType.APPLICATION_JSON))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.characterDTOs.[0].name").value("nameOne"));
        verify(iMovieService).linkWithCharacter(anyLong(), anyLong());
    }

    @Test
    @WithMockUser
    public void addCharacterTest() throws JsonProcessingException, Exception{
        // Given
        CharacterDTO characterDTO = DTOsUtil.CHARACTER_DTO_ONE;
                characterDTO.setMovieDTOs(Collections.EMPTY_LIST);
        MovieDetailsResponse movieDetailsResponse = ResponseUtil.MOVIE_DETAILS_RESPONSE_ONE;
                movieDetailsResponse.setCharacterDTOs(Arrays.asList(characterDTO));
        when(iMovieService.addCharacter(anyLong(), eq(characterDTO))).thenReturn(movieDetailsResponse);
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/1/addCharacter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(characterDTO)))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.characterDTOs.[0].name").value("nameOne"));
        verify(iMovieService).addCharacter(1L, characterDTO);
    }

    @Test
    @WithMockUser
    public void linkWithGenderTest() throws JsonProcessingException, Exception{
        // Given
        MovieDetailsResponse movieDetailsResponse = ResponseUtil.MOVIE_DETAILS_RESPONSE_ONE;
                movieDetailsResponse.setGenderDTO(DTOsUtil.GENDER_DTO_ONE);
        when(iMovieService.linkWithGender(anyLong(), anyLong())).thenReturn(movieDetailsResponse);
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/1/gender/1")
                .contentType(MediaType.APPLICATION_JSON))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genderDTO.name").value("nameOne"));
        verify(iMovieService).linkWithGender(anyLong(), anyLong());
    }

    @Test
    @WithMockUser
    public void addGenderTest() throws JsonProcessingException, Exception{
        // Given
        GenderDTO genderDTO = DTOsUtil.GENDER_DTO_ONE;
                genderDTO.setMovieDTOs(Collections.EMPTY_LIST);
        MovieDetailsResponse movieDetailsResponse = ResponseUtil.MOVIE_DETAILS_RESPONSE_ONE;
                movieDetailsResponse.setGenderDTO(genderDTO);
        when(iMovieService.addGender(anyLong(), eq(genderDTO))).thenReturn(movieDetailsResponse);
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/1/addGender")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(genderDTO)))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genderDTO.name").value("nameOne"));
        verify(iMovieService).addGender(1L, genderDTO);
    }

    @Test
    @WithMockUser
    public void updateMovieTest() throws JsonProcessingException, Exception{
        // Given
        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_ONE;
                movieDTO.setCharacterDTOs(Collections.EMPTY_LIST);
        when(iMovieService.update(movieDTO)).thenReturn(movieDTO);
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(movieDTO)))
        
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("imageOne"));
        verify(iMovieService).update(movieDTO);
    }

}
