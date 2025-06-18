package com.marcosavard.awsmodule.sendmail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;

public interface EmailService {

    void setCredential(String username, String password) throws AddressException;
    Message createMessage(String subject, String text) throws MessagingException;
    void addFileToMessage(File file, Message message) throws MessagingException;
    void sendMessage(Message message, InternetAddress toAddress) throws MessagingException;

}
