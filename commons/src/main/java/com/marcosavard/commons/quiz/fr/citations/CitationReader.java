package com.marcosavard.commons.quiz.fr.citations;

import com.marcosavard.commons.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.*;

public class CitationReader {
    private ResourceReader resourceReader;

    public CitationReader() {
        resourceReader = new ResourceReader(CitationReader.class, "citations.par.auteurs.txt", StandardCharsets.UTF_8);
    }

    public Map<String, Author> readAll() {
        Map<String, Author> authorByName = new LinkedHashMap<>();
       // Map<String, List<String>> citationsByAuthor = new LinkedHashMap<>();

        BufferedReader bf = new BufferedReader(resourceReader);
        String line, author = null;
        boolean isAuthor = true;

        try {
            do {
                line = bf.readLine();
                boolean empty = (line == null) || line.trim().isEmpty();

                if (isAuthor && !empty) {
                    author = readAuthor(authorByName, line.trim());
                    isAuthor = false;
                } else if (! empty) {
                    readCitation(authorByName, author, line.trim());
                } else {
                    isAuthor = true;
                }
            } while (line != null);
        } catch (IOException e) {
             throw  new RuntimeException(e);
        }

        return authorByName;
    }

    private String readAuthor(Map<String, Author> authorByName, String authorLine) {
        int idx0 = authorLine.indexOf('(');
        int idx1 = authorLine.indexOf(')');
        String authorName = authorLine.substring(0, idx0-1).trim();
        Year[] years = parseYears(authorLine.substring(idx0+1, idx1));

        if (! authorByName.containsKey(authorName)) {
            Author author = new Author(authorName, years);
            authorByName.put(authorName, author);
        }

        return authorName;
    }

    private Year[] parseYears(String years) {
        years = years.replace('–', '-');
        Year[] yearArray = new Year[2];

        if (years.contains("+")) {
            int idx = years.indexOf('+');
            String s1 = years.substring(0, idx);
            String s2 = years.substring(idx+1);
            yearArray[0] = Year.of(Integer.parseInt(s1));
            yearArray[1] = Year.of(Integer.parseInt(s2));
        } else if (years.startsWith("-")) {
            int idx = years.lastIndexOf('-');
            String s1 = years.substring(0, idx);
            String s2 = years.substring(idx);
            yearArray[0] = Year.of(Integer.parseInt(s1));
            yearArray[1] = Year.of(Integer.parseInt(s2));
        } else if (years.endsWith("-")) {
            int idx = years.lastIndexOf('-');
            String s1 = years.substring(0, idx);
            yearArray[0] = Year.of(Integer.parseInt(s1));
        } else {
            int idx = years.indexOf('-');
            String s1 = years.substring(0, idx);
            String s2 = years.substring(idx+1);
            yearArray[0] = Year.of(Integer.parseInt(s1));
            yearArray[1] = Year.of(Integer.parseInt(s2));
        }

        return yearArray;
    }

    private void readCitation(Map<String, Author> authorByName, String author, String citation) {
        List<String> citations = authorByName.get(author).getCitations();
        citation = citation.replace("«", "");
        citation = citation.replace("»", "");
        citation = citation.replace("\"", "");
        citations.add(citation.trim());
    }

    public static class Author {
        String name;
        Year[] years = new Year[2];
        List<String> citations = new ArrayList<>();

        public Author(String authorName, Year[] years) {
            this.name = authorName;
            this.years[0] = years[0];
            this.years[1] = years[1];
        }

        public List<String> getCitations() {
            return citations;
        }

        public Year[] getYears() {
            return years;
        }

        public String getName() {
            return name;
        }
    }


}
