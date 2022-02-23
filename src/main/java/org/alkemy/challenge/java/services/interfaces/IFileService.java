package org.alkemy.challenge.java.services.interfaces;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    MovieDTO updateMovie(MultipartFile file, Long id);
    CharacterDTO updateCharacter(MultipartFile file, Long id);
    GenderDTO updateGender(MultipartFile file, Long id);
}
