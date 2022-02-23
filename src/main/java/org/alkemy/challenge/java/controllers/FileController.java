package org.alkemy.challenge.java.controllers;

import org.alkemy.challenge.java.services.interfaces.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class FileController {

    /*
    Para acceder a los métodos de este Controller por Postman tenemos que enviar la imagen vía 'form-data'
    */

    @Autowired
    private IFileService iFileService;

    @PostMapping(value = "/movie/{id}")
    public ResponseEntity<?> uploadInMovies(@RequestParam(value = "file") MultipartFile file,
            @PathVariable Long id) {
        /*
        File file = new File("C://" + file.getOriginalFilename());
            file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
        */
        return new ResponseEntity<>(iFileService.updateMovie(file, id), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/character/{id}")
    public ResponseEntity<?> uploadInCharacters(@RequestParam(value = "file") MultipartFile file,
            @PathVariable Long id) {
        return new ResponseEntity<>(iFileService.updateCharacter(file, id), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/gender/{id}")
    public ResponseEntity<?> uploadInGenders(@RequestParam(value = "file") MultipartFile file, 
            @PathVariable Long id) {
        return new ResponseEntity<>(iFileService.updateGender(file, id), HttpStatus.ACCEPTED);
    }

}
