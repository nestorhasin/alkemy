package org.alkemy.challenge.java.utils;

import java.time.LocalDate;

import org.alkemy.challenge.java.DTOs.CharacterDTO;
import org.alkemy.challenge.java.DTOs.GenderDTO;
import org.alkemy.challenge.java.DTOs.MovieDTO;

public class DTOsUtil {

    public static final CharacterDTO CHARACTER_DTO_ONE = new CharacterDTO(1L, "imageOne", "nameOne", 1, 1.0, "historyOne", null);
    public static final CharacterDTO CHARACTER_DTO_TWO = new CharacterDTO(2L, "imageTwo", "nameTwo", 2, 2.0, "historyTwo", null);
    public static final CharacterDTO CHARACTER_DTO_THREE = new CharacterDTO(3L, "imageThree", "nameThree", 3, 3.0, "historyThree", null);
    
    public static final GenderDTO GENDER_DTO_ONE = new GenderDTO(1L, "nameOne", "imageOne", null);
    public static final GenderDTO GENDER_DTO_TWO = new GenderDTO(2L, "nameTwo", "imageTwo", null);
    public static final GenderDTO GENDER_DTO_THREE = new GenderDTO(3L, "nameThree", "imageThree", null);
    
    public static final MovieDTO MOVIE_DTO_ONE = new MovieDTO(1L, "imageOne", "titleOne", LocalDate.of(2001, 01, 01), 1, null, null);
    public static final MovieDTO MOVIE_DTO_TWO = new MovieDTO(2L, "imageTwo", "titleTwo", LocalDate.of(2002, 02, 02), 2, null, null);
    public static final MovieDTO MOVIE_DTO_THREE = new MovieDTO(3L, "imageThree", "titleThree", LocalDate.of(2003, 03, 03), 3, null, null);
    
}
