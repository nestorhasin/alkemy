package org.alkemy.challenge.java.services.interfaces;

import java.util.List;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.DTOs.response.MovieDetailsResponse;
import org.alkemy.challenge.java.DTOs.response.MovieResponse;

public interface IMovieService {
    MovieDTO create(MovieDTO movieDTO);
    List<MovieResponse> read();
    MovieDetailsResponse readById(Long id);
        List<MovieDTO> readAllByTitle(String title);
        List<MovieDTO> readAllByGender(Long id);
        List<MovieDTO> readByOrder(String order);
    MovieDTO update(MovieDTO movieDTO);
    void delete(Long id);
    MovieDetailsResponse linkWithCharacter(Long idMovie, Long idCharacter);
    MovieDetailsResponse addCharacter(Long idMovie, CharacterDTO characterDTO);
    MovieDetailsResponse linkWithGender(Long idMovie, Long idGender);
    MovieDetailsResponse addGender(Long idMovie, GenderDTO genderDTO);
}
