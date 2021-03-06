package org.alkemy.challenge.java.controllers;

import javax.validation.Valid;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;
import org.alkemy.challenge.java.services.interfaces.IMovieService;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private IMovieService iMovieService;

    @GetMapping
    public ResponseEntity<?> getMovies(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gender", required = false) Long gender,
            @RequestParam(value = "order", required = false) String order) {
        if (name != null) {
            return new ResponseEntity<>(iMovieService.readAllByTitle(name), HttpStatus.OK);
        }
        if (gender != null) {
            return new ResponseEntity<>(iMovieService.readAllByGender(gender), HttpStatus.OK);
        }
        if (order != null) {
            return new ResponseEntity<>(iMovieService.readByOrder(order), HttpStatus.OK);
        }
        return new ResponseEntity<>(iMovieService.read(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id) {
        return new ResponseEntity<>(iMovieService.readById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        return new ResponseEntity<>(iMovieService.create(movieDTO), HttpStatus.CREATED);
    }

    @PostMapping("/{idMovie}/character/{idCharacter}")
    public ResponseEntity<?> linkWithCharacter(@PathVariable Long idMovie, @PathVariable Long idCharacter) {
        return new ResponseEntity<>(iMovieService.linkWithCharacter(idMovie, idCharacter), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/addCharacter")
    public ResponseEntity<?> addCharacter(@PathVariable Long id, @Valid @RequestBody CharacterDTO characterDTO) {
        return new ResponseEntity<>(iMovieService.addCharacter(id, characterDTO), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{idMovie}/gender/{idGender}")
    public ResponseEntity<?> linkWithGender(@PathVariable Long idMovie, @PathVariable Long idGender) {
        return new ResponseEntity<>(iMovieService.linkWithGender(idMovie, idGender), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/addGender")
    public ResponseEntity<?> addGender(@PathVariable Long id, @Valid @RequestBody GenderDTO genderDTO) {
        return new ResponseEntity<>(iMovieService.addGender(id, genderDTO), HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<?> updateMovie(@Valid @RequestBody MovieDTO movieDTO) {
        return new ResponseEntity<>(iMovieService.update(movieDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        iMovieService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
