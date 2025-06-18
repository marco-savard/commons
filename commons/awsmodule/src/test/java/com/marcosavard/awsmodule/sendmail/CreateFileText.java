package com.marcosavard.awsmodule.sendmail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CreateFileText {

    public static void main(String[] args) {
        try {
            String resource = "file.txt";
            URL url = GmailService.class.getResource(resource);
            InputStream input = url.openStream();

            File tempFile = File.createTempFile("prefix-", ".txt");
            Path target = tempFile.toPath();
            Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("file created : " + tempFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
