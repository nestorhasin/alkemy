package org.alkemy.challenge.java.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.alkemy.challenge.java.annotations.RepositoryTest;
import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.exceptions.ResourceNotFoundException;
import org.alkemy.challenge.java.repositories.IGenderRepository;
import org.alkemy.challenge.java.utils.EntitiesUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@RepositoryTest
public class GenderRepositoryTest {
    
    @Autowired
    private IGenderRepository iGenderRepository;

    @Test
    public void saveTest(){
        Gender gender = EntitiesUtil.GENDER_ONE;
        Gender savedGender = iGenderRepository.save(gender);
        assertNotNull(savedGender);
    }

    @Test
    public void findAllTest(){
        Gender genderOne = EntitiesUtil.GENDER_ONE;
        Gender genderTwo = EntitiesUtil.GENDER_TWO;
        Gender genderThree = EntitiesUtil.GENDER_THREE;
        iGenderRepository.save(genderOne);
        iGenderRepository.save(genderTwo);
        iGenderRepository.save(genderThree);
        List<Gender> genders = iGenderRepository.findAll();
        Assertions.assertThat(genders).size().isGreaterThan(0);
    }

    
    @Test
    public void findByIdTest(){
        Gender gender = EntitiesUtil.GENDER_ONE;
        Gender savedGender = iGenderRepository.save(gender);
        Optional<Gender> optionalGender = iGenderRepository.findById(savedGender.getId());
        assertTrue(optionalGender.isPresent());    
    }

    @Test
    public void deleteTest(){
        Gender gender = EntitiesUtil.GENDER_ONE;
        Gender savedGender = iGenderRepository.save(gender);
        Optional<Gender> optionalGender = iGenderRepository.findById(savedGender.getId());
        assertTrue(optionalGender.isPresent());
        iGenderRepository.delete(optionalGender.get());
        Optional<Gender> genderFinal = iGenderRepository.findById(savedGender.getId());
        assertFalse(genderFinal.isPresent());
    }

}
