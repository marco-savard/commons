package com.marcosavard.commons.ling.fr.dic;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.writer.ResourceWriter;
import com.marcosavard.commons.lang.NullSafe;
import com.marcosavard.commons.lang.StringUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FrenchDicoDemo {

    public static void main(String[] args) throws IOException {
        FrenchWordWithCategoryReader reader = new FrenchWordWithCategoryReader();
        List<Word> words =  reader.readAll();

        guessGender(words);

       // List<String> words = readFrenchWordFrequency();

       // printWords(words);
      //  printAnagrams(words);
    }

    private static void guessGender(List<Word> words) {
        List<Word> mistakes = new ArrayList<>();

        for (Word word : words) {
            boolean guess = guessGender(word);
            boolean result = word.isFeminine();

            if (guess != result) {
                mistakes.add(word);
            }
        }

        double percentMistakes = 100.0 * mistakes.size() / words.size();
        List<Word> examples = mistakes.subList(0, Math.min(400, mistakes.size()));
        Console.println("Error rate : {0}", Double.toString(percentMistakes));
        Console.println("Examples of error : {0}", examples);
    }

    private static boolean guessGender(Word word) {
        String text = word.getText();
        boolean feminine = false;

        if (text.startsWith("abaisse-")) {
            feminine = false;
        } else if (text.startsWith("abat-")) {
            feminine = false;
        } else if (text.startsWith("abri-")) {
            feminine = false;
        } else if (text.startsWith("abris-")) {
            feminine = false;
        } else if (text.startsWith("accroche-")) {
            feminine = false;
        } else if (text.startsWith("allume-")) {
            feminine = false;
        } else if (text.startsWith("amuse-")) {
            feminine = false;
        } else if (text.startsWith("appuie-")) {
            feminine = false;
        } else if (text.startsWith("arrache-")) {
            feminine = false;
        } else if (text.endsWith("gramme")) {
            feminine = false;
        } else if (text.endsWith("glyphe")) {
            feminine = false;
        } else if (text.endsWith("graphe")) {
            feminine = false;
        } else if (text.endsWith("morphe")) {
            feminine = false;
        } else if (text.endsWith("plaste")) {
            feminine = false;
        } else if (text.endsWith("rithme")) {
            feminine = false;
        } else if (text.endsWith("thèque")) {
            feminine = true;
        } else if (text.endsWith("angle")) {
            feminine = false;
        } else if (text.endsWith("clone")) {
            feminine = false;
        } else if (text.endsWith("drome")) {
            feminine = false;
        } else if (text.endsWith("lithe")) {
            feminine = false;
        } else if (text.endsWith("ecte")) {
            feminine = false;
        } else if (text.endsWith("logue")) {
            feminine = false;
        } else if (text.endsWith("mètre")) {
            feminine = false;
        } else if (text.endsWith("naute")) {
            feminine = false;
        } else if (text.endsWith("pathe")) {
            feminine = false;
        } else if (text.endsWith("peute")) {
            feminine = false;
       } else if (text.endsWith("phale")) {
            feminine = false;
        } else if (text.endsWith("phène")) {
            feminine = false;
        } else if (text.endsWith("phile")) {
            feminine = false;
        } else if (text.endsWith("phobe")) {
            feminine = false;
        } else if (text.endsWith("phone")) {
            feminine = false;
        } else if (text.endsWith("scope")) {
            feminine = false;
        } else if (text.endsWith("sophe")) {
            feminine = false;
        } else if (text.endsWith("taire")) {
            feminine = false;
        } else if (text.endsWith("trope")) {
            feminine = false;
        } else if (text.endsWith("aure")) {
            feminine = false;
        } else if (text.endsWith("cène")) {
            feminine = false;
        } else if (text.endsWith("cète")) {
            feminine = false;
        } else if (text.endsWith("cule")) {
            feminine = false;
        } else if (text.endsWith("dyne")) {
            feminine = false;
        } else if (text.endsWith("dyte")) {
            feminine = false;
        } else if (text.endsWith("gène")) {
            feminine = false;
        } else if (text.endsWith("gyne")) {
            feminine = false;
        } else if (text.endsWith("isme")) {
            feminine = false;
        } else if (text.endsWith("ïsme")) {
            feminine = false;
        } else if (text.endsWith("iste")) {
            feminine = false;
        } else if (text.endsWith("nome")) {
            feminine = false;
        } else if (text.endsWith("nyme")) {
            feminine = false;
        } else if (text.endsWith("oïde")) {
            feminine = false;
        } else if (text.endsWith("pode")) {
            feminine = false;
        } else if (text.endsWith("side")) {
            feminine = false;
        } else if (text.endsWith("sion")) {
            feminine = true;
        } else if (text.endsWith("tion")) {
            feminine = true;
        } else if (text.endsWith("tome")) {
            feminine = false;
        } else if (text.endsWith("type")) {
            feminine = false;
        } else if (text.endsWith("vion")) {
            feminine = true;
        } else if (text.endsWith("xion")) {
            feminine = true;
        } else if (text.endsWith("ysme")) {
            feminine = false;
        } else if (text.endsWith("yste")) {
            feminine = false;
        } else if (text.endsWith("zyme")) {
            feminine = false;
        } else if (text.endsWith("age")) {
            feminine = false;
        } else if (text.endsWith("ate")) {
            feminine = false;
        } else if (text.endsWith("aze")) {
            feminine = false;
        } else if (text.endsWith("ège")) {
            feminine = false;
        } else if (text.endsWith("ème")) {
            feminine = false;
        } else if (text.endsWith("ide")) {
            feminine = false;
        } else if (text.endsWith("îme")) {
            feminine = false;
        } else if (text.endsWith("ixe")) {
            feminine = false;
        } else if (text.endsWith("ome")) {
            feminine = false;
        } else if (text.endsWith("ôme")) {
            feminine = false;
        } else if (text.endsWith("son")) {
            feminine = true;
        } else if (text.endsWith("yle")) {
            feminine = false;
        } else if (text.endsWith("yte")) {
            feminine = false;
        } else if (text.endsWith("té")) {
            feminine = true;
        } else if (text.endsWith("e")) {
            feminine = true;
        }

        return feminine;
    }

    private static boolean isFeminine(Word word) {
        boolean feminine = word.isFeminine();

        return feminine;
    }

    private static List<String> readFrenchWordFrequency() throws IOException {
        //FrenchWordFrequencyReader reader = new FrenchWordFrequencyReader();
        FrenchDictionaryReader reader = new FrenchDictionaryReader();
        List<String> words = reader.readAll();
        return words;
    }

    private static void printWords(List<String> words) throws IOException {

        for (String word : words) {
            Console.println(word);
        }

        Console.println("Total : {0}", Integer.toString(words.size()));
    }

    private static void printAnagrams(List<String> words) throws IOException {
        Map<Integer, List<String[]>> anagramsByLength = findAnagramsByLength(words);
        List<String> anagrams = toAnagrams(anagramsByLength);

        for (String word : anagrams) {
            Console.println(word);
        }

        Console.println("Total : {0}", Integer.toString(anagrams.size()));

                /*
        Map<Integer, List<String[]>> anagrams = getAnagrams(wordBySignature);
        writeAnagrams(anagrams);

       Random random = new Random();
       printAnagrams(anagrams, random);
         */
    }


    private static Map<Integer, List<String[]>> findAnagramsByLength(List<String> words) {
        Map<String, List<String>> wordBySignature = new HashMap<>();

        for (String word : words) {
            String sig = getSignature(word);
            addWord(wordBySignature, word, sig);
        }

        Map<Integer, List<String[]>> anagramsByLength = getAnagrams(wordBySignature);



        return anagramsByLength;
    }

    private static List<String> toAnagrams(Map<Integer, List<String[]>> anagramsByLength) {
        List<String> anagrams = new ArrayList<>();

        for (List<String[]> values : anagramsByLength.values()) {
           for (String[] value : values) {
               anagrams.add(value[0]);
           }
        }

        return anagrams;
    }




    private static void writeAnagrams(Map<Integer, List<String[]>> anagrams) throws IOException {
        Class claz = FrenchDicoDemo.class;
        ResourceWriter rw = new ResourceWriter("commons/src/main/resources", claz,"anagrams.txt", StandardCharsets.UTF_8);
        BufferedWriter writer = new BufferedWriter(rw);
        writeAnagrams(writer, anagrams);
        writer.close();
    }

    private static void writeAnagrams(BufferedWriter writer, Map<Integer, List<String[]>> anagramByLength) throws IOException {
        for (int i=2; i<= 20; i++) {
            List<String[]> anagramList = (List<String[]>)NullSafe.of(anagramByLength.get(i));

            for (int j=0; j<anagramList.size(); j++) {
                String[] anagrams = anagramList.get(j);
                writer.write(String.join(", ", anagrams));
                writer.newLine();
            }
        }
    }

    private static void printAnagrams(Map<Integer, List<String[]>> anagramByLength, Random random) {

        for (int i=2; i<= 10; i++) {
            List<String[]> anagramList = anagramByLength.get(i);
            String[] anagrams = anagramList.get(random.nextInt(anagramList.size()));
            Console.println(anagrams);
        }
    }

    private static void addWord(Map<String, List<String>> wordBySignature, String word, String sig) {
        List<String> words = wordBySignature.get(sig);

        if (words == null) {
            words = new ArrayList<>();
            wordBySignature.put(sig, words);
        }

        if (! contains(words, word)) {
            words.add(word);
        }
    }

    private static boolean contains(List<String> words, String word) {
        boolean contained = false;

        for (String w : words) {
            contained = contained || StringUtil.stripAccents(w).equals(StringUtil.stripAccents(word));
        }

        return contained;
    }

    private static String getSignature(String line) {
        char[] chars = StringUtil.stripAccents(line).toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    private static Map<Integer, List<String[]>> getAnagrams(Map<String, List<String>> wordBySignature) {
        Map<Integer, List<String[]>> anagramMap = new HashMap<>();

        for (String sig : wordBySignature.keySet()) {
            List<String> words = wordBySignature.get(sig);
            if (words.size() > 1) {
                int len = words.get(0).length();
                List<String[]> anagrams = anagramMap.get(len);

                if (anagrams == null) {
                    anagrams = new ArrayList<>();
                    anagramMap.put(len, anagrams);
                }

                anagrams.add(words.toArray(new String[0]));
            }
        }
        return anagramMap;
    }

    static final class WordComparator implements Comparator<String>, Serializable {

        @Override
        public int compare(String s1, String s2) {
            return StringUtil.stripAccents(s1).compareTo(StringUtil.stripAccents(s2));
        }
    }
}
