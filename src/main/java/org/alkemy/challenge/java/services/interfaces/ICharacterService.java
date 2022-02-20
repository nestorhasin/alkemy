package org.alkemy.challenge.java.services.interfaces;

import java.util.List;

import org.alkemy.challenge.java.DTOs.CharacterDTO;

public interface ICharacterService {
    CharacterDTO create(CharacterDTO characterDTO);
    List<CharacterDTO> read();
    CharacterDTO readById(Long id);
        List<CharacterDTO> readAllByName(String name);
        List<CharacterDTO> readAllByAge(Integer age);
        List<CharacterDTO> readAllByWeight(Double weight);
        List<CharacterDTO> readAllByMovie(Long id);
    CharacterDTO update(CharacterDTO characterDTO);
    void delete(Long id);
}
