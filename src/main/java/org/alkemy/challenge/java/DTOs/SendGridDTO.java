package org.alkemy.challenge.java.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SendGridDTO {
    
    private String to;

    private String subject;

    private String body;

}
