package org.alkemy.challenge.java.services;

import java.io.IOException;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import org.alkemy.challenge.java.entities.User;
import org.alkemy.challenge.java.services.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {
    
    // VER LUEGO
    @Value("${templateId}")
    private String templateId;
    
    @Autowired
    private SendGrid sendGrid;
    
    @Override
    public void sendEmail(User user) {
        Mail mail = buildMail(user.getEmail());
        this.send(mail);
    }

    private Response send(Mail mail) {
        //SendGrid sendGrid = new SendGrid(System.getenv("sendGridKey"));
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("auth/register");
            request.setBody(mail.build());
            response = this.sendGrid.api(request);
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
        return response;
    }

    private Mail buildMail(String email) {
        Email from = new Email("nestorhasin@gmail.com");
        String subject = "Welcome to Alkemy Challenge!";
        Email to = new Email(email);
        Content content = new Content("text/plain", "Congratulation!");
        Mail mail = new Mail(from, subject, to, content);
        Personalization personalization = new Personalization();
        personalization.addTo(to);
        mail.addPersonalization(personalization);
        return mail;
    }

}
