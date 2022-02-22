package org.alkemy.challenge.java.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.alkemy.challenge.java.entities.Character;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.exceptions.ResourceNotFoundException;
import org.alkemy.challenge.java.repositories.ICharacterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CharacterRepositoryTest {
    
    @Autowired
    private ICharacterRepository iCharacterRepository;

    @Test
    public void saveTest(){
        Character character = new Character();
            character.setImage("https://ar.linkedin.com/in/nestorhasin");
            character.setName("Nestor Hasin");
            character.setAge(34);
            character.setWeight(100.00);
            character.setHistory("Desde que comenzó la pandemia por el COVID-19 me puse como meta ser programador y cambiar el paradigma de mi vida. Actualmente soy un apasionado del código y estoy seguro que la crisis nos da la oportunidad de reafirmar los caminos elegidos o, como en mi caso, tomar nuevos rumbos... HELLO PEOPLE!");
        Character characterFinal = iCharacterRepository.save(character);
        assertNotNull(characterFinal);
    }

    @Test
    public void findAllTest(){
        List<Character> characters = iCharacterRepository.findAll();
        assertNotNull(characters);
    }


    @Test
    public void findByIdTest(){
        Long id = 1L;
        Optional<Character> character = iCharacterRepository.findById(id);
        assertDoesNotThrow(() -> character.get(), new ResourceNotFoundException("Character", "id", id).getMessage());
    }

    @Test
    public void findAllByNameTest(){
        String name = "name 1";
        List<Character> characters = iCharacterRepository.findAllByName(name);
        assertEquals(characters.get(0).getName(), name);
    }

    @Test
    public void findAllByAgeTest(){
        Integer age = 35;
        List<Character> characters = iCharacterRepository.findAllByAge(age);
        assertEquals(characters.get(0).getAge(), age);
    }

    @Test
    public void findAllByWeightTest(){
        Double weight = 100.00;
        List<Character> characters = iCharacterRepository.findAllByWeight(weight);
        assertEquals(characters.get(0).getWeight(), weight);
    }

    @Test
    public void findAllByMoviesTest(){
        Movie movie = new Movie(1L, "image 2", "title 2", LocalDate.of(2002, 02, 02), 2, null, null);
        List<Character> characters = iCharacterRepository.findAllByMovies(movie);
        assertEquals(characters.size(), 2);
    }

    @Test
    public void deleteTest(){
        Long id = 1L;
        Optional<Character> character = iCharacterRepository.findById(id);
        assertTrue(character.isPresent());
        iCharacterRepository.delete(character.get());
        Optional<Character> characterFinal = iCharacterRepository.findById(id);
        assertFalse(characterFinal.isPresent());
    }

}
