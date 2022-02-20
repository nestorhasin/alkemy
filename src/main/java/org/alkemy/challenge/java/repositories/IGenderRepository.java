package org.alkemy.challenge.java.repositories;

import java.io.Serializable;

import org.alkemy.challenge.java.entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenderRepository extends JpaRepository<Gender, Serializable> {
    
}
