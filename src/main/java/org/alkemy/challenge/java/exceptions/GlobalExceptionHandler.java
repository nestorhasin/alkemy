package org.alkemy.challenge.java.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.alkemy.challenge.java.DTOs.response.ExceptionDetailsResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> manageResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){
        ExceptionDetailsResponse exceptionDetailsResponse = new ExceptionDetailsResponse(new Date(), resourceNotFoundException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionDetailsResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DisneyException.class)
    public ResponseEntity<?> manageDisneyException(DisneyException disneyException, WebRequest webRequest){
        ExceptionDetailsResponse exceptionDetailsResponse = new ExceptionDetailsResponse(new Date(), disneyException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionDetailsResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> manageException(Exception exception, WebRequest webRequest){
        ExceptionDetailsResponse exceptionDetailsResponse = new ExceptionDetailsResponse(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionDetailsResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach(error -> {
                String field = ((FieldError)error).getField();
                String message = error.getDefaultMessage();
                errors.put(field, message);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
