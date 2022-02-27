package org.alkemy.challenge.java.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.response.CharacterDetailsResponse;
import org.alkemy.challenge.java.DTOs.response.CharacterResponse;
import org.alkemy.challenge.java.entities.Character;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.repositories.ICharacterRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.services.interfaces.ICharacterService;
import org.alkemy.challenge.java.utils.DTOsUtil;
import org.alkemy.challenge.java.utils.EntitiesUtil;
import org.alkemy.challenge.java.utils.ResponseUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CharacterServiceTest {
    
    //@Mock
    @MockBean
    ICharacterRepository iCharacterRepository;

    //@Mock
    @MockBean
    IMovieRepository iMovieRepository;

    //@Spy
    //@Mock
    @MockBean
    ModelMapper modelMapper;

    //@InjectMocks
    //CharacterServiceImpl characterServiceImpl;

    @Autowired
    ICharacterService iCharacterService;

    @Test
    public void readTest() {
        // Given
        Character characterOne = EntitiesUtil.CHARACTER_ONE;
        Character characterTwo = EntitiesUtil.CHARACTER_TWO;

        List<Character> characters = Arrays.asList(characterOne, characterTwo);
        
        when(iCharacterRepository.findAll()).thenReturn(characters);
        
        // When
        List<CharacterResponse> characterResponses = iCharacterService.read();
        
        // Then
        assertFalse(characters.isEmpty());
        assertEquals(2, characters.size());
        assertTrue(characterResponses.contains(modelMapper.map(characterOne, CharacterResponse.class)));
        
        verify(iCharacterRepository).findAll();
    }

    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    public void createTest(){
        // Given
        CharacterDTO characterDTO = DTOsUtil.CHARACTER_DTO_ONE;
            characterDTO.setMovieDTOs(Collections.EMPTY_LIST);
        
        Character character = EntitiesUtil.CHARACTER_ONE;
            character.setMovies(Collections.EMPTY_LIST);
        
        when(iCharacterRepository.save(any())).thenReturn(character);
        when(modelMapper.map(character, CharacterDTO.class)).thenReturn(characterDTO);
        when(modelMapper.map(characterDTO, Character.class)).thenReturn(character);
        
        // When
        CharacterDTO createdCharacterDTO = iCharacterService.create(characterDTO);
        
        // Then
        assertEquals(character.getImage(), createdCharacterDTO.getImage());
        assertEquals(character.getName(), createdCharacterDTO.getName());
        
        verify(iCharacterRepository).save(character);
    }

    @Test
    public void readByIdTest(){
        // Given
        CharacterDTO characterDTO = DTOsUtil.CHARACTER_DTO_THREE;
            characterDTO.setMovieDTOs(Collections.EMPTY_LIST);
        
        Character character = EntitiesUtil.CHARACTER_THREE;
            character.setMovies(Collections.EMPTY_LIST);
        
        Optional<Character> optionalCharacter = Optional.of(character);
        
        CharacterDetailsResponse characterDetailsResponse = ResponseUtil.CHARACTER_DETAILS_RESPONSE_THREE;
            characterDetailsResponse.setMovieDTOs(Collections.EMPTY_LIST);
        
        when(iCharacterRepository.findById(character.getId())).thenReturn(optionalCharacter);
        when(modelMapper.map(characterDTO, Character.class)).thenReturn(character);
        when(modelMapper.map(character, CharacterDetailsResponse.class)).thenReturn(characterDetailsResponse);

        // When
        CharacterDetailsResponse finalCharacterDetailsResponse = iCharacterService.readById(character.getId());
    
        // Then
        assertEquals(character.getImage(), finalCharacterDetailsResponse.getImage());
        assertEquals(character.getName(), finalCharacterDetailsResponse.getName());
        
        verify(iCharacterRepository).findById(character.getId());
    }

    @Test
    public void readAllByNameTest(){
        // Given
        CharacterDTO characterDTO = DTOsUtil.CHARACTER_DTO_THREE;
            characterDTO.setMovieDTOs(Collections.EMPTY_LIST);
        
        Character character = EntitiesUtil.CHARACTER_THREE;
            character.setMovies(Collections.EMPTY_LIST);
        
        when(iCharacterRepository.findAllByName(character.getName())).thenReturn(Collections.singletonList(character));
        when(modelMapper.map(character, CharacterDTO.class)).thenReturn(characterDTO);
        when(modelMapper.map(characterDTO, Character.class)).thenReturn(character);

        // When
        List<CharacterDTO> characterDTOs = iCharacterService.readAllByName(character.getName());
    
        // Then
        assertEquals(character.getName(), characterDTOs.get(0).getName());
        assertEquals(character.getAge(), characterDTOs.get(0).getAge());
        
        verify(iCharacterRepository).findAllByName(character.getName());
    }

    @Test
    public void readAllByAgeTest(){
        // Given
        CharacterDTO characterDTO = DTOsUtil.CHARACTER_DTO_THREE;
            characterDTO.setMovieDTOs(Collections.EMPTY_LIST);
        
        Character character = EntitiesUtil.CHARACTER_THREE;
            character.setMovies(Collections.EMPTY_LIST);
        
        when(iCharacterRepository.findAllByAge(character.getAge())).thenReturn(Collections.singletonList(character));
        when(modelMapper.map(character, CharacterDTO.class)).thenReturn(characterDTO);
        when(modelMapper.map(characterDTO, Character.class)).thenReturn(character);

        // When
        List<CharacterDTO> characterDTOs = iCharacterService.readAllByAge(character.getAge());
    
        // Then
        assertEquals(character.getName(), characterDTOs.get(0).getName());
        assertEquals(character.getAge(), characterDTOs.get(0).getAge());
        
        verify(iCharacterRepository).findAllByAge(character.getAge());
    }

    @Test
    public void readAllByWeightTest(){
        // Given
        CharacterDTO characterDTO = DTOsUtil.CHARACTER_DTO_THREE;
            characterDTO.setMovieDTOs(Collections.EMPTY_LIST);
        
        Character character = EntitiesUtil.CHARACTER_THREE;
            character.setMovies(Collections.EMPTY_LIST);
        
        when(iCharacterRepository.findAllByWeight(character.getWeight())).thenReturn(Collections.singletonList(character));
        when(modelMapper.map(character, CharacterDTO.class)).thenReturn(characterDTO);
        when(modelMapper.map(characterDTO, Character.class)).thenReturn(character);

        // When
        List<CharacterDTO> characterDTOs = iCharacterService.readAllByWeight(character.getWeight());
    
        // Then
        assertEquals(character.getName(), characterDTOs.get(0).getName());
        assertEquals(character.getAge(), characterDTOs.get(0).getAge());
        
        verify(iCharacterRepository).findAllByWeight(character.getWeight());
    }

    @Test
    public void readAllByMovieTest(){
        // Given
        CharacterDTO characterDTOOne = DTOsUtil.CHARACTER_DTO_THREE;
            characterDTOOne.setMovieDTOs(Collections.EMPTY_LIST);
        CharacterDTO characterDTOTwo = DTOsUtil.CHARACTER_DTO_TWO;
            characterDTOOne.setMovieDTOs(Collections.EMPTY_LIST);
        
        Movie movieOne = EntitiesUtil.MOVIE_ONE;
            movieOne.setCharacters(Collections.EMPTY_LIST);
        Movie movieTwo = EntitiesUtil.MOVIE_ONE;
            movieTwo.setCharacters(Collections.EMPTY_LIST);
        Movie movieThree = EntitiesUtil.MOVIE_THREE;
            movieThree.setCharacters(Collections.EMPTY_LIST);
        
        Character characterOne = EntitiesUtil.CHARACTER_THREE;
            characterOne.setMovies(Arrays.asList(movieOne, movieTwo, movieThree));
        Character characterTwo = EntitiesUtil.CHARACTER_TWO;
            characterTwo.setMovies(Arrays.asList(movieOne));
        
        when(iMovieRepository.findById(movieOne.getId())).thenReturn(Optional.of(movieOne));
        when(iCharacterRepository.findAllByMovies(movieOne)).thenReturn(Arrays.asList(characterOne, characterTwo));
        when(modelMapper.map(characterOne, CharacterDTO.class)).thenReturn(characterDTOOne);
        when(modelMapper.map(characterTwo, CharacterDTO.class)).thenReturn(characterDTOTwo);
        when(modelMapper.map(characterDTOOne, Character.class)).thenReturn(characterOne);
        when(modelMapper.map(characterDTOTwo, Character.class)).thenReturn(characterTwo);

        // When
        List<CharacterDTO> characterDTOs = iCharacterService.readAllByMovie(movieOne.getId());
    
        // Then
        assertEquals(characterOne.getName(), characterDTOs.get(0).getName());
        assertEquals(characterTwo.getAge(), characterDTOs.get(1).getAge());
        assertTrue(characterDTOs.size() == 2);
        
        verify(iCharacterRepository).findAllByMovies(movieOne);
    }

    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    public void updateTest(){
        // Given
        CharacterDTO characterDTO = DTOsUtil.CHARACTER_DTO_ONE;
            characterDTO.setMovieDTOs(Collections.EMPTY_LIST);
        
        Character character = EntitiesUtil.CHARACTER_ONE;
            character.setMovies(Collections.EMPTY_LIST);
        
        when(iCharacterRepository.findById(characterDTO.getId())).thenReturn(Optional.of(character));
        when(iCharacterRepository.save(any())).thenReturn(character);
        when(modelMapper.map(character, CharacterDTO.class)).thenReturn(characterDTO);
        when(modelMapper.map(characterDTO, Character.class)).thenReturn(character);
        
        // When
        CharacterDTO updatedCharacterDTO = iCharacterService.update(characterDTO);
        
        // Then
        assertEquals(character.getImage(), updatedCharacterDTO.getImage());
        assertEquals(character.getHistory(), updatedCharacterDTO.getHistory());
    
        verify(iCharacterRepository).save(character);
    }

    @Test
    public void deleteTest(){
        // Given
        Character character = EntitiesUtil.CHARACTER_ONE;
            character.setMovies(Collections.EMPTY_LIST);
        
        when(iCharacterRepository.findById(character.getId())).thenReturn(Optional.of(character));

        // When
        iCharacterService.delete(character.getId());
        
        // Then
        verify(iCharacterRepository).delete(character);
    }

    /*
    @Test
    public void linkWithMovieTest(){
        // Given
        Movie movie = EntitiesUtil.MOVIE_ONE;
            movie.setCharacters(Collections.EMPTY_LIST);

        Character character = EntitiesUtil.CHARACTER_ONE;
            character.setMovies(Collections.EMPTY_LIST);

        CharacterDetailsResponse characterDetailsResponse = ResponseUtil.CHARACTER_DETAILS_RESPONSE_ONE;
            characterDetailsResponse.setMovieDTOs(Collections.EMPTY_LIST);

        MovieDTO movieDTO = DTOsUtil.MOVIE_DTO_ONE;
            movieDTO.setCharacterDTOs(Collections.EMPTY_LIST);

        when(iCharacterRepository.findById(character.getId())).thenReturn(Optional.of(character));
        when(iMovieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));
        when(iCharacterRepository.save(character)).thenReturn(character);
        when(modelMapper.map(character, CharacterDetailsResponse.class)).thenReturn(characterDetailsResponse);
        when(modelMapper.map(movie, MovieDTO.class)).thenReturn(movieDTO);

        // When
        CharacterDetailsResponse finalCharacterDetailsResponse = characterServiceImpl.linkWithMovie(character.getId(), movie.getId());

        // Then
        assertEquals(character.getName(), finalCharacterDetailsResponse.getName());
        assertEquals(character.getAge(), finalCharacterDetailsResponse.getAge());
        assertEquals(movie.getImage(), finalCharacterDetailsResponse.getMovieDTOs().get(0).getImage());
        assertEquals(movie.getTitle(), finalCharacterDetailsResponse.getMovieDTOs().get(0).getTitle());

        verify(iCharacterRepository).save(character);
    }
    */

}
