package org.alkemy.challenge.java.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.repositories.IGenderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@TestMethodOrder(OrderAnnotation.class)
public class GenderRepositoryTest {
    
    @Autowired
    private IGenderRepository iGenderRepository;

    @Test
    //@Order(1)
    public void saveTest(){
        Gender gender = new Gender();
            gender.setName("Anime");
            gender.setImage("Image Anime");
        Gender genderFinal = iGenderRepository.save(gender);
        assertNotNull(genderFinal);
    }

    @Test
    public void findAllTest(){
        List<Gender> genders = iGenderRepository.findAll();
        Assertions.assertThat(genders).size().isGreaterThan(0);
    }

    @Test
    public void findByIdTest(){
        Long id = 1L;
        Optional<Gender> gender = iGenderRepository.findById(id);
        Assertions.assertThat(gender.get()).isExactlyInstanceOf(Gender.class);
    }

    @Test
    public void deleteTest(){
        Long id = 1L;
        Optional<Gender> gender = iGenderRepository.findById(id);
        assertTrue(gender.isPresent());
        iGenderRepository.delete(gender.get());
        Optional<Gender> genderFinal = iGenderRepository.findById(id);
        assertFalse(genderFinal.isPresent());
    }


}
