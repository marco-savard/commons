package com.marcosavard.awsmodule.sendmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

@RestController
public class SendMailController {
    private static final String USERNAME = "savard.marco@gmail.com";
    private static final String PASSWORD = "zuny ymzy eqrx wanc";
    private final AlmanachService fileService;
    private final EmailService emailService;

    @Autowired
    public SendMailController(AlmanachService fileService, GmailService emailService) {
        this.fileService = fileService;
        this.emailService = emailService;
        System.out.println("SendMailController");
    }

    @GetMapping("/sendmail")
    public String sendmail() {
        return "Request should be /sendmail/{recipient}";
    }

    @GetMapping("/sendmail/{recipient}")
    public String sendmailRecipient(@PathVariable("recipient") String recipient) {
        //settings
        String subject = "Jeu du jour";
        Locale display = Locale.FRENCH;
        LocalDate date = LocalDate.now().plusDays(0);
        String result;

        try {
            File file = fileService.generateFile(date, display);
            emailService.setCredential(USERNAME, PASSWORD);
            Message message = emailService.createMessage(subject, "Text");
            emailService.addFileToMessage(file, message);
            InternetAddress toAddress = new InternetAddress(recipient);
            emailService.sendMessage(message, toAddress);
            result = "Message sent to " + recipient;
        } catch (MessagingException | IOException e) {
            result = e.toString();
        }

        return result;
    }
}
