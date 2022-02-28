package org.alkemy.challenge.java.controllersTest;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.DTOs.response.CharacterDetailsResponse;
import org.alkemy.challenge.java.annotations.ControllerTest;
import org.alkemy.challenge.java.services.interfaces.ICharacterService;
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

import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

//@WebMvcTest(CharacterController.class)
//@ExtendWith(SpringExtension.class)
@ControllerTest
public class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICharacterService iCharacterService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void getCharactersTest() throws Exception {
        // Given
        when(iCharacterService.read()).thenReturn(Arrays.asList(ResponseUtil.CHARACTER_RESPONSE_ONE,
                ResponseUtil.CHARACTER_RESPONSE_TWO, ResponseUtil.CHARACTER_RESPONSE_THREE));
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/characters").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("nameOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("nameTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("nameThree"));
        verify(iCharacterService).read();
    }

    @Test
    public void getCharactersByNameTest() throws Exception {
        // Given
        when(iCharacterService.readAllByName("nameTwo"))
                .thenReturn(Collections.singletonList(DTOsUtil.CHARACTER_DTO_TWO));
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/characters?name=nameTwo").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("nameTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageTwo"));
        verify(iCharacterService).readAllByName("nameTwo");
    }

    @Test
    public void getCharactersByAgeTest() throws Exception {
        // Given
        when(iCharacterService.readAllByAge(3)).thenReturn(Collections.singletonList(DTOsUtil.CHARACTER_DTO_THREE));
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/characters?age=3").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("nameThree"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageThree"));
        verify(iCharacterService).readAllByAge(3);
    }

    @Test
    public void getCharactersByMovieTest() throws Exception {
        // Given
        when(iCharacterService.readAllByMovie(1L)).thenReturn(Collections.singletonList(DTOsUtil.CHARACTER_DTO_THREE));
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/characters?movie=1").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("nameThree"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageThree"));
        verify(iCharacterService).readAllByMovie(1L);
    }

    @Test
    public void getCharactersByWeightTest() throws Exception {
        // Given
        when(iCharacterService.readAllByWeight(1.0)).thenReturn(Collections.singletonList(DTOsUtil.CHARACTER_DTO_ONE));
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/characters?weight=1.0").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("nameOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("imageOne"));
        verify(iCharacterService).readAllByWeight(1.0);
    }

    @Test
    public void getCharacterByIdTest() throws Exception {
        // Given
        when(iCharacterService.readById(1L)).thenReturn(ResponseUtil.CHARACTER_DETAILS_RESPONSE_ONE);
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/characters/1").contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("nameOne"));
        verify(iCharacterService).readById(1L);
    }

    @Test
    @WithMockUser
    public void createCharacterTest() throws JsonProcessingException, Exception {
        // Given
        CharacterDTO characterDTO = DTOsUtil.CHARACTER_DTO_ONE;
                characterDTO.setMovieDTOs(Collections.EMPTY_LIST);
        when(iCharacterService.create(any())).thenReturn(characterDTO);
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/characters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(characterDTO)))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("imageOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.history").value("historyOne"));
        verify(iCharacterService).create(characterDTO);
    }

    @Test
    @WithMockUser
    public void linkWithMovieTest() throws JsonProcessingException, Exception{
        // Given
        CharacterDetailsResponse characterDetailsResponse = ResponseUtil.CHARACTER_DETAILS_RESPONSE_ONE;
                characterDetailsResponse.setMovieDTOs(Arrays.asList(DTOsUtil.MOVIE_DTO_ONE));
        when(iCharacterService.linkWithMovie(anyLong(), anyLong())).thenReturn(characterDetailsResponse);
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/characters/1/movie/1")
                .contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieDTOs.[0].title").value("titleOne"));
        verify(iCharacterService).linkWithMovie(anyLong(), anyLong());
    }

    // Content type not set --> BUSCAR EL XQ
    @Test
    @WithMockUser
    public void addMovieTest() throws JsonProcessingException, Exception{
        // Given
        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_ONE;
                movieDTO.setCharacterDTOs(Collections.EMPTY_LIST);
        CharacterDetailsResponse characterDetailsResponse = ResponseUtil.CHARACTER_DETAILS_RESPONSE_ONE;
                characterDetailsResponse.setMovieDTOs(Arrays.asList(movieDTO));
        when(iCharacterService.addMovie(anyLong(), eq(movieDTO))).thenReturn(characterDetailsResponse);
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/characters/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDTO)))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieDTOs.[0].title").value("titleOne"));
        verify(iCharacterService).addMovie(1L, movieDTO);
    }

    // Content type not set --> BUSCAR EL XQ
    @Test
    @WithMockUser
    public void updateCharacterTest() throws JsonProcessingException, Exception{
        // Given
        CharacterDTO characterDTO = DTOsUtil.CHARACTER_DTO_ONE;
                characterDTO.setMovieDTOs(Collections.EMPTY_LIST);
        when(iCharacterService.update(characterDTO)).thenReturn(characterDTO);
        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/characters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(characterDTO)))
                
        // Then
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("imageOne"));
        verify(iCharacterService).update(characterDTO);
    }

}
