package org.alkemy.challenge.java.services;

import java.io.IOException;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import org.alkemy.challenge.java.DTOs.SendGridDTO;
import org.alkemy.challenge.java.configurations.SendGridConfig;
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
        SendGrid sg = new SendGrid(new SendGridConfig().getSendGridKey());

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("email");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }

    /*
    public Response sendEmail(SendGridDTO email) {
        Mail mail = new Mail(new Email("nestorhasin@gmail.com"),
            "Saludos, Challenge Alkemy",
            new Email(email.getTo()),
            new Content("text/plain", "Te quiero saludar y felicitar por registrarte"));
        mail.setReplyTo(new Email("nestorhasin@gmail.com"));
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("/email");
            request.setBody(mail.build());
            response = this.sendGrid.api(request);
    
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println(response.getClass());
        return response;
    }
    */

}
