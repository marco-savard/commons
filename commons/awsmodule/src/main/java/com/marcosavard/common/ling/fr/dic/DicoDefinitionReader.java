package com.marcosavard.common.ling.fr.dic;

import com.marcosavard.common.debug.Console;
import com.marcosavard.common.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DicoDefinitionReader  {
    private BufferedReader reader;
    private List<Word> allWords = null;
    private Pattern LATIN_LETTERS = Pattern.compile("[-' \\p{IsLatin}]*");

    public DicoDefinitionReader() {
        reader = new BufferedReader(new ResourceReader(DicoDefinitionReader.class, "dico-definition.csv", StandardCharsets.UTF_8));
    }

    public List<Word> readAll() throws IOException {
        if (allWords == null) {
            Console.println("reading dictionary..");
            allWords = new ArrayList<>();
            String line = reader.readLine(); //header

            do {
                line = reader.readLine();

                if (line != null) {
                    readLine(allWords, line);
                }
            } while (line != null);

            Console.println("sorting words..");
            Collections.sort(allWords, new WordComparator());
        }

        return allWords;
    }

    private void readLine(List<Word> words, String line) {
        int idx = line.indexOf(",");
        String text = line.substring(0, idx);
        Matcher matcher = LATIN_LETTERS.matcher(text);

        if (matcher.matches()) { //contains letter
            if (! text.contains(" ") && ! text.contains("-") && ! text.contains("'")) {
                if (! text.matches(".*\\d.*")) { //does not contain digits
                    List<String> defs = toDefs(line.substring(idx+1));
                    Word word = Word.of(text, Word.Gender.EPICENE);
                    word.addDefinitions(defs);
                    words.add(word);
                }
            }
        }
    }

    private List<String> toDefs(String text) {
        int idx1 = text.indexOf('[');
        int idx2 = text.lastIndexOf(']');
        text = text.substring(idx1+1, idx2);
        text = text.replace("\"\"", "\"");
        StringBuilder builder = new StringBuilder();
        List<String> defs = new ArrayList<>();
        boolean insideQuotes = false, insideDoubleQuotes = false;

        for (int i=0; i<text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\"') {
                insideDoubleQuotes = ! insideDoubleQuotes;
            } else if (ch == '\'' && ! insideDoubleQuotes) {
                insideQuotes = ! insideQuotes;
            } else if (ch == ','  && ! (insideDoubleQuotes || insideQuotes)) {
                defs.add(toDef(builder.toString().trim()));
                builder.setLength(0);
                continue;
            }

            builder.append(ch);
        }

        defs.add(toDef(builder.toString().trim()));

       return defs;
    }

    private String toDef(String text) {
      if (text.startsWith("\"") && text.endsWith("\"")) {
          text = text.substring(1, text.length() - 1);
      }

        if (text.startsWith("\'") && text.endsWith("\'")) {
            text = text.substring(1, text.length() - 1);
        }

      return text;
    }

    public List<Word> pickWords(int count, Random random) throws IOException {
        List<Word> pickedWords = new ArrayList<>();

        for (int i=0; i<count; i++) {
            Word pickedWord = pickWord(random);
            pickedWords.add(pickedWord);
        }

        return pickedWords;
    }

    private Word pickWord(Random random) throws IOException {
        List<Word> allWords = readAll();
        Word pickedWord = null;

        do {
            Word word = allWords.get(random.nextInt(allWords.size()));
            List<String> definitions = word.getDefinitions();
            String definition = definitions.get(random.nextInt(definitions.size())).toLowerCase();
            boolean pick = ! definition.contains(word.getText());
            pick = pick && ! definition.startsWith("(autre sens");
            pick = pick && ! definition.startsWith("première");
            pick = pick && ! definition.startsWith("deuxième");
            pick = pick && ! definition.startsWith("troisième");
            pick = pick && ! definition.startsWith("participe");
            pick = pick && ! definition.startsWith("du verbe");
            pick = pick && ! definition.startsWith("féminin");
            pick = pick && ! definition.startsWith("masculin");
            pick = pick && ! definition.startsWith("pluriel");
            pick = pick && ! definition.startsWith("habitant");
            pick = pick && ! definition.startsWith("commune");
            pick = pick && (definition.length() < 100);

            if (pick) {
                pickedWord = word;
            }

        } while (pickedWord == null);

        return pickedWord;
    }

    private static class WordComparator implements Comparator<Word>  {
        @Override
        public int compare(Word w1, Word w2) {
            return w1.getText().compareTo(w2.getText());
        }
    }
}
