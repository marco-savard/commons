package com.marcosavard.library;

import com.marcosavard.commons.io.FileSystem;

import java.io.*;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class WordTextReplacerDemo {

    public static void main(String args[]) throws Exception {
        File docFolder = FileSystem.getUserDocumentFolder();
        File wordFolder = new File(docFolder, "Documents Word");
        String filename = "Mise à jour du questionnaire";
        File src = new File(wordFolder, filename + ".docx");
        File dest = new File(wordFolder, MessageFormat.format("{0}.v2.docx", filename));

        if (dest.exists()) {
            dest.delete();
        }

        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dest);

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH);
        String dateToday = formatter.format(today);

        WordTextReplacer replacer = new WordTextReplacer(in, out);
        replacer.addReplacement("enfant.nom", "Savard");
        replacer.addReplacement("enfant.prenom", "Sarah-Maude");
        replacer.addReplacement("date.naissance", "22 Septembre 2011");
        replacer.addReplacement("code.ramq", "SAVS 1109 2201");
        replacer.addReplacement("enseignant.nom", "Prof Untel");
        replacer.addReplacement("date.today", dateToday);
        replacer.replace();
    }
}
