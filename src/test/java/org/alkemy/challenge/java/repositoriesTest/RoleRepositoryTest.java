package org.alkemy.challenge.java.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.alkemy.challenge.java.entities.Role;
import org.alkemy.challenge.java.repositories.IRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RoleRepositoryTest {
    
    @Autowired
    private IRoleRepository iRoleRepository;

    @Test
    public void saveTest(){
        Role role = new Role();
            role.setName("ROLE_USER");
        Role roleFinal = iRoleRepository.save(role);
        assertNotNull(roleFinal);
    }

    @Test
    public void findAllTest(){
        List<Role> roles = iRoleRepository.findAll();
        assertEquals(roles.size(), 2);
    }

    @Test
    public void findById(){
        Long id = 1L;
        Optional<Role> role = iRoleRepository.findById(id);
        assertTrue(role.isPresent());
    }

    @Test
    public void findByNameTest(){
        String name = "ROLE_ADMIN";
        Optional<Role> role = iRoleRepository.findByName(name);
        assertTrue(role.isPresent());
    }

    @Test
    public void deleteTest(){
        Long id = 2L;
        Optional<Role> role = iRoleRepository.findById(id);
        assertTrue(role.isPresent());
        iRoleRepository.delete(role.get());
        Optional<Role> roleFinal = iRoleRepository.findById(id);
        assertFalse(roleFinal.isPresent());
    }

}