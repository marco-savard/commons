package com.marcosavard.commons.text;

import java.text.MessageFormat;
import java.util.List;

public class DictionaryDemo {
	private static final String[] WORDS = new String[] {"arbre", "arbol", "gomme"};

	public static void main(String[] args) {
		try {
			//read the dictionary
			DictionaryReader dr = new DictionaryReader("fr.dic", "Cp1250"); 
			Dictionary dictionary = dr.readAll(); 
			
			runDemo(dictionary); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void runDemo(Dictionary dictionary) {
		//how many words in dictionary
		int len = dictionary.length(); 
		System.out.println(MessageFormat.format("Dictionary contains {0} words", len)); 
		
		//is word in dictionary?
		for (String word : WORDS) {
			boolean contained = dictionary.contains(word);
			String msg = MessageFormat.format("  {0} in dictionary : {1}", word, contained); 
			System.out.println(msg); 
		}
	
		//words by patterns
		List<String> words = dictionary.findWordsByWildcards("*queux"); 
		
		for (String word : words) {
			System.out.println(word); 
		}
		System.out.println();
		
		//find suggestions
		List<String> suggestions =  dictionary.findSuggestions("achetter"); 
		for (String suggestion : suggestions) {
			System.out.println(suggestion); 
		}
	}



}
