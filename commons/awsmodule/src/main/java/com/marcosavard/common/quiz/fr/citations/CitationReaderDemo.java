package com.marcosavard.common.quiz.fr.citations;

import com.marcosavard.common.debug.Console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CitationReaderDemo {

    public static void main(String[] args) throws IOException {
        CitationReader reader = new CitationReader();
        Map<String, CitationReader.Author> authorByName = reader.readAll();
        List<String> authors = new ArrayList<>(authorByName.keySet());
        Random random = new Random();

        for (int i=0; i<3; i++) {
            printCitation(authorByName, authors, random);
        }
    }

    private static void printCitation(Map<String, CitationReader.Author> authorByName , List<String> authors, Random random) {
        int authorIdx = random.nextInt(authorByName.size());
        String author = authors.get(authorIdx);
        List<String> citations = authorByName.get(author).getCitations();
        int citationIdx = random.nextInt(citations.size());
        String citation = citations.get(citationIdx);

        Console.println(citation);
        Console.println("  - {0}", author);
        Console.println();
    }
}
