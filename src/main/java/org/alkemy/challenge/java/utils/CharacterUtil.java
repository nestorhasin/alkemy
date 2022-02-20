package org.alkemy.challenge.java.utils;

import java.util.Optional;

import org.alkemy.challenge.java.DTOs.CharacterDTO;

public class CharacterUtil {

    public static Optional<CharacterDTO> create(){
        CharacterDTO characterDto = new CharacterDTO();
            characterDto.setId(1L);
            characterDto.setImage("https://ar.linkedin.com/in/nestorhasin");
            characterDto.setName("Nestor Hasin");
            characterDto.setAge(34);
            characterDto.setWeight(100.00);
            characterDto.setHistory("Desde que comenzó la pandemia por el COVID-19 me puse como meta ser programador y cambiar el paradigma de mi vida. Actualmente soy un apasionado del código y estoy seguro que la crisis nos da la oportunidad de reafirmar los caminos elegidos o, como en mi caso, tomar nuevos rumbos... HELLO PEOPLE!");
        return Optional.of(characterDto);
    }
    
}
