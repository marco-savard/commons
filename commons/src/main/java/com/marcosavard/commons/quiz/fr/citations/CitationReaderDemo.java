package com.marcosavard.commons.quiz.fr.citations;

import com.marcosavard.commons.debug.Console;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CitationReaderDemo {

    public static void main(String[] args) throws IOException {
        CitationReader reader = new CitationReader("citations.par.auteurs.txt", StandardCharsets.UTF_8);
        Map<String, List<String>> citationsByAuthor = reader.readAll();
        List<String> authors = new ArrayList<>(citationsByAuthor.keySet());
        Random random = new Random();

        for (int i=0; i<3; i++) {
            printCitation(citationsByAuthor, authors, random);
        }
    }

    private static void printCitation(Map<String, List<String>> citationsByAuthor, List<String> authors, Random random) {
        int authorIdx = random.nextInt(citationsByAuthor.size());
        String author = authors.get(authorIdx);
        List<String> citations = citationsByAuthor.get(author);
        int citationIdx = random.nextInt(citations.size());
        String citation = citationsByAuthor.get(author).get(citationIdx);

        Console.println(citation);
        Console.println("  - {0}", author);
        Console.println();
    }
}
