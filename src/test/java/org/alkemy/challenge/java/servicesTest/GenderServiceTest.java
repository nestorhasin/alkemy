package org.alkemy.challenge.java.servicesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.alkemy.challenge.java.DTOs.response.GenderDetailsResponse;
import org.alkemy.challenge.java.exceptions.ResourceNotFoundException;
import org.alkemy.challenge.java.repositories.IGenderRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.services.GenderServiceImpl;
import org.alkemy.challenge.java.services.interfaces.IGenderService;
import org.alkemy.challenge.java.utils.EntitiesUtil;
import org.alkemy.challenge.java.utils.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class GenderServiceTest {
    
    //@Mock
    @MockBean
    IGenderRepository iGenderRepository;

    //@Mock
    @MockBean
    IMovieRepository iMovieRepository;

    //@InjectMocks
    //GenderServiceImpl genderServiceImpl;

    @Autowired
    IGenderService iGenderService;
    

}
