package org.alkemy.challenge.java.services;

import java.util.List;
import java.util.stream.Collectors;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.entities.Character;
import org.alkemy.challenge.java.entities.Movie;
import org.alkemy.challenge.java.exceptions.ResourceNotFoundException;
import org.alkemy.challenge.java.repositories.ICharacterRepository;
import org.alkemy.challenge.java.repositories.IMovieRepository;
import org.alkemy.challenge.java.services.interfaces.ICharacterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterServiceImpl implements ICharacterService {

    @Autowired
    private ICharacterRepository iCharacterRepository;

    @Autowired
    private IMovieRepository iMovieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public CharacterDTO create(CharacterDTO characterDTO) {
        Character character = iCharacterRepository.save(modelMapper.map(characterDTO, Character.class));
        return modelMapper.map(character, CharacterDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterDTO> read() {
        List<Character> characters = iCharacterRepository.findAll();
        return characters.stream().map(character -> modelMapper.map(character, CharacterDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CharacterDTO readById(Long id) {
        Character character = iCharacterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Character", "id", id));
        return modelMapper.map(character, CharacterDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterDTO> readAllByName(String name) {
        List<Character> characters = iCharacterRepository.findAllByName(name);
        return characters.stream().map(character -> modelMapper.map(character, CharacterDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterDTO> readAllByAge(Integer age) {
        List<Character> characters = iCharacterRepository.findAllByAge(age);
        return characters.stream().map(character -> modelMapper.map(character, CharacterDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterDTO> readAllByWeight(Double weight){
        List<Character> characters = iCharacterRepository.findAllByWeight(weight);
        return characters.stream().map(character -> modelMapper.map(character, CharacterDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterDTO> readAllByMovie(Long id) {
        Movie movie = iMovieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        List<Character> characters = iCharacterRepository.findAllByMovies(movie);
        return characters.stream().map(character -> modelMapper.map(character, CharacterDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CharacterDTO update(CharacterDTO characterDTO) {
        Character character = iCharacterRepository.findById(characterDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Character", "id", characterDTO.getId()));
            character.setImage(characterDTO.getImage());
            character.setName(characterDTO.getName());
            character.setAge(characterDTO.getAge());
            character.setWeight(characterDTO.getWeight());
            character.setHistory(characterDTO.getHistory());
        return modelMapper.map(iCharacterRepository.save(character), CharacterDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Character character = iCharacterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Character", "id", id));
        iCharacterRepository.delete(character);
    }
    
}
