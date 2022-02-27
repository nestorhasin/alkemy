package org.alkemy.challenge.java.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.alkemy.challenge.java.annotations.RepositoryTest;
import org.alkemy.challenge.java.entities.Character;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.exceptions.ResourceNotFoundException;
import org.alkemy.challenge.java.repositories.ICharacterRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.utils.EntitiesUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@RepositoryTest
public class CharacterRepositoryTest {
    
    @Autowired
    private ICharacterRepository iCharacterRepository;

    @Test
    public void saveTest(){
        Character character = EntitiesUtil.CHARACTER_ONE;
        Character savedCharacter = iCharacterRepository.save(character);
        assertNotNull(savedCharacter);
    }

    @Test
    public void findAllTest(){
        Character characterOne = EntitiesUtil.CHARACTER_ONE;
        Character characterTwo = EntitiesUtil.CHARACTER_TWO;
        Character characterThree = EntitiesUtil.CHARACTER_THREE;
        iCharacterRepository.save(characterOne);
        iCharacterRepository.save(characterTwo);
        iCharacterRepository.save(characterThree);
        List<Character> characters = iCharacterRepository.findAll();
        Assertions.assertThat(characters).size().isGreaterThan(2);
    }


    @Test
    public void findByIdTest(){
        Character character = EntitiesUtil.CHARACTER_ONE;
        Character savedCharacter = iCharacterRepository.save(character);
        Optional<Character> optionalCharacter = iCharacterRepository.findById(savedCharacter.getId());
        assertTrue(optionalCharacter.isPresent());
    }

    @Test
    public void findAllByNameTest(){
        Character character = EntitiesUtil.CHARACTER_ONE;
        Character savedCharacter = iCharacterRepository.save(character);
        List<Character> characters = iCharacterRepository.findAllByName(savedCharacter.getName());
        assertEquals(characters.get(0).getName(), savedCharacter.getName());
    }

    @Test
    public void findAllByAgeTest(){
        Character character = EntitiesUtil.CHARACTER_ONE;
        Character savedCharacter = iCharacterRepository.save(character);
        List<Character> characters = iCharacterRepository.findAllByAge(savedCharacter.getAge());
        assertEquals(characters.get(0).getAge(), savedCharacter.getAge());
    }

    @Test
    public void findAllByWeightTest(){
        Character character = EntitiesUtil.CHARACTER_ONE;
        Character savedCharacter = iCharacterRepository.save(character);
        List<Character> characters = iCharacterRepository.findAllByWeight(savedCharacter.getWeight());
        assertEquals(characters.get(0).getWeight(), savedCharacter.getWeight());
    }

    /*
    @Test
    public void findAllByMoviesTest(){
        Movie movie = EntitiesUtil.MOVIE_ONE;
        Character character = EntitiesUtil.CHARACTER_THREE;
            //character.setMovies(Collections.singletonList(movie)); IndexOfBoundsException
            //character.addMovie(movie); NullPointerException
        Character savedCharacter = iCharacterRepository.save(character);
        List<Character> characters = iCharacterRepository.findAllByMovies(savedCharacter.getMovies().get(0));
        assertEquals(characters.get(0).getName(), savedCharacter.getName());
    }
    */

    @Test
    public void deleteTest(){
        Character character = EntitiesUtil.CHARACTER_TWO;
        Character savedCharacter = iCharacterRepository.save(character);
        Optional<Character> optionalCharacter = iCharacterRepository.findById(savedCharacter.getId());
        assertTrue(optionalCharacter.isPresent());
        iCharacterRepository.delete(optionalCharacter.get());
        Optional<Character> finalCharacter = iCharacterRepository.findById(savedCharacter.getId());
        assertFalse(finalCharacter.isPresent());
    }

}
