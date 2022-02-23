package org.alkemy.challenge.java.utils;

import java.time.LocalDate;

import org.alkemy.challenge.java.DTOs.response.CharacterDetailsResponse;
import org.alkemy.challenge.java.DTOs.response.CharacterResponse;
import org.alkemy.challenge.java.DTOs.response.GenderDetailsResponse;
import org.alkemy.challenge.java.DTOs.response.MovieDetailsResponse;
import org.alkemy.challenge.java.DTOs.response.MovieResponse;

public class ResponseUtil {
    
    public static final CharacterDetailsResponse CHARACTER_DETAILS_RESPONSE_ONE = new CharacterDetailsResponse(1L, "imageOne", "nameOne", 1, 1.0, "historyOne", null);
    public static final CharacterDetailsResponse CHARACTER_DETAILS_RESPONSE_TWO = new CharacterDetailsResponse(2L, "imageTwo", "nameTwo", 2, 2.0, "historyTwo", null);
    public static final CharacterDetailsResponse CHARACTER_DETAILS_RESPONSE_THREE = new CharacterDetailsResponse(3L, "imageThree", "nameThree", 3, 3.0, "historyThree", null);
    
    public static final CharacterResponse CHARACTER_RESPONSE_ONE = new CharacterResponse("imageOne", "nameOne");
    public static final CharacterResponse CHARACTER_RESPONSE_TWO = new CharacterResponse("imageTwo", "nameTwo");
    public static final CharacterResponse CHARACTER_RESPONSE_THREE = new CharacterResponse("imageThree", "nameThree");

    public static final GenderDetailsResponse GENDER_DETAILS_RESPONSE_ONE = new GenderDetailsResponse(1L, "nameOne", "imageOne", null);
    public static final GenderDetailsResponse GENDER_DETAILS_RESPONSE_TWO = new GenderDetailsResponse(2L, "nameTwo", "imageTwo", null);
    public static final GenderDetailsResponse GENDER_DETAILS_RESPONSE_THREE = new GenderDetailsResponse(3L, "nameThree", "imageThree", null);
    
    public static final MovieDetailsResponse MOVIE_DETAILS_RESPONSE_ONE = new MovieDetailsResponse(1L, "imageOne", "titleOne", LocalDate.of(2001, 01, 01), 1, null, null);
    public static final MovieDetailsResponse MOVIE_DETAILS_RESPONSE_TWO = new MovieDetailsResponse(2L, "imageTwo", "titleTwo", LocalDate.of(2002, 02, 02), 2, null, null);
    public static final MovieDetailsResponse MOVIE_DETAILS_RESPONSE_THREE = new MovieDetailsResponse(3L, "imageThree", "titleThree", LocalDate.of(2003, 03, 03), 3, null, null);

    public static final MovieResponse MOVIE_RESPONSE_ONE = new MovieResponse("imageOne", "titleOne", LocalDate.of(2001, 01, 01));
    public static final MovieResponse MOVIE_RESPONSE_TWO = new MovieResponse("imageTwo", "titleTwo", LocalDate.of(2002, 02, 02));
    public static final MovieResponse MOVIE_RESPONSE_THREE = new MovieResponse("imageThree", "titleThree", LocalDate.of(2003, 03, 03));

}
