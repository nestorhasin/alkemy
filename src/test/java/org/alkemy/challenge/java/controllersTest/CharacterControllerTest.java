package org.alkemy.challenge.java.controllersTest;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.controllers.CharacterController;
import org.alkemy.challenge.java.services.interfaces.ICharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CharacterController.class)
public class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICharacterService iCharacterService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
    }

    /*
    @Test
    public void getCharactersTest() throws Exception{
        List<CharacterDTO> characterDTOs = Arrays.asList(CharacterUtil.create().orElseThrow(Exception::new));
        when(iCharacterService.read()).thenReturn(characterDTOs);
        mockMvc.perform(get("/characters").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].image").value("https://ar.linkedin.com/in/nestorhasin"))
               .andExpect(jsonPath("$[0].name").value("Nestor Hasin"))
               .andExpect(jsonPath("$[0].age").value("34"))
               .andExpect(jsonPath("$[0].weight").value("100.00"))
               .andExpect(jsonPath("$[0].history").value("Desde que comenzó la pandemia por el COVID-19 me puse como meta ser programador y cambiar el paradigma de mi vida. Actualmente soy un apasionado del código y estoy seguro que la crisis nos da la oportunidad de reafirmar los caminos elegidos o, como en mi caso, tomar nuevos rumbos... HELLO PEOPLE!"))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(content().json(objectMapper.writeValueAsString(characterDTOs)))
               .andExpect(status().isOk());
        verify(iCharacterService).read();
    }

    @Test
    public void getCharacterByIdTest() throws Exception{
        when(iCharacterService.readById(1L)).thenReturn(CharacterUtil.create().orElseThrow(() -> new ResourceNotFoundException("Character", "id", 1L)));
        mockMvc.perform(get("/characters/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.image").value("https://ar.linkedin.com/in/nestorhasin"))
               .andExpect(jsonPath("$.name").value("Nestor Hasin"))
               .andExpect(jsonPath("$.age").value("34"))
               .andExpect(jsonPath("$.weight").value("100.00"))
               .andExpect(jsonPath("$.history").value("Desde que comenzó la pandemia por el COVID-19 me puse como meta ser programador y cambiar el paradigma de mi vida. Actualmente soy un apasionado del código y estoy seguro que la crisis nos da la oportunidad de reafirmar los caminos elegidos o, como en mi caso, tomar nuevos rumbos... HELLO PEOPLE!"))
               .andExpect(status().isOk());
        verify(iCharacterService).readById(1L);
    }
    */

    @Test
    public void createCharacterTest() throws Exception{
        CharacterDTO characterDto = new CharacterDTO();
            characterDto.setId(1L);
            characterDto.setImage("https://ar.linkedin.com/in/nestorhasin");
            characterDto.setName("Nestor Hasin");
            characterDto.setAge(34);
            characterDto.setWeight(100.00);
            characterDto.setHistory("Desde que comenzó la pandemia por el COVID-19 me puse como meta ser programador y cambiar el paradigma de mi vida. Actualmente soy un apasionado del código y estoy seguro que la crisis nos da la oportunidad de reafirmar los caminos elegidos o, como en mi caso, tomar nuevos rumbos... HELLO PEOPLE!");
        System.out.println(objectMapper.writeValueAsString(characterDto));
        mockMvc.perform(post("/characters").contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(characterDto)))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.image").value("https://ar.linkedin.com/in/nestorhasin"))
               .andExpect(jsonPath("$.name").value("Nestor Hasin"))
               .andExpect(jsonPath("$.age").value("34"))
               .andExpect(jsonPath("$.weight").value("100.00"))
               .andExpect(jsonPath("$.history").value("Desde que comenzó la pandemia por el COVID-19 me puse como meta ser programador y cambiar el paradigma de mi vida. Actualmente soy un apasionado del código y estoy seguro que la crisis nos da la oportunidad de reafirmar los caminos elegidos o, como en mi caso, tomar nuevos rumbos... HELLO PEOPLE!"))
               .andExpect(content().json(objectMapper.writeValueAsString(characterDto)))
               .andExpect(status().isCreated());
        verify(iCharacterService).create(characterDto);
    }

}
