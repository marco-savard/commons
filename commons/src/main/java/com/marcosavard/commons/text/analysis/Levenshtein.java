package com.marcosavard.commons.text.analysis;

import java.util.ArrayList;
import java.util.List;

import com.marcosavard.commons.lang.IndexSafe;

//https://rosettacode.org/wiki/Levenshtein_distance#Java
public class Levenshtein {
	
	public static List<List<Integer>> distanceOf(List<String> list1, List<String> list2) {
		int count = Math.max(list1.size(), list2.size()); 
		List<List<Integer>> distances = new ArrayList<>(); 
		
		for (int i=0; i<count; i++) {
			String text1 = IndexSafe.of(list1).get(i);
			String text2 = IndexSafe.of(list2).get(i);
			List<Integer> distance = distanceOf(text1, text2);
			distances.add(distance); 
	    }
	
		return distances;
	}
	
	public static List<Integer> distanceOf(String text1, String text2) {
		List<Integer> distances = new ArrayList<>(); 
		String[] words1 = (text1 == null) ? new String[] {} : text1.split(" "); 
		String[] words2 = (text2 == null) ? new String[] {} : text2.split(" "); 
		int count = Math.max(words1.length, words2.length); 
		
		for (int i=0; i<count; i++) {
			String word1 = IndexSafe.of(words1).get(i);
			String word2 = IndexSafe.of(words2).get(i);
			int distance = distanceOfWords(word1, word2);
			distances.add(distance);
		}
		
		return distances;
	}

	public static int distanceOfWords(String a, String b) {
        a = (a == null) ? "" : a.toLowerCase();
        b = (b == null) ? "" : b.toLowerCase();
        
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        
        return costs[b.length()];
    }


 

}
