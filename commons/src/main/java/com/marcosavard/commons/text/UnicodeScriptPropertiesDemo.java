package com.marcosavard.commons.text;

import com.marcosavard.commons.lang.IndexSafe;
import com.marcosavard.commons.lang.NullSafe;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UnicodeScriptPropertiesDemo {

	public static void main(String[] args) {
		Locale displayLocale = Locale.FRENCH;
		printScriptProperties(displayLocale); 
		//printUnicodeScriptRange();
	}

	private static void printScriptProperties(Locale displayLocale) {
		UnicodeScriptProperties scriptProperties = new UnicodeScriptProperties(); 
		
		for (int i=1; i<=32; i++) {
			Character.UnicodeScript script = Character.UnicodeScript.values()[i];
			String displayName = scriptProperties.getDisplayName(script, displayLocale);
			String nativeName = scriptProperties.getNativeName(script);
			byte directionality = scriptProperties.getDirectionality(script);
			List<int[]> ranges = scriptProperties.getScriptRanges(script);
			
			String joined = String.join(", ", displayName, nativeName, Byte.toString(directionality), toString(ranges)); 
			System.out.println(script.name() + " : " + joined);
		}
	}
	
	private static String toString(List<int[]> ranges) {
		List<String> items = new ArrayList<>();
		
		for (int[] range : ranges) {
			items.add(toString(range));
		}
		
		String str = "[" + String.join(", ", items) + "]"; 
		return str;
	}

	private static String toString(int[] range) {
		int start = range[0]; 
		int end = range[range.length-1];
		String span = (start == end) ? Character.toString(start): Character.toString(start) + ".." + Character.toString(end); 
		String str = "(" + span + ")"; 
		return str;
	}

	private static void printUnicodeScriptRange() {
		UnicodeScriptProperties scriptProperties = new UnicodeScriptProperties(); 
		List<Character.UnicodeScript> scripts = scriptProperties.getScriptList(); 
		List<Integer> scriptStarts = scriptProperties.getScriptStartList(); 
		
		for (int i=0; i<scripts.size(); i++) {
			int scriptStart = scriptStarts.get(i);
			Integer scriptEnd = IndexSafe.get(scriptStarts, i+1);
			Character.UnicodeScript script = scripts.get(i);
			String hexa = String.format("0x%04X", scriptStart); 
			char ch1 = (char)scriptStart;
			char ch2 = (char)(int)(NullSafe.coalesce(scriptEnd, 0)-1);
			
			String msg = MessageFormat.format("{0} {1}-{2} : {3}", hexa, ch1, ch2, script);
			System.out.println(msg);
		}
	}
	

}
