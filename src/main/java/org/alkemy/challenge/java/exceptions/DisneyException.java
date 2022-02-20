package org.alkemy.challenge.java.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DisneyException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public DisneyException(HttpStatus status, String message, String newMessage){
        setStatus(status);
        setMessage(message);
        setMessage(newMessage);
    }
    
}
