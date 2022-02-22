package org.alkemy.challenge.java.services.interfaces;

public interface IEmailService {
    void sendText(String to);
    void sendHTML(String to);
}
