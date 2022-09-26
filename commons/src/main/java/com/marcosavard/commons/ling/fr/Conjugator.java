package com.marcosavard.commons.ling.fr;

import java.util.Arrays;

public class Conjugator {
	
	public static void conjugate(String infinitive) {
		Conjugation conjugation = findConjugationFor(infinitive); 
		conjugation.conjugate(infinitive);
	}
	
	private static Conjugation findConjugationFor(String infinitive) {
		int group = GroupFinder.findGroup(infinitive); 
		Conjugation conjugator = null; 
		
		if (group == 1) { 
			conjugator = ConjugationGroup1.findConjugationFor(infinitive); 
		} else if (group == 2) {  
			conjugator = ConjugationGroup2.findConjugationFor(infinitive); 
		} else if (group == 3) {  
			conjugator = ConjugationGroup3.findConjugationFor(infinitive); 
		} 
		
		return conjugator;
	}
}
