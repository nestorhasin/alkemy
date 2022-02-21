package org.alkemy.challenge.java.services.interfaces;

import java.util.List;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.DTOs.response.CharacterDetailsResponse;
import org.alkemy.challenge.java.DTOs.response.CharacterResponse;

public interface ICharacterService {
    CharacterDTO create(CharacterDTO characterDTO);
    List<CharacterResponse> read();
    CharacterDetailsResponse readById(Long id);
        List<CharacterDTO> readAllByName(String name);
        List<CharacterDTO> readAllByAge(Integer age);
        List<CharacterDTO> readAllByWeight(Double weight);
        List<CharacterDTO> readAllByMovie(Long id);
    CharacterDTO update(CharacterDTO characterDTO);
    void delete(Long id);
    CharacterDetailsResponse linkWithMovie(Long idCharacter, Long idMovie);
    CharacterDetailsResponse addMovie(Long idCharacter, MovieDTO movieDTO);
}
