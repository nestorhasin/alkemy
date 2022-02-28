package org.alkemy.challenge.java.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.alkemy.challenge.java.annotations.RepositoryTest;
import org.alkemy.challenge.java.entities.Role;
import org.alkemy.challenge.java.repositories.IRoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
public class RoleRepositoryTest {
    
    @Autowired
    private IRoleRepository iRoleRepository;

    @Test
    public void saveTest(){
        Role role = new Role(null, "ROLE_USER");
        Role savedRole = iRoleRepository.save(role);
        assertNotNull(savedRole);
    }

    @Test
    public void findAllTest(){
        Role roleOne = new Role(null, "ROLE_USER");
        Role roleTwo = new Role(null, "ROLE_ADMIN");
        iRoleRepository.save(roleOne);
        iRoleRepository.save(roleTwo);
        List<Role> roles = iRoleRepository.findAll();
        Assertions.assertThat(roles).size().isGreaterThan(1);
    }

    @Test
    public void findById(){
        Role role = new Role(1L, "ROLE_USER");
        Role savedRole = iRoleRepository.save(role);
        Optional<Role> optionalRole = iRoleRepository.findById(savedRole.getId());
        assertTrue(optionalRole.isPresent());
    }

    @Test
    public void findByNameTest(){
        Role role = new Role(null, "ROLE_ADMIN");
        Role savedRole = iRoleRepository.save(role);
        Optional<Role> optionalRole = iRoleRepository.findByName(savedRole.getName());
        assertTrue(optionalRole.isPresent());
    }

    @Test
    public void deleteTest(){
        Role role = new Role(2L, "ROLE_USER");
        Role savedRole = iRoleRepository.save(role);
        Optional<Role> optionalRole = iRoleRepository.findById(savedRole.getId());
        assertTrue(optionalRole.isPresent());
        iRoleRepository.delete(optionalRole.get());
        Optional<Role> finalRole = iRoleRepository.findById(savedRole.getId());
        assertFalse(finalRole.isPresent());
    }

}