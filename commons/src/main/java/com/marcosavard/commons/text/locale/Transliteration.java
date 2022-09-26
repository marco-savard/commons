package com.marcosavard.commons.text.locale;

import java.lang.Character.UnicodeScript;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Transliteration {
	
	public static boolean isSupported(UnicodeScript script) {
		boolean supported = script.equals(UnicodeScript.LATIN); 
		Map<UnicodeScript, Transliteration> transliterationByScript = getTransliterationByScript();
		supported = supported || transliterationByScript.containsKey(script); 
		return supported;
	}
	
	public static List<String> translate(List<String> words) {
		List<String> translation = new ArrayList<>();
		
		for (String word : words) { 
			String translated = translate(word);
			translation.add(translated);
		}
		
		return translation;
	}
	
	public static String translate(String word) {
	    StringBuilder builder = new StringBuilder();
	    for (int i = 0; i < word.length(); i++) {
	    	char letter = word.charAt(i); 
	    	String transcoded = translateLetter(letter);
	    	
	    	if (transcoded == null) { 
	    		transcoded = "?"; 
	    	}
	    	
	    	builder.append(transcoded);
	    }
	    return builder.toString();
	}
	
	
	private static String translateLetter(char letter) {
		Map<UnicodeScript, Transliteration> transliterationByScript = getTransliterationByScript();
		UnicodeScript script = UnicodeScript.of(letter);
		Transliteration transliteration = transliterationByScript.get(script); 
		String translated = (transliteration == null) ? Character.toString(letter) : transliteration.translate(letter); 
		return translated;
	}


	private static Map<UnicodeScript, Transliteration> transliterationByScript = null; 
	
	private static Map<UnicodeScript, Transliteration> getTransliterationByScript() {
		if (transliterationByScript == null) { 
			transliterationByScript = new HashMap<>(); 
			transliterationByScript.put(UnicodeScript.CYRILLIC, new CyrillicTransliteration()); 
			transliterationByScript.put(UnicodeScript.GREEK, new GreekTransliteration()); 
		}
		
		return transliterationByScript;
	}

	protected abstract String translate(char letter);



	
	public static class CyrillicTransliteration extends Transliteration { 
	    char[] abcCyr =   {' ','а','б','в','г','д','е','ё', 'ә', 'ж', 'з','и','й','ї', 'к','қ', 'л','м','н','о','п','р','с','т','у','ф','х', 'ц','ч', 'ҷ',  'ҹ',  'ш', 'щ',  'ъ','ы','ь','э', 'ю','я', 'ј', 'ӕ',  'А','Б','В','Г','Д','Е','Ё', 'Ж','З','И','Й','К','Қ', 'Л','М','Н','О','П','Р','С','Т','У','Ў', 'Ф','Х', 'Ц', 'Ч', 'Ш', 'Щ',  'Ъ','Ы','Ь','Э','Ю', 'Я', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		String[] abcLat = {" ","a","b","v","g","d","e","e", "e", "zh","z","i","y","ï", "k","kh","l","m","n","o","p","r","s","t","u","f","h","ts","ch","dj", "ch", "sh","sch", "","i", "","e","ju","ja","j", "ae", "A","B","V","G","D","E","E","Zh","Z","I","Y","K","Kh","L","M","N","O","P","R","S","T","U","Ou","F","H","Ts", "Ch","Sh","Sch", "","I", "","E","Ju","Ja","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		  
		@Override
		protected String translate(char letter) {
			String transcoded = null;
			
			for (int x = 0; x < abcCyr.length; x++ ) {
	            if (letter == abcCyr[x]) {
	            	transcoded = abcLat[x];
	                break;
	            }
	        }
			return transcoded;
		}
	}

	
	public static class GreekTransliteration extends Transliteration { 
		char[] abcCyr =   {' ','α','ά','β','γ','δ','ε','έ','ζ','ι','ί','η', 'ή', 'θ',  'κ','λ','μ','ν','ξ',  'ο','π','ρ','σ','ς','τ','υ','ύ', 'φ', 'χ',  'ψ','ω', 'Α','Β','Γ','Δ','Ε', 'Ζ', 'Θ',  'Ι','Η','Κ','Λ','Μ','Ν','Ξ',  'Ο','Π','Ρ','Σ','Τ','Υ','Φ', 'Χ', 'Ψ', 'Ω'};
		String[] abcLat = {" ","a","á","b","g","d","e","e","z","i","í","y", "y", "th", "k","l","m","n","ks", "o","p","r","s","s","t","u","ú", "ph","ch","ps","o", "A","B","G","D","E", "Z", "Th", "I","Y","K","L","M","N","Ks", "O","P","R","S","T","U","PH","Kh","Ps","O"};
		
		@Override
		protected String translate(char letter) {
			String transcoded = null;
			
			for (int x = 0; x < abcCyr.length; x++ ) {
	            if (letter == abcCyr[x]) {
	            	transcoded = abcLat[x];
	                break;
	            }
	        }
			return transcoded;
		}
	}




}
