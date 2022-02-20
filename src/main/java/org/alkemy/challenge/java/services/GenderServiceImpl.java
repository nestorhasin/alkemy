package org.alkemy.challenge.java.services;

import java.util.List;
import java.util.stream.Collectors;

import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.exceptions.ResourceNotFoundException;
import org.alkemy.challenge.java.repositories.IGenderRepository;
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
    private ModelMapper modelMapper;

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
    public GenderDTO readById(Long id){
        Gender gender = iGenderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Gender", "id", id));
        return modelMapper.map(gender, GenderDTO.class);
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
    
}
