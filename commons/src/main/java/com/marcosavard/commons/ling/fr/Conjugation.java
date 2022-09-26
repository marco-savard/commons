package com.marcosavard.commons.ling.fr;

import java.util.Arrays;

public abstract class Conjugation {
	private static final String VOWELS = "aeiou";
	
	private static final String[] PRONOUNS = new String[] { //
			"je", "tu", "il", "nous", "vous", "ils"
	};
	
	public abstract void conjugate(String infinitive);

	protected String getPronoun(String conjugated, int i) {
		String pronoun = PRONOUNS[i];
		boolean vowel = isVowel(conjugated.charAt(0));
		
		if (vowel && (i == 0)) { 
			pronoun = "j'"; 
		}
		
		return pronoun;
	}

	private boolean isVowel(char letter) {
		boolean vowel = (VOWELS.indexOf(letter) != -1);
		return vowel;
	}

	protected String conjugate(String radical, int pers) {
		String ending = getEnding(pers); 
		radical = changeRadical(radical, pers);
		String conjugated = radical + ending;
		return conjugated;
	}

	protected abstract String getEnding(int pers);

	protected String changeRadical(String radical, int pers) {
		String unchanged = radical;  
		return unchanged;
	}


} 
