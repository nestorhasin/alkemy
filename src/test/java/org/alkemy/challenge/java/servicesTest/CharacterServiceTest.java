package org.alkemy.challenge.java.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.response.CharacterResponse;
import org.alkemy.challenge.java.entities.Character;
import org.alkemy.challenge.java.repositories.ICharacterRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.services.interfaces.ICharacterService;
import org.alkemy.challenge.java.utils.EntitiesUtil;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CharacterServiceTest {
    
    // @Mock
    @MockBean
    ICharacterRepository iCharacterRepository;

    // @Mock
    @MockBean
    IMovieRepository iMovieRepository;

    @MockBean
    ModelMapper modelMapper;

    // @InjectMocks
    // GenderServiceImpl genderServiceImpl;

    @Autowired
    ICharacterService iCharacterService;

    @Test
    public void readTest() {
        // Given
        List<Character> characters = Arrays.asList(EntitiesUtil.CHARACTER_ONE, EntitiesUtil.CHARACTER_TWO);
        when(iCharacterRepository.findAll()).thenReturn(characters);
        // When
        List<CharacterResponse> characterResponses = iCharacterService.read();
        // Then
        assertFalse(characters.isEmpty());
        assertEquals(2, characters.size());
        assertTrue(characterResponses.contains(modelMapper.map(EntitiesUtil.CHARACTER_ONE, CharacterResponse.class)));
        verify(iCharacterRepository).findAll();
    }

    // CHEQUEAR
    @Test
    public void createTest(){
        // Given
        CharacterDTO characterTest = new CharacterDTO(null, "imageTest", "nameTest", 10, 10.00, "historyTest", null);
        when(iCharacterRepository.save(any())).then(invocation -> {
            Character test = invocation.getArgument(0);
            test.setId(10L);
            return test;
        });
        // When
        CharacterDTO characterDTO = iCharacterService.create(characterTest);
        // Then
        assertEquals("imageTest", characterDTO.getImage());
        assertEquals("nameTest", characterDTO.getName());
        verify(iCharacterRepository).save(any());
    }

}