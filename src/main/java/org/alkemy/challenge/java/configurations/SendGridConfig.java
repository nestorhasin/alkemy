package org.alkemy.challenge.java.configurations;

import com.sendgrid.SendGrid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@PropertySource("classpath:application.properties")
public class SendGridConfig {
    
    @Getter
    @Value("${spring.sendgrid.api-key}")
    private String sendGridKey;

    @Bean
    public SendGrid getSendGrid(){
        return new SendGrid(sendGridKey);
    }

}
