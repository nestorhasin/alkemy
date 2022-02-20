package org.alkemy.challenge.java.configurations;

import com.sendgrid.SendGrid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
public class SendGridConfig {
    
    @Getter
    @Value("${sendGridKey}")
    private String sendGridKey;

    @Bean
    public SendGrid getSendGrid(){
        return new SendGrid(sendGridKey);
    }

}
