package org.alkemy.challenge.java.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.entities.Character;
import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.exceptions.ResourceNotFoundException;
import org.alkemy.challenge.java.repositories.ICharacterRepository;
import org.alkemy.challenge.java.repositories.IGenderRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.services.interfaces.IFileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private IMovieRepository iMovieRepository;

    @Autowired
    private ICharacterRepository iCharacterRepository;

    @Autowired
    private IGenderRepository iGenderRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String PATH_MOVIE = "src//main//resources//static/images/movies";

    private static final String PATH_CHARACTER = "src//main//resources//static/images/characters";

    private static final String PATH_GENDER = "src//main//resources//static/images/genders";

    private void upload(MultipartFile file, String path){
        Path relativePath = Paths.get(path);
        String absolutePath = relativePath.toFile().getAbsolutePath();
        try {
            byte[] bytes = file.getBytes();
            Path completePath = Paths.get(absolutePath + "//" + file.getOriginalFilename());
            Files.write(completePath, bytes);
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
    }

    @Override
    public MovieDTO updateMovie(MultipartFile file, Long id) {
        Movie movie = iMovieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        upload(file, PATH_MOVIE);
        movie.setImage(file.getOriginalFilename());
        return modelMapper.map(iMovieRepository.save(movie), MovieDTO.class);
    }

    @Override
    public CharacterDTO updateCharacter(MultipartFile file, Long id) {
        Character character = iCharacterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Character", "id", id));
        upload(file, PATH_CHARACTER);
        character.setImage(file.getOriginalFilename());
        return modelMapper.map(iCharacterRepository.save(character), CharacterDTO.class);
    }

    @Override
    public GenderDTO updateGender(MultipartFile file, Long id) {
        Gender gender = iGenderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Gender", "id", id));
        upload(file, PATH_GENDER);
        gender.setImage(file.getOriginalFilename());
        return modelMapper.map(iGenderRepository.save(gender), GenderDTO.class);
    }
    
}
