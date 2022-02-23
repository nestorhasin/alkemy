package org.alkemy.challenge.java.utils;

import java.time.LocalDate;

import org.alkemy.challenge.java.entities.Character;
import org.alkemy.challenge.java.entities.Gender;
import org.alkemy.challenge.java.entities.Movie;

public class EntitiesUtil {
    
    public static final Character CHARACTER_ONE = new Character(1L, "imageOne", "nameOne", 1, 1.0, "historyOne", null);
    public static final Character CHARACTER_TWO = new Character(2L, "imageTwo", "nameTwo", 2, 2.0, "historyTwo", null);
    public static final Character CHARACTER_THREE = new Character(3L, "imageThree", "nameThree", 3, 3.0, "historyThree", null);
    
    public static final Gender GENDER_ONE = new Gender(1L, "nameOne", "imageOne", null);
    public static final Gender GENDER_TWO = new Gender(2L, "nameTwo", "imageTwo", null);
    public static final Gender GENDER_THREE = new Gender(3L, "nameThree", "imageThree", null);
    
    public static final Movie MOVIE_ONE = new Movie(1L, "imageOne", "titleOne", LocalDate.of(2001, 01, 01), 1, null, null);
    public static final Movie MOVIE_TWO = new Movie(2L, "imageTwo", "titleTwo", LocalDate.of(2002, 02, 02), 2, null, null);
    public static final Movie MOVIE_THREE = new Movie(3L, "imageThree", "titleThree", LocalDate.of(2003, 03, 03), 3, null, null);

}
