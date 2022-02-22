package org.alkemy.challenge.java.services;

import java.io.IOException;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.alkemy.challenge.java.services.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {
    
    // VER LUEGO
    //@Value("${templateId}")
    //private String templateId;
    
    @Autowired
    private SendGrid sendGrid;

    private static final String FROM = "nestorhasin@gmail.com";

    private static final String SUBJECT = "Wellcome to Alkemy Challenge";

    private static final String BODY_PLAIN = "Congratulation!";

    private static final String BODY_HTML = "<strong>Congratulation!</strong>";

    @Override
    public void sendText(String to) {
        Response response = sendEmail(FROM, to, SUBJECT, new Content("text/plain", BODY_PLAIN));
        System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: " + response.getHeaders());
    }

    @Override
    public void sendHTML(String to) {
        Response response = sendEmail(FROM, to, SUBJECT, new Content("text/html", BODY_HTML));
        System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: " + response.getHeaders());
    }
    
    private Response sendEmail(String from, String to, String subject, Content content) {
        Mail mail = new Mail(new Email(from), subject, new Email(to), content);
        mail.setReplyTo(new Email("nestorhasin@gmail.com"));
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
                response = this.sendGrid.api(request);
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
        return response;
    }

}
