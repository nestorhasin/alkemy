package org.alkemy.challenge.java.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.alkemy.challenge.java.annotations.RepositoryTest;
import org.alkemy.challenge.java.entities.User;
import org.alkemy.challenge.java.repositories.IUserRepository;
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
public class UserRepositoryTest {
    
    @Autowired
    private IUserRepository iUserRepository;

    @Test
    public void saveTest(){
        User user = EntitiesUtil.USER_ONE;
        User savedUser = iUserRepository.save(user);
        assertNotNull(savedUser);
    }

    @Test
    public void findAllTest(){
        User userOne = EntitiesUtil.USER_ONE;
        User userTwo = EntitiesUtil.USER_TWO;
        User userThree = EntitiesUtil.USER_THREE;
        iUserRepository.save(userOne);
        iUserRepository.save(userTwo);
        iUserRepository.save(userThree);
        List<User> users = iUserRepository.findAll();
        Assertions.assertThat(users).size().isGreaterThan(2);
    }

    @Test
    public void findByIdTest(){
        User user = EntitiesUtil.USER_THREE;
        User savedUser = iUserRepository.save(user);
        Optional<User> optionalUser = iUserRepository.findById(savedUser.getId());
        assertTrue(optionalUser.isPresent());
    }

    @Test
    public void findByUsernameTest(){
        User user = EntitiesUtil.USER_TWO;
        User savedUser = iUserRepository.save(user);
        Optional<User> optionalUser = iUserRepository.findByUsername(savedUser.getUsername());
        assertTrue(optionalUser.isPresent());
    }

    @Test
    public void findByEmailTest(){
        User user = EntitiesUtil.USER_ONE;
        User savedUser = iUserRepository.save(user);
        Optional<User> optionalUser = iUserRepository.findByEmail(savedUser.getEmail());
        assertTrue(optionalUser.isPresent());
    }

    @Test
    public void findByUsernameOrEmailTest(){
        User user = EntitiesUtil.USER_THREE;
        User savedUser = iUserRepository.save(user);
        Optional<User> userWithUsername = iUserRepository.findByUsernameOrEmail(savedUser.getUsername(), null);
        assertTrue(userWithUsername.isPresent());
        Optional<User> userWithEmail = iUserRepository.findByUsernameOrEmail(null, savedUser.getEmail());
        assertTrue(userWithEmail.isPresent());
    }

    @Test
    public void existsByUsernameTest(){
        User user = EntitiesUtil.USER_THREE;
        User savedUser = iUserRepository.save(user);
        assertTrue(iUserRepository.existsByUsername(savedUser.getUsername()));
    }

    @Test
    public void existsByEmailTest(){
        User user = EntitiesUtil.USER_TWO;
        User savedUser = iUserRepository.save(user);
        assertTrue(iUserRepository.existsByEmail(savedUser.getEmail()));
    }

    @Test
    public void deleteTest(){
        User user = EntitiesUtil.USER_THREE;
        User savedUser = iUserRepository.save(user);
        Optional<User> optionalUser = iUserRepository.findById(savedUser.getId());
        assertTrue(optionalUser.isPresent());
        iUserRepository.delete(optionalUser.get());
        Optional<User> userFinal = iUserRepository.findById(savedUser.getId());
        assertFalse(userFinal.isPresent());
    }

}
