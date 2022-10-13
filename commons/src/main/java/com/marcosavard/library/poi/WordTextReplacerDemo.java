package com.marcosavard.library.poi;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.FileSystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class WordTextReplacerDemo {

  public static void main(String[] args) throws Exception {
    // read source
    String resourceName = "Mise à jour du questionnaire.docx";
    InputStream input = WordTextReplacerDemo.class.getResourceAsStream(resourceName);

    // create target
    File docFolder = FileSystem.getUserDocumentFolder();
    File wordFolder = new File(docFolder, "Documents Word");
    String filename = "Mise à jour du questionnaire";
    File dest = new File(wordFolder, MessageFormat.format("{0}.v2.docx", filename));

    if (dest.exists()) {
      Files.delete(dest.toPath());
    }

    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH);
    String dateToday = formatter.format(today);

    OutputStream output = new FileOutputStream(dest);
    WordTextReplacer replacer = new WordTextReplacer(input, output);
    replacer.addReplacement("enfant.nom", "Savard");
    replacer.addReplacement("enfant.prenom", "Sarah-Maude");
    replacer.addReplacement("date.naissance", "22 Septembre 2011");
    replacer.addReplacement("code.ramq", "SAVS 1109 2201");
    replacer.addReplacement("enseignant.nom", "Prof Untel");
    replacer.addReplacement("date.today", dateToday);
    replacer.replace();
    Console.println("File {0} generated", dest.getPath());
  }
}
