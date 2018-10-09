package com.marcosavard.commons.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class that computes the distance (similarity) between two words. 
 * 
 * @author Marco
 *
 */
public class WordDistance {
	
	/**
	 * Compute the Levenshtein distance between two words. 
	 * 
	 * @param word1 first word
	 * @param word2 second word
	 * @return a number representing the distance between two words. 
	 */
	//https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
	public static int levenshteinDistance (CharSequence word1, CharSequence word2) {                          
	    int len0 = word1.length() + 1;                                                     
	    int len1 = word2.length() + 1;                                                     
	                                                                                    
	    // the array of distances                                                       
	    int[] cost = new int[len0];                                                     
	    int[] newcost = new int[len0];                                                  
	                                                                                    
	    // initial cost of skipping prefix in String s0                                 
	    for (int i = 0; i < len0; i++) cost[i] = i;                                     
	                                                                                    
	    // dynamically computing the array of distances                                  
	                                                                                    
	    // transformation cost for each letter in s1                                    
	    for (int j = 1; j < len1; j++) {                                                
	        // initial cost of skipping prefix in String s1                             
	        newcost[0] = j;                                                             
	                                                                                    
	        // transformation cost for each letter in s0                                
	        for(int i = 1; i < len0; i++) {                                             
	            // matching current letters in both strings                             
	            int match = (word1.charAt(i - 1) == word2.charAt(j - 1)) ? 0 : 1;             
	                                                                                    
	            // computing cost for each transformation                               
	            int cost_replace = cost[i - 1] + match;                                 
	            int cost_insert  = cost[i] + 1;                                         
	            int cost_delete  = newcost[i - 1] + 1;                                  
	                                                                                    
	            // keep minimum cost                                                    
	            newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
	        }                                                                           
	                                                                                    
	        // swap cost/newcost arrays                                                 
	        int[] swap = cost; cost = newcost; newcost = swap;                          
	    }                                                                               
	                                                                                    
	    // the distance is the cost for transforming all letters in both strings        
	    return cost[len0 - 1];                                                          
	}
	
	/**
	 * From of list of candidate words, return the candidate that is the nearest 
	 * from the given word. The nearest word is the word whose position in a dictionary
	 * is the closest to a given word, which is different from the Levenshtein distance. 
	 * For instance, "best" and "test" are very similar (one letter differs, that gives
	 * short Levenshtein distance), but they are not near each other ("best" has its
	 * entry in the "B" section of the dictionary, and "test" in the "T" one). 
	 * 
	 * means the word whose entry in the list
	 * is the close
	 * 
	 * @param word a given word
	 * @param wordList a list of candidate words
	 * @return the nearest word in the wordList
	 */
	public static String findNearestString(String word, List<String> wordList) {
		List<String> candidates = wordList; 
        String bestCandidate = wordList.get(0); 
        int len = word.length(); 
         
        for (int i=0; i<len; i++) {
            bestCandidate = candidates.get(0); 
            List<String> bestCandidates = findCandidates(word, candidates, i); 
             
            if (bestCandidates.size() == 1) {
                bestCandidate = bestCandidates.get(0); 
                break;
            } else if (bestCandidates.size() > 1) {
                candidates = bestCandidates; 
            }
        }
 
        return bestCandidate;
	}
	
	public static String findNearestString(String code, String[] words) {
        return findNearestString(code, new ArrayList<>(Arrays.asList(words))); 
    }

	
	//
	// private methods
	// 
	private static  List<String> findCandidates(String code, List<String> wordList, int idx) {
        List<String> candidates = new ArrayList<>(); 
        int bestScore = Integer.MAX_VALUE; 
         
        for (String word : wordList) {
            int score = computeScore(code, word, idx); 
             
            if (score < bestScore) {
                bestScore = score; 
                candidates.clear();
                candidates.add(word); 
            } else if (score == bestScore) {
                candidates.add(word); 
            }
        }
         
        return candidates;
    }
	
	 private static int computeScore(String code, String word, int idx) {
	        char c1 = (code.length() == idx) ? ' ' : code.charAt(idx); 
	        char c2 = (word.length() == idx) ? ' ' : word.charAt(idx); 
	        return Math.abs(c2-c1);
	    }

}
