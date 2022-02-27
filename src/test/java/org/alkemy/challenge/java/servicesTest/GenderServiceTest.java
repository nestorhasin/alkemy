package org.alkemy.challenge.java.servicesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.response.GenderDetailsResponse;
import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.repositories.IGenderRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.services.interfaces.IGenderService;
import org.alkemy.challenge.java.utils.DTOsUtil;
import org.alkemy.challenge.java.utils.EntitiesUtil;
import org.alkemy.challenge.java.utils.ResponseUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GenderServiceTest {

    //@Mock
    @MockBean
    IGenderRepository iGenderRepository;

    //@Mock
    @MockBean
    IMovieRepository iMovieRepository;

    //@Spy
    //@Mock
    @MockBean
    ModelMapper modelMapper;

    //@InjectMocks
    //GenderServiceImpl genderServiceImpl;

    @Autowired
    IGenderService iGenderService;

    @Test
    public void readTest() {
        // Given
        Gender genderOne = EntitiesUtil.GENDER_ONE;
        Gender genderTwo = EntitiesUtil.GENDER_TWO;

        List<Gender> genders = Arrays.asList(genderOne, genderTwo);
        
        when(iGenderRepository.findAll()).thenReturn(genders);
        
        // When
        List<GenderDTO> genderDTOs = iGenderService.read();
        
        // Then
        assertFalse(genders.isEmpty());
        assertEquals(2, genders.size());
        assertTrue(genderDTOs.contains(modelMapper.map(genderOne, GenderDTO.class)));
        
        verify(iGenderRepository).findAll();
    }

    @Test
    public void createTest(){
        // Given
        GenderDTO genderDTO = DTOsUtil.GENDER_DTO_ONE;
        
        Gender gender = EntitiesUtil.GENDER_ONE;
        
        when(iGenderRepository.save(any())).thenReturn(gender);
        when(modelMapper.map(gender, GenderDTO.class)).thenReturn(genderDTO);
        when(modelMapper.map(genderDTO, Gender.class)).thenReturn(gender);
        
        // When
        GenderDTO createdGender = iGenderService.create(genderDTO);
        
        // Then
        assertEquals(gender.getImage(), createdGender.getImage());
        assertEquals(gender.getName(), createdGender.getName());
        
        verify(iGenderRepository).save(gender);
    }

    @Test
    public void readByIdTest(){
        // Given
        GenderDTO genderDTO = DTOsUtil.GENDER_DTO_ONE;
            genderDTO.setMovieDTOs(Collections.EMPTY_LIST);
        
        Gender gender = EntitiesUtil.GENDER_ONE;
            gender.setMovies(Collections.EMPTY_LIST);
        
        Optional<Gender> optionalGender = Optional.of(gender);

        GenderDetailsResponse genderDetailsResponse = ResponseUtil.GENDER_DETAILS_RESPONSE_ONE;
            genderDetailsResponse.setMovieDTOs(Collections.EMPTY_LIST);

        when(iGenderRepository.findById(gender.getId())).thenReturn(optionalGender);
        when(modelMapper.map(gender, GenderDetailsResponse.class)).thenReturn(genderDetailsResponse);
        when(modelMapper.map(genderDTO, Gender.class)).thenReturn(gender);
        
        // When
        GenderDetailsResponse finalGenderDetailsResponse = iGenderService.readById(gender.getId());
        
        // Then
        assertEquals(gender.getImage(), finalGenderDetailsResponse.getImage());
        assertEquals(gender.getName(), finalGenderDetailsResponse.getName());
        
        verify(iGenderRepository).findById(gender.getId());
    }

    @Test
    public void updateTest(){
        // Given
        GenderDTO genderDTO = DTOsUtil.GENDER_DTO_ONE;
            genderDTO.setMovieDTOs(Collections.EMPTY_LIST);

        Gender gender = EntitiesUtil.GENDER_ONE;
            gender.setMovies(Collections.EMPTY_LIST);

        when(iGenderRepository.findById(genderDTO.getId())).thenReturn(Optional.of(gender));
        when(iGenderRepository.save(any())).thenReturn(gender);
        when(modelMapper.map(gender, GenderDTO.class)).thenReturn(genderDTO);
        when(modelMapper.map(genderDTO, Gender.class)).thenReturn(gender);

        // When
        GenderDTO updatedGender = iGenderService.update(genderDTO);
        
        // Then
        assertEquals(genderDTO.getImage(), updatedGender.getImage());
        assertEquals(genderDTO.getName(), updatedGender.getName());

        verify(iGenderRepository).save(gender);
    }

    @Test
    public void deleteTest(){
        // Given
        Gender gender = EntitiesUtil.GENDER_ONE;
            gender.setMovies(Collections.EMPTY_LIST);
        
        when(iGenderRepository.findById(gender.getId())).thenReturn(Optional.of(gender));

        // When
        iGenderService.delete(gender.getId());
        
        // Then
        verify(iGenderRepository).delete(gender);
    }

}
