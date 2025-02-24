package com.marcosavard.commons.quiz.fr.citations;

import com.marcosavard.commons.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class CitationReader extends ResourceReader {

    public CitationReader(String resource, Charset charset) {
        super(CitationReader.class, resource, charset);
    }

    public Map<String, List<String>> readAll() {
        Map<String, List<String>> citationsByAuthor = new LinkedHashMap<>();
        BufferedReader bf = new BufferedReader(this);
        String line, author = null;
        boolean isAuthor = true;

        try {
            do {
                line = bf.readLine();
                boolean empty = (line == null) || line.trim().isEmpty();

                if (isAuthor && !empty) {
                    author = readAuthor(citationsByAuthor, line.trim());
                    isAuthor = false;
                } else if (! empty) {
                    readCitation(citationsByAuthor, author, line.trim());
                } else {
                    isAuthor = true;
                }
            } while (line != null);
        } catch (IOException e) {
             throw  new RuntimeException(e);
        }


        return citationsByAuthor;
    }

    private String readAuthor(Map<String, List<String>> citationsByAuthor, String author) {
        if (! citationsByAuthor.containsKey(author)) {
            citationsByAuthor.put(author, new ArrayList<>());
        }

        return author;
    }

    private void readCitation(Map<String, List<String>> citationsByAuthor, String author, String citation) {
        List<String> citations = citationsByAuthor.get(author);
        citation = citation.replace("«", "");
        citation = citation.replace("»", "");
        citation = citation.replace("\"", "");
        citations.add(citation.trim());
    }



}
