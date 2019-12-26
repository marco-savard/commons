package com.marcosavard.commons.text;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SyllableSplitter {
	
	public List<String> split(String word) {
		word = stripAccents(word).toLowerCase(); 		
		List<String> syllables = new ArrayList<>();

		boolean firstVowelFound = false;
		int beginIndex = 0;
		
		for (int i=0; i<word.length(); i++) {
			char wc = word.charAt(i);
			boolean vowel = isVowel(wc); 
			boolean lastChar = (i == (word.length() -1));
			firstVowelFound = firstVowelFound |= vowel; 
			boolean shouldSplit = (i > 1) && (! vowel) && firstVowelFound && (!lastChar); 
			
			if (shouldSplit) {
				char nextChar =  word.charAt(i+1);
				boolean nextCharVowel = isVowel(nextChar); 
				
				if (nextCharVowel) {
					String syllable = word.substring(beginIndex, i);
					syllables.add(syllable); 
					beginIndex = i;
				} else if ((wc == 'c') && (nextChar == 'h')) { //do not split 'ch'
					String syllable = word.substring(beginIndex, i);
					syllables.add(syllable); 
					beginIndex = i++;
				} else if ((wc == 'g') && (nextChar == 'n')) { //do not split 'gn'
					String syllable = word.substring(beginIndex, i);
					syllables.add(syllable); 
					beginIndex = i++;
				} else if ((wc != 'l') && (nextChar == 'l')) { //do not split consonant + 'l', except 'll'
					String syllable = word.substring(beginIndex, i);
					syllables.add(syllable); 
					beginIndex = i++;
				} else if ((wc == 'p') && (nextChar == 'h')) { //do not split 'ph'
					String syllable = word.substring(beginIndex, i);
					syllables.add(syllable); 
					beginIndex = i++;
				} else if ((wc != 'r') && (nextChar == 'r')) { //do not split consonant + 'r', except 'rr'
					String syllable = word.substring(beginIndex, i);
					syllables.add(syllable); 
					beginIndex = i++;
				} else if ((wc == 't') && (nextChar == 'h')) {  //do not split 'th'
					String syllable = word.substring(beginIndex, i);
					syllables.add(syllable); 
					beginIndex = i++;
				}
			}
		}
		
		String lastSyllable = word.substring(beginIndex);
		syllables.add(lastSyllable); 
		
		return syllables;
	}
	
	/*
	//based on https://stackoverflow.com/questions/405161/detecting-syllables-in-a-word
	public String splitOld(String word) {
		StringBuffer splitWord = new StringBuffer(); 
		boolean lastWasVowel = false;
		int beginIndex = 0;
		
		for (int i=0; i<word.length(); i++) {
			char wc = word.charAt(i);
			boolean foundVowel = false; 
			
			for (char v : VOWELS) {
				//don't count diphthongs
                if (v == wc && lastWasVowel) {
                	foundVowel = true;
                    lastWasVowel = true;
                    
                    break;
                } else if (v == wc && !lastWasVowel) {
                	foundVowel = true;
                    lastWasVowel = true;
                    int endIndex = i+1;
                    String syllable = word.substring(beginIndex, endIndex);
                    beginIndex = endIndex;
                    System.out.println("    " + syllable); 
                    break;
                }   	
			}
			
			 //if full cycle and no vowel found, set lastWasVowel to false;
            if (!foundVowel) {
                lastWasVowel = false;
            }
		}
		
		return splitWord.toString();
	}*/
	
	public static boolean isVowel(char ch) {
		return "aeiou".indexOf(ch) != -1;
	}
	
	public static String stripAccents(String s) {
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s;
	}

}
