package org.alkemy.challenge.java.servicesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.repositories.IGenderRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.services.interfaces.IGenderService;
import org.alkemy.challenge.java.utils.EntitiesUtil;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class GenderServiceTest {

    // @Mock
    @MockBean
    IGenderRepository iGenderRepository;

    // @Mock
    @MockBean
    IMovieRepository iMovieRepository;

    @MockBean
    ModelMapper modelMapper;

    // @InjectMocks
    // GenderServiceImpl genderServiceImpl;

    @Autowired
    IGenderService iGenderService;

    @Test
    public void readTest() {
        // Given
        List<Gender> genders = Arrays.asList(EntitiesUtil.GENDER_ONE, EntitiesUtil.GENDER_TWO);
        when(iGenderRepository.findAll()).thenReturn(genders);
        // When
        List<GenderDTO> genderDTOs = iGenderService.read();
        // Then
        assertFalse(genders.isEmpty());
        assertEquals(2, genders.size());
        assertTrue(genderDTOs.contains(modelMapper.map(EntitiesUtil.GENDER_ONE, GenderDTO.class)));
        verify(iGenderRepository).findAll();
    }

    @Test
    public void createTest(){
        // Given
        GenderDTO genderTest = new GenderDTO(null, "nameTest", "imageTest", null);
        when(iGenderRepository.save(any())).then(invocation -> {
            Gender test = invocation.getArgument(0);
            test.setId(10L);
            return test;
        });
        // When
        GenderDTO genderDTO = iGenderService.create(genderTest);
        // Then
        assertEquals("imageTest", genderDTO.getImage());
        assertEquals("nameTest", genderDTO.getName());
        verify(iGenderRepository).save(any());
    }

}
