package edu.collin.db_conn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String name, long Id) throws Exception {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("User Registration Confirmation");

        message.setText(
                "Hello " + name + ",\n\n" +
                        "Click on the following link to confirm your registration:\n\n" +
                        "https://db-conn.onrender.com/users/confirm?name="+name+"&email="+to+"&id="+Id
        );

        mailSender.send(message);
    }
}

