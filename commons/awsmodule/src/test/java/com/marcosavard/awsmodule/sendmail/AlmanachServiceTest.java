package com.marcosavard.awsmodule.sendmail;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

public class AlmanachServiceTest {

    public static void main(String[] args) {
        //setting
        Locale display = Locale.FRENCH;
        LocalDate date = LocalDate.now().plusDays(0);

        try {
            AlmanachService almanachService = new AlmanachService();
            File file = almanachService.generateFile(date, display);
            System.out.println("File generated : " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
