package org.alkemy.challenge.java.controllersTest;

import java.util.Arrays;

import org.alkemy.challenge.java.services.interfaces.IGenderService;
import org.alkemy.challenge.java.utils.DTOsUtil;
import org.alkemy.challenge.java.utils.ResponseUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(CharacterController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GenderControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IGenderService iGenderService;

    @Test
    public void getGendersTest() throws Exception {
        // Given
        when(iGenderService.read()).thenReturn(Arrays.asList(DTOsUtil.GENDER_DTO_ONE, DTOsUtil.GENDER_DTO_TWO, DTOsUtil.GENDER_DTO_THREE));
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
    public void getGenderByIdTest() throws Exception{
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

}
