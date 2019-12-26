package com.marcosavard.commons.text;

import java.text.MessageFormat;
import java.util.List;

public class SyllableSplitterDemo {
	private static final String[] WORDS = new String[] {
		"agneau",
		"art",
		"autre",
		"belle",
		"biche",
		"bleue",
		"bord",
		"compter",
		"eau",
		"ecole",
		"fortier",
		"invisible",
		"loto",
		"marcher",
		"montagne",
		"pomme",
		"telephone"
	}; 

	public static void main(String[] args) {
		SyllableSplitter splitter = new SyllableSplitter();
		
		for (String word : WORDS) {
			List<String> syllables = splitter.split(word);
			String splitWord = String.join("-", syllables); 
			String msg = MessageFormat.format("  {0} -> {1}", word, splitWord); 
			System.out.println(msg);
		}
		

	}

}
