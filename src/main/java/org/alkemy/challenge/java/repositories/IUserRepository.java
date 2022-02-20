package org.alkemy.challenge.java.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.alkemy.challenge.java.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Serializable> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
