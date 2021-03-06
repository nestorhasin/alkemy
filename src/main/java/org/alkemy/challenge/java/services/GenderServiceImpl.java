package org.alkemy.challenge.java.services;

import java.util.List;
import java.util.stream.Collectors;

import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.DTOs.response.GenderDetailsResponse;
import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.exceptions.ResourceNotFoundException;
import org.alkemy.challenge.java.repositories.IGenderRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.services.interfaces.IGenderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenderServiceImpl implements IGenderService {

    @Autowired
    private IGenderRepository iGenderRepository;

    @Autowired
    private IMovieRepository iMovieRepository;

    @Autowired
    private ModelMapper modelMapper;

    // CHEQUEAR SI UTILIZANDO ANNOTATION DE SPRING, MOCKITO NECESITA ESTO...
    public GenderServiceImpl(IGenderRepository iGenderRepository, IMovieRepository iMovieRepository){
        this.iGenderRepository = iGenderRepository;
        this.iMovieRepository = iMovieRepository;
    }

    @Override
    @Transactional
    public GenderDTO create(GenderDTO genderDTO) {
        Gender gender = iGenderRepository.save(modelMapper.map(genderDTO, Gender.class));
        return modelMapper.map(gender, GenderDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenderDTO> read() {
        List<Gender> genders = iGenderRepository.findAll();
        return genders.stream().map(gender -> modelMapper.map(gender, GenderDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GenderDetailsResponse readById(Long id){
        Gender gender = iGenderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Gender", "id", id));
        // ESTE TRATAMIENTO HAY QUE HACERLO DEBIDO A UN BUG DE MODEL MAPPER QUE NO MAPEA LAS COMPOSICIONES
        GenderDetailsResponse genderDetailsResponse = modelMapper.map(gender, GenderDetailsResponse.class);
        if(!gender.getMovies().isEmpty()){
            List<MovieDTO> movieDTOs = gender.getMovies().stream().map(entity -> modelMapper.map(entity, MovieDTO.class)).collect(Collectors.toList());
            genderDetailsResponse.setMovieDTOs(movieDTOs);
        
        }
        return genderDetailsResponse;
    }

    @Override
    @Transactional
    public GenderDTO update(GenderDTO genderDTO) {
        Gender gender = iGenderRepository.findById(genderDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Gender", "id", genderDTO.getId()));
            gender.setImage(genderDTO.getImage());
            gender.setName(genderDTO.getName());
        return modelMapper.map(iGenderRepository.save(gender), GenderDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Gender gender = iGenderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Gender", "id", id));
        iGenderRepository.delete(gender);
    }

    @Override
    public GenderDetailsResponse linkWithMovie(Long idGender, Long idMovie) {
        Gender gender = iGenderRepository.findById(idGender).orElseThrow(() -> new ResourceNotFoundException("Gender", "id", idGender));
        Movie movie = iMovieRepository.findById(idMovie).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", idMovie));
        gender.addMovie(movie);
        Gender genderFinal = iGenderRepository.save(gender);
        // ESTE TRATAMIENTO HAY QUE HACERLO DEBIDO A UN BUG DE MODEL MAPPER QUE NO MAPEA LAS COMPOSICIONES
        GenderDetailsResponse genderDetailsResponse = modelMapper.map(genderFinal, GenderDetailsResponse.class);
        if(!genderFinal.getMovies().isEmpty()){
            List<MovieDTO> movieDTOs = genderFinal.getMovies().stream().map(entity -> modelMapper.map(entity, MovieDTO.class)).collect(Collectors.toList());
            genderDetailsResponse.setMovieDTOs(movieDTOs);
        }
        return genderDetailsResponse;
    }

    @Override
    public GenderDetailsResponse addMovie(Long id, MovieDTO movieDTO) {
        Gender gender = iGenderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Gender", "id", id));
        Movie movie = iMovieRepository.save(modelMapper.map(movieDTO, Movie.class));
        return linkWithMovie(gender.getId(), movie.getId());
    }
    
}
