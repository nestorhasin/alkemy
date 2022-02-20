package org.alkemy.challenge.java.services;

import java.util.List;
import java.util.stream.Collectors;

import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.exceptions.ResourceNotFoundException;
import org.alkemy.challenge.java.repositories.IGenderRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.services.interfaces.IMovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    private IMovieRepository iMovieRepository;

    @Autowired
    private IGenderRepository iGenderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public MovieDTO create(MovieDTO movieDTO) {
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        return modelMapper.map(iMovieRepository.save(movie), MovieDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDTO> read() {
        return iMovieRepository.findAll().stream().map(movie -> modelMapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDTO> readPage(String sortBy, String order){
        sortBy = "creationDate";
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(0, 10, Sort.by(sortBy));
        Page<Movie> moviesPage = iMovieRepository.findAll(pageable);
        List<Movie> movies = moviesPage.getContent();
        return movies.stream().map(movie -> modelMapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MovieDTO readById(Long id){
        Movie movie = iMovieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        return modelMapper.map(movie, MovieDTO.class);
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
    
}
