package com.example.lbms;

import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service

public class EmailSenderService {

    public void sendEmail(String toEmail,String subject, String body)
    {
        Properties properties = new Properties();
        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols","TLSv1.2");

        //Your gmail
        String myAccountEmail = "iutlibrarysystem@gmail.com";
        //Your gmail password
        String password = "tbvtjlsbctycwifo";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
    }
}
