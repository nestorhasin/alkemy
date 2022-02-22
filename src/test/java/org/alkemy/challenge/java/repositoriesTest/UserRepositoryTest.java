package org.alkemy.challenge.java.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.alkemy.challenge.java.entities.User;
import org.alkemy.challenge.java.repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {
    
    @Autowired
    private IUserRepository iUserRepository;

    @Test
    public void saveTest(){
        User user = new User();
            user.setName("Julio Cesar");
            user.setUsername("juliocesar");
            user.setEmail("juliocesar@gmail.com");
            user.setPassword("juliocesar");
        User userFinal = iUserRepository.save(user);
        assertNotNull(userFinal);
    }

    @Test
    public void findAllTest(){
        List<User> users = iUserRepository.findAll();
        assertTrue(users.size() >= 1);
    }

    @Test
    public void findByIdTest(){
        Long id = 1L;
        Optional<User> user = iUserRepository.findById(id);
        assertTrue(user.isPresent());
    }

    @Test
    public void findByUsernameTest(){
        String username = "nestorhasin";
        Optional<User> user = iUserRepository.findByUsername(username);
        assertTrue(user.isPresent());
    }

    @Test
    public void findByEmailTest(){
        String email = "nestorhasin@gmail.com";
        Optional<User> user = iUserRepository.findByEmail(email);
        assertTrue(user.isPresent());
    }

    @Test
    public void findByUsernameOrEmailTest(){
        String username = "nestorhasin";
        String email = "nestorhasin@gmail.com";
        Optional<User> userWithUsername = iUserRepository.findByUsernameOrEmail(username, null);
        assertTrue(userWithUsername.isPresent());
        Optional<User> userWithEmail = iUserRepository.findByUsernameOrEmail(null, email);
        assertTrue(userWithEmail.isPresent());
    }

    @Test
    public void existsByUsernameTest(){
        String username = "nestorhasin";
        assertTrue(iUserRepository.existsByUsername(username));
    }

    @Test
    public void existsByEmailTest(){
        String email = "nestorhasin@gmail.com";
        assertTrue(iUserRepository.existsByEmail(email));
    }

    @Test
    public void deleteTest(){
        Long id = 1L;
        Optional<User> user = iUserRepository.findById(id);
        assertTrue(user.isPresent());
        iUserRepository.delete(user.get());
        Optional<User> userFinal = iUserRepository.findById(id);
        assertFalse(userFinal.isPresent());
    }

}
