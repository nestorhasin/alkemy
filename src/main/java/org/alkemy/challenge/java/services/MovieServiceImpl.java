package org.alkemy.challenge.java.services;

import java.util.List;
import java.util.stream.Collectors;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.DTOs.response.MovieDetailsResponse;
import org.alkemy.challenge.java.DTOs.response.MovieResponse;
import org.alkemy.challenge.java.entities.Character;
import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.exceptions.ResourceNotFoundException;
import org.alkemy.challenge.java.repositories.ICharacterRepository;
import org.alkemy.challenge.java.repositories.IGenderRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.services.interfaces.IMovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    private IMovieRepository iMovieRepository;

    @Autowired
    private ICharacterRepository iCharacterRepository;

    @Autowired
    private IGenderRepository iGenderRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String ORDER_BY = "creationDate";

    @Override
    @Transactional
    public MovieDTO create(MovieDTO movieDTO) {
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        return modelMapper.map(iMovieRepository.save(movie), MovieDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> read() {
        return iMovieRepository.findAll().stream().map(movie -> modelMapper.map(movie, MovieResponse.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDTO> readByOrder(String order){
        String sortBy = ORDER_BY;
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        List<Movie> movies = iMovieRepository.findAll(sort);
        return movies.stream().map(movie -> modelMapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MovieDetailsResponse readById(Long id){
        Movie movie = iMovieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        // ESTE TRATAMIENTO HAY QUE HACERLO DEBIDO A UN BUG DE MODEL MAPPER QUE NO MAPEA LAS COMPOSICIONES
        MovieDetailsResponse movieDetailsResponse = modelMapper.map(movie, MovieDetailsResponse.class);
        if(!movie.getCharacters().isEmpty()){
            List<CharacterDTO> characterDTOs = movie.getCharacters().stream().map(entity -> modelMapper.map(entity, CharacterDTO.class)).collect(Collectors.toList());
            movieDetailsResponse.setCharacterDTOs(characterDTOs);
        }
        if(movie.getGender() != null){
            GenderDTO genderDTO = modelMapper.map(movie.getGender(), GenderDTO.class);
            movieDetailsResponse.setGenderDTO(genderDTO);
        }
        return movieDetailsResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDTO> readAllByTitle(String title) {
        List<Movie> movies= iMovieRepository.findAllByTitle(title);
        return movies.stream().map(movie -> modelMapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDTO> readAllByGender(Long id) {
        Gender gender = iGenderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Gender", "id", id));
        List<Movie> movies = iMovieRepository.findAllByGender(gender);
        return movies.stream().map(movie -> modelMapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovieDTO update(MovieDTO movieDTO) {
        Movie movie = iMovieRepository.findById(movieDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieDTO.getId()));
            movie.setImage(movieDTO.getImage());
            movie.setTitle(movieDTO.getTitle());
            movie.setCreationDate(movieDTO.getCreationDate());
            movie.setQualification(movieDTO.getQualification());
        return modelMapper.map(iMovieRepository.save(movie), MovieDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Movie movie = iMovieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        iMovieRepository.delete(movie);
    }

    @Override
    @Transactional
    public MovieDetailsResponse linkWithCharacter(Long idMovie, Long idCharacter) {
        Movie movie = iMovieRepository.findById(idMovie).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", idMovie));
        Character character = iCharacterRepository.findById(idCharacter).orElseThrow(() -> new ResourceNotFoundException("Character", "id", idCharacter));
        movie.addCharacter(character);
        Movie movieFinal = iMovieRepository.save(movie);
        // ESTE TRATAMIENTO HAY QUE HACERLO DEBIDO A UN BUG DE MODEL MAPPER QUE NO MAPEA LAS COMPOSICIONES
        MovieDetailsResponse movieDetailsResponse = modelMapper.map(movieFinal, MovieDetailsResponse.class);
        List<CharacterDTO> characterDTOs = movieFinal.getCharacters().stream().map(entity -> modelMapper.map(entity, CharacterDTO.class)).collect(Collectors.toList());
        movieDetailsResponse.setCharacterDTOs(characterDTOs);
        if(movie.getGender() != null){
            GenderDTO genderDTO = modelMapper.map(movie.getGender(), GenderDTO.class);
            movieDetailsResponse.setGenderDTO(genderDTO);
        }
        return movieDetailsResponse;
    }

    @Override
    @Transactional
    public MovieDetailsResponse addCharacter(Long idMovie, CharacterDTO characterDTO) {
        Movie movie = iMovieRepository.findById(idMovie).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", idMovie));
        Character character = iCharacterRepository.save(modelMapper.map(characterDTO, Character.class));
        return linkWithCharacter(movie.getId(), character.getId());
    }

    @Override
    public MovieDetailsResponse linkWithGender(Long idMovie, Long idGender) {
        Movie movie = iMovieRepository.findById(idMovie).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", idMovie));
        Gender gender= iGenderRepository.findById(idGender).orElseThrow(() -> new ResourceNotFoundException("Gender", "id", idGender));
        movie.addGender(gender);
        Movie movieFinal = iMovieRepository.save(movie);
        // ESTE TRATAMIENTO HAY QUE HACERLO DEBIDO A UN BUG DE MODEL MAPPER QUE NO MAPEA LAS COMPOSICIONES
        MovieDetailsResponse movieDetailsResponse = modelMapper.map(movieFinal, MovieDetailsResponse.class);
        GenderDTO genderDTO = modelMapper.map(movieFinal.getGender(), GenderDTO.class);
        movieDetailsResponse.setGenderDTO(genderDTO);
        if(!movieFinal.getCharacters().isEmpty()){
            List<CharacterDTO> characterDTOs = movieFinal.getCharacters().stream().map(entity -> modelMapper.map(entity, CharacterDTO.class)).collect(Collectors.toList());
            movieDetailsResponse.setCharacterDTOs(characterDTOs);
        }
        return movieDetailsResponse;
    }

    @Override
    public MovieDetailsResponse addGender(Long idMovie, GenderDTO genderDTO) {
        Movie movie = iMovieRepository.findById(idMovie).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", idMovie));
        Gender gender = iGenderRepository.save(modelMapper.map(genderDTO, Gender.class));
        return linkWithGender(movie.getId(), gender.getId());
    }
    
}
