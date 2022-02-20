package org.alkemy.challenge.java.services.interfaces;

import java.util.List;

import org.alkemy.challenge.java.DTOs.MovieDTO;

public interface IMovieService {
    MovieDTO create(MovieDTO movieDTO);
    List<MovieDTO> read();
    MovieDTO readById(Long id);
        List<MovieDTO> readAllByTitle(String title);
        List<MovieDTO> readAllByGender(Long id);
        List<MovieDTO> readPage(String sortBy, String order);
    MovieDTO update(MovieDTO movieDTO);
    void delete(Long id);
}
