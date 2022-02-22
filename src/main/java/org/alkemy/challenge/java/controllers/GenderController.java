package org.alkemy.challenge.java.controllers;

import javax.validation.Valid;

import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.services.interfaces.IGenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/genders")
public class GenderController {

    @Autowired
    private IGenderService iGenderService;

    @GetMapping
    public ResponseEntity<?> getGenders() {
        return new ResponseEntity<>(iGenderService.read(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGender(@PathVariable Long id) {
        return new ResponseEntity<>(iGenderService.readById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createGender(@Valid @RequestBody GenderDTO genderDTO) {
        return new ResponseEntity<>(iGenderService.create(genderDTO), HttpStatus.CREATED);
    }

    @PostMapping("/{idGenre}/movie/{idMovie}")
    public ResponseEntity<?> linkWithMovie(@PathVariable Long idGenre, @PathVariable Long idMovie) {
        return new ResponseEntity<>(iGenderService.linkWithMovie(idGenre, idMovie), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addMovie(@PathVariable Long id, @Valid @RequestBody MovieDTO movieDTO) {
        return new ResponseEntity<>(iGenderService.addMovie(id, movieDTO), HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<?> updateGender(@Valid @RequestBody GenderDTO genderDTO) {
        return new ResponseEntity<>(iGenderService.update(genderDTO), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGender(@PathVariable Long id) {
        iGenderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
