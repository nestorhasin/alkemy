package org.alkemy.challenge.java.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.alkemy.challenge.java.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Serializable>  {
    Optional<Role> findByName(String name);
}
