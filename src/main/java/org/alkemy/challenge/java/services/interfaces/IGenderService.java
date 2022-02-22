package org.alkemy.challenge.java.services.interfaces;

import java.util.List;

import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.DTOs.response.GenderDetailsResponse;

public interface IGenderService {
    GenderDTO create(GenderDTO genderDTO);
    List<GenderDTO> read();
    GenderDTO readById(Long id);
    GenderDTO update(GenderDTO genderDTO);
    void delete(Long id);
    GenderDetailsResponse linkWithMovie(Long idGender, Long idMovie);
    GenderDetailsResponse addMovie(Long id, MovieDTO movieDTO);
}
