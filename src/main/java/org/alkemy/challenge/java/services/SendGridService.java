package org.alkemy.challenge.java.services;

import java.io.IOException;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import org.alkemy.challenge.java.DTOs.SendGridDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendGridService {
    
    @Value("${templateId}")
    private String templateId;
    
    @Autowired
    private SendGrid sendGrid;

    public String sendEmail(SendGridDTO sendGridDTO) throws IOException {
        Email from = new Email("nestorhasin@gmail.com");
        String subject = "Welcome";
        Email to = new Email("nestorhasin@gmail.com");
        Content content = new Content("text/plain", "This is a test email");
        Mail mail = new Mail(from, subject, to, content);
            mail.setReplyTo(new Email("nestorhasin@gmail.com"));
            mail.setTemplateId(templateId);
        Personalization personalization = new Personalization();
            personalization.addTo(to);
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("/email");
            request.setBody(mail.build());
            response = this.sendGrid.api(request);
            System.out.println("STATUS CODE: " + response.getStatusCode());
            System.out.println("BODY: " + response.getBody());
            System.out.println("HEADER: " + response.getHeaders());
            return response.getBody();
        } catch (IOException exception) {
            throw exception;
        }
    }

}
