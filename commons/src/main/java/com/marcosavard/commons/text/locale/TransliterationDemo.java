package com.marcosavard.commons.text.locale;

import java.util.List;
import java.util.Locale;

public class TransliterationDemo {

	public static void main(String[] args) {
		Locale gr = new Locale("el"); 
		Glossary glossary = Glossary.of(gr); 
		List<String> translation = Transliteration.translate(glossary.getWords());
		
		for (String translated : translation) {
			System.out.println(translated);
		}
	}

}
