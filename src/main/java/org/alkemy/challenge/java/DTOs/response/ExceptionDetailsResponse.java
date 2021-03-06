package org.alkemy.challenge.java.DTOs.response;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExceptionDetailsResponse implements Serializable {
    
    private Date dateTime;
    private String message;
    private String details;

}
