package com.marcosavard.commons.ling.fr.dic;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.writer.FormatWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public class GlossaryDemo {

    public static void main(String[] args) throws IOException {

        Glossary glossary = new Glossary();
        FormatWriter consoleFeedback = new ConsoleFeedback();
        glossary.addListener(consoleFeedback);
     //   glossary.addCategory(Glossary.Category.GREECE);
     //   glossary.addCategory(Glossary.Category.ROME);
      //  glossary.addCategory(Glossary.Category.GAUL);
        glossary.addCategory(Glossary.Category.VIKING);
        glossary.addCategory(Glossary.Category.MIDDLE_AGE);
        List<Word> wordList = glossary.getWordList();

        for (int i=0; i< wordList.size(); i++) {
            Console.println("  {0}) {1}", Integer.toString(i+1), wordList.get(i));
        }
    }

    private static class ConsoleFeedback extends FormatWriter {
        public ConsoleFeedback() {
            super(System.out);
        }
    }







}
