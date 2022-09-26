package com.marcosavard.commons.text;

import java.lang.Character.UnicodeScript;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class UnicodeScriptProperties {
	private static final int MAX_CHARACTER = 32 * 1024;
	
	private static final ScriptToLocaleMap[] SCRIPT_TO_LOCALE_MAPS = new ScriptToLocaleMap[] {
			new ScriptToLocaleMap(UnicodeScript.LATIN, "sr__#Latn"),
			new ScriptToLocaleMap(UnicodeScript.CYRILLIC, "sr__#Cyrl"),
			new ScriptToLocaleMap(UnicodeScript.ARABIC, "pa__#Arab"),
			new ScriptToLocaleMap(UnicodeScript.ADLAM, "ff__#Adlm"),
			new ScriptToLocaleMap(UnicodeScript.BENGALI, "mni__#Beng"),
			new ScriptToLocaleMap(UnicodeScript.DEVANAGARI, "sd__#Deva"),
			new ScriptToLocaleMap(UnicodeScript.GURMUKHI, "pa__#Guru"),
			new ScriptToLocaleMap(UnicodeScript.OL_CHIKI, "sat__#Olck"),
			new ScriptToLocaleMap(UnicodeScript.TIFINAGH, "shi__#Tfng"),
			new ScriptToLocaleMap(UnicodeScript.VAI, "vai__#Vaii"),
			new ScriptToLocaleMap(UnicodeScript.HAN, "zh__#Hans"),
			
			new ScriptToLocaleMap(UnicodeScript.GREEK, "el"),
			new ScriptToLocaleMap(UnicodeScript.HEBREW, "iw"),
			new ScriptToLocaleMap(UnicodeScript.ARMENIAN, "hy"),
			new ScriptToLocaleMap(UnicodeScript.GUJARATI, "gu"),
			new ScriptToLocaleMap(UnicodeScript.TAMIL, "ta"),
			new ScriptToLocaleMap(UnicodeScript.TELUGU, "te"),
			new ScriptToLocaleMap(UnicodeScript.KANNADA, "kn"),
			new ScriptToLocaleMap(UnicodeScript.SINHALA, "si"),
			new ScriptToLocaleMap(UnicodeScript.THAI, "th"),
			new ScriptToLocaleMap(UnicodeScript.LAO, "lo"),
			new ScriptToLocaleMap(UnicodeScript.TIBETAN, "bo"),
			new ScriptToLocaleMap(UnicodeScript.GEORGIAN, "ka"),
			new ScriptToLocaleMap(UnicodeScript.CHEROKEE, "chr"),
			new ScriptToLocaleMap(UnicodeScript.KHMER, "km"),
			new ScriptToLocaleMap(UnicodeScript.MONGOLIAN, "mn"),
			new ScriptToLocaleMap(UnicodeScript.JAVANESE, "jv")
		}; 
	
	//object fields
	private Map<UnicodeScript, String> localesByUnicodeScript = new HashMap<>();
	private List<Locale> allLocales;
	private List<String> allLanguages; 
	private List<UnicodeScript> scriptList = new ArrayList<>();
	private List<Integer> scriptStartList = new ArrayList<>();
	
	public UnicodeScriptProperties() {
		allLocales = Arrays.asList(Locale.getAvailableLocales());
		allLanguages = Arrays.asList(Locale.getISOLanguages()); 
		initialize();
		
		for (ScriptToLocaleMap map : SCRIPT_TO_LOCALE_MAPS) {
			localesByUnicodeScript.put(map.script, map.localeName); 
		}
	}

	public String getDisplayName(UnicodeScript script, Locale displayLocale) {
		String localeCode = localesByUnicodeScript.get(script); 
		String displayName = extractName(localeCode, displayLocale);
		displayName = (displayName != null) ? displayName : extractFromEnum(script);
		return displayName;
	}
	
	public String getNativeName(UnicodeScript script) {
		String localeCode = localesByUnicodeScript.get(script); 
		Locale locale = allLocales.stream().filter(l -> l.toString().equals(localeCode)).findFirst().orElse(null);
		return getDisplayName(script, locale);
	}
	
	public byte getDirectionality(UnicodeScript script) {
		String nativeCode = getNativeName(script);
		char ch = nativeCode.charAt(0); 
		return Character.getDirectionality(ch); 
	}
	
	public List<UnicodeScript> getScriptList() {
		return scriptList; 
	}
	
	public List<Integer> getScriptStartList() {
		return scriptStartList; 
	}
	
	//
	// private methods
	//
	private void initialize() {
		UnicodeScript previousScript = null;
		int previousIndex = 0;
		
		for (int i=0; i<MAX_CHARACTER; i++) {
            UnicodeScript currentScript = UnicodeScript.of(i);
			
			if (! currentScript.equals(previousScript)) {
				if (previousScript != null) {
					scriptList.add(previousScript);
					scriptStartList.add(previousIndex);
				}
				
				previousIndex = i;
				previousScript = currentScript;
			}
		}
	}
	
	private String extractName(String localeCode, Locale displayLocale) {
		Locale locale = findLocale(allLocales, localeCode); 
		boolean language = allLanguages.contains(localeCode);
		String displayName = (locale == null) ? null : locale.getDisplayName(displayLocale); 
		String substring = language ? displayName : substring(displayName, "(", ")");
		return substring;
	}
	
	private Locale findLocale(List<Locale> allLocales, String code) {
		Locale foundLocale = (code == null) ? null : allLocales.stream().filter(l -> code.equals(l.toString())).findFirst().orElse(null);
		return foundLocale;
	}
	
	private static String substring(String givenString, String prefix, String suffix) {
		int begin = (givenString == null) ? -1 : givenString.indexOf(prefix); 
		int end = (givenString == null) ? -1 : givenString.lastIndexOf(suffix); 
		String substring = (begin == -1) || (end == -1) ? null : givenString.substring(begin+1, end);
		return substring;
	}

	private String extractFromEnum(UnicodeScript script) {
		String name = script.toString().toLowerCase().replace('_', ' ');
		return name;
	}
	
	//
	// inner class
	//
	private static class ScriptToLocaleMap {
		private UnicodeScript script;
		private String localeName;
		
		ScriptToLocaleMap(UnicodeScript script, String localeName) {
			this.script = script;
			this.localeName = localeName;
		}	
	}

	public List<int[]> getScriptRanges(UnicodeScript givenScript) {
		List<int[]> ranges = new ArrayList<>(); 
		List<Integer> indices = new ArrayList<>();
		
		for (int i=0; i<scriptList.size(); i++) {
			UnicodeScript script = scriptList.get(i); 
			if (script.equals(givenScript)) {
				indices.add(i);
			}
		}
		
		for (int i : indices) {
			int start = scriptStartList.get(i);
			int end = scriptStartList.get(i+1)-1;
			int[] range = new int[] {start, end};
			ranges.add(range);
		}
		
		return ranges;
	}





}
