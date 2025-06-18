package com.marcosavard.awsmodule.sendmail;

import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

@Service
public class GmailService implements EmailService {
    private final Properties properties;
    private Session session;
    private InternetAddress fromAddress;

    public GmailService() {
        properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        System.out.println("Set properties");
    }

    @Override
    public void setCredential(String username, String password) throws AddressException {
        Authenticator authenticator = new GmailService.PasswordAuthenticator(username, password);
        session = Session.getInstance(properties, authenticator);
        fromAddress = new InternetAddress(username);
    }

    @Override
    public Message createMessage(String subject, String text) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(fromAddress);
        message.setSubject(subject);
        message.setText(text);

        return message;
    }

    @Override
    public void addFileToMessage(File file, Message message) throws MessagingException {
        //text part
        Multipart multipart = new MimeMultipart();
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText("Ci-joint, les jeux du jour en pièce jointe");
        multipart.addBodyPart(textPart);

        //file part
        MimeBodyPart filePart = new MimeBodyPart();
        DataSource source = new FileDataSource(file);
        filePart.setDataHandler(new DataHandler(source));
        filePart.setFileName(file.getName());
        multipart.addBodyPart(filePart);

        message.setContent(multipart);
    }


    @Override
    public void sendMessage(Message message, InternetAddress toAddress) throws MessagingException {
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[] {toAddress});
        Transport.send(message);
    }

    private static class PasswordAuthenticator extends Authenticator {
        final String username;
        final String password;

        public PasswordAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }

}
