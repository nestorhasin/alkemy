package org.alkemy.challenge.java.controllersTest;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.response.CharacterResponse;
import org.alkemy.challenge.java.controllers.CharacterController;
import org.alkemy.challenge.java.securities.CustomUserDetailsService;
import org.alkemy.challenge.java.services.interfaces.ICharacterService;
import org.alkemy.challenge.java.utils.DTOsUtil;
import org.alkemy.challenge.java.utils.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

//@WebMvcTest(CharacterController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICharacterService iCharacterService;

    @Test
    public void getCharactersTest() throws Exception {
        // Given
        when(iCharacterService.read()).thenReturn(Arrays.asList(ResponseUtil.CHARACTER_RESPONSE_ONE, ResponseUtil.CHARACTER_RESPONSE_TWO, ResponseUtil.CHARACTER_RESPONSE_THREE));
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
        when(iCharacterService.readAllByName("nameTwo")).thenReturn(Collections.singletonList(DTOsUtil.CHARACTER_DTO_TWO));
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
    public void getCharacterByIdTest() throws Exception{
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

}
