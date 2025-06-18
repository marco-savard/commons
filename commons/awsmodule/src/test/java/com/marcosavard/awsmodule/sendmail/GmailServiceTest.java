package com.marcosavard.awsmodule.sendmail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

public class GmailServiceTest {

    public static void main(String[] args) {
        EmailService emailService = new GmailService();
        final String username = "savard.marco@gmail.com";
        final String password = "zuny ymzy eqrx wanc";

        try {
            emailService.setCredential(username, password);
            Message message = emailService.createMessage("Subject", "Text");
            InternetAddress toAddress = new InternetAddress("marco.savard.cofomo@gmail.com");
            emailService.sendMessage(message, toAddress);
            System.out.println("Message sent");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
