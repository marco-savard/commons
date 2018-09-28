package com.marcosavard.commons.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * A word dictionary. A dictionary contains a list of valid words, ordered
 * by their length, and then by the alphabetical order.  
 * 
 * @author Marco
 *
 */
public class Dictionary {
	private int nbWords = 0; 
	private Map<Integer, List<String>> wordsByLength = new HashMap<Integer, List<String>>();

	/**
	 * Add a word in the dictionary
	 * 
	 * @param word to be added
	 */
	public void addWord(String word) {
		int len = word.length(); 
		List<String> wordList = wordsByLength.get(len); 
		
		if (wordList == null) {
			wordList = new ArrayList<>(); 
			wordsByLength.put(len, wordList); 
		}
		
		wordList.add(word);
		nbWords++;
	}

	/**
	 * Tell if the word is in the dictionary
	 * 
	 * @param word to check if it is contained 
	 * @return true if word is contained
	 */
	public boolean contains(String word) {
		boolean contained = false;
		int len = word.length(); 
		List<String> words = wordsByLength.get(len);
		
		if (words != null) {
			contained = words.contains(word); 
		}
		
		return contained;
	}

	/**
	 * Return the number of words in the dictionary 
	 * @return the number of words in the dictionary 
	 */
	public int length() {
		return nbWords;
	}
	
	/**
	 * Find a list of words matching the wildcards
	 * 
	 * @param wildcards
	 * @return list of words matching the wildcards
	 */
	public List<String> findWordsByWildcards(String wildcards) {
		String regex = wildcards.replaceAll("\\?", ".");  
		regex =  regex.replaceAll("\\*", ".+"); 
		return findWordsByRegex(regex);
	}

	/**
	 * Find a list of words matching the regular expression
	 * 
	 * @param regex
	 * @return list of words matching the the regular expression
	 */
	public List<String> findWordsByRegex(String regex) {
		List<String> foundWords = new ArrayList<String>(); 
		Pattern pattern = Pattern.compile(regex);
		
		Iterator<String> iter = new WordIterator();
		while (iter.hasNext()) {
			String nextWord = iter.next(); 
			if (pattern.matcher(nextWord).matches()) {
				foundWords.add(nextWord); 
			}
		}
		
		return foundWords;
	}
	
	/**
	 * Find a list of words similar to a given misspelled word. 
	 * For instance, findSuggestions("achetter", 1) will return "acheter". 
	 * 
	 * @param misspelled word 
	 * @param maxSuggestions (5 by default)
	 * @return
	 */
	public List<String> findSuggestions(String misspelled, int maxSuggestions) {
		TreeSet<Integer> shorterDistances = new TreeSet<>();
		List<String> suggestions = new ArrayList<>();
		
		Iterator<String> iter = new WordIterator();
		while (iter.hasNext()) {
			String nextWord = iter.next(); 
			int distance = WordDistance.levenshteinDistance(misspelled, nextWord);
			
			if (shorterDistances.size() < maxSuggestions) {
				suggestions.add(0, nextWord); 
				shorterDistances.add(distance);  
			} else {
				if (distance <= shorterDistances.first().intValue()) {
					suggestions.add(0, nextWord); 
					shorterDistances.pollLast();
					shorterDistances.add(distance); 
				}
			}
			
			if (suggestions.size() > maxSuggestions) {
				suggestions = suggestions.subList(0, maxSuggestions); 
			}
		}

		return suggestions;
	}
	
	public List<String> findSuggestions(String mispelled) {
		return findSuggestions(mispelled, 5);
	}
	
	
	
	private class WordIterator implements Iterator<String> {
		private boolean hasNext = true;
		private Iterator<Integer> lengthIterator; 
		private Iterator<String> wordIterator; 
		private int currentLength = -1;
		
		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public String next() {
			if (lengthIterator == null) {
				lengthIterator = wordsByLength.keySet().iterator();
			}
			
			if (currentLength == -1) {
				currentLength = lengthIterator.next(); 
				List<String> wordList = wordsByLength.get(currentLength);
				wordIterator = wordList.iterator(); 
			}

			String nextWord = wordIterator.next(); 
			
			if (! wordIterator.hasNext()) {
				if (lengthIterator.hasNext()) {
					currentLength = lengthIterator.next(); 
					List<String> wordList = wordsByLength.get(currentLength);
					wordIterator = wordList.iterator(); 
				} else {
					hasNext = false;
				}
			}
			
			return nextWord;
		}
	}



}
