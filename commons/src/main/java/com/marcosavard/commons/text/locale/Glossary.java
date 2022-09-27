package com.marcosavard.commons.text.locale;

import com.marcosavard.commons.lang.CharString;
import com.marcosavard.commons.util.collection.UniqueList;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class Glossary {
	private Locale language;
	private Map<String, Category> glossary = new TreeMap<>();

	public static Glossary of(Locale locale) {
		Glossary glossary = new Glossary(locale); 
		return glossary;
	}
	
	private Glossary(Locale locale) {	
		this.language = locale;
		addWeekDays(locale);
		addMonthNames(locale);
		addLanguageNames(locale); 
		//addCountryNames(locale); 
		//addCurrencyNames(locale);
		//addTimeZones(locale);

	}




	public List<String> getWords() {
		return new ArrayList<>(glossary.keySet()); 
	}
	
	public List<String> getWords(Category category) {
		List<String> words = glossary.keySet().stream().filter(s -> glossary.get(s).equals(category)).collect(Collectors.toList()); 
		return words;
	}
	
	private void addCountryNames(Locale displayLocale) {
		String[] countries = Locale.getISOCountries();
		List<Locale> allLocales = Arrays.asList(Locale.getAvailableLocales());
		
		for (String country : countries) { 
			List<Locale> locales = allLocales.stream().filter(l -> country.equals(l.getCountry())).collect(Collectors.toList());
			for (Locale locale : locales) {
				String countryName = locale.getDisplayCountry(displayLocale);
				addToGlossary(countryName, Category.COUNTRY); 
			}
		}
	}

	private void addLanguageNames(Locale displayLocale) {
		List<Locale> allLocales = Arrays.asList(Locale.getAvailableLocales());
		List<String> countries = Arrays.asList(Locale.getISOCountries());
		Currency euro = Currency.getInstance("EUR");

		List<Locale> euroLocales = allLocales.stream()
				.filter(l -> countries.contains(l.getCountry()))
				.filter(l -> euro.equals(Currency.getInstance(l)))
				.collect(Collectors.toList());

		//euroLocales = new UniqueList<Locale>(euroLocales);

		for (Locale locale : euroLocales) {
			String languageName = locale.getDisplayLanguage(displayLocale);
			addToGlossary(languageName, Category.NATIONAL_LANGUAGE);
		}

	}
	
	private void addLanguageNamesOld(Locale displayLocale) {
		//add national languages 
		List<Locale> allLocales = Arrays.asList(Locale.getAvailableLocales());
		String[] countries = Locale.getISOCountries();
		List<String> nationalLanguages = new ArrayList<>();
		
		for (String country : countries) { 
			List<Locale> locales = allLocales.stream().filter(l -> country.equals(l.getCountry())).collect(Collectors.toList());
			
			for (Locale locale : locales) {
				String languageName = locale.getDisplayLanguage(displayLocale);
				nationalLanguages.add(languageName);
				String script = locale.getDisplayScript(displayLocale); 
				addToGlossary(languageName, Category.NATIONAL_LANGUAGE); 
				addToGlossary(script, Category.SCRIPT);
			}
		}
		
		//add other languages
		String[] languages = Locale.getISOLanguages(); 
		
		for (String language : languages) { 
			List<Locale> locales = allLocales.stream().filter(l -> language.equals(l.getLanguage())).collect(Collectors.toList());
			
			for (Locale locale : locales) {
				String languageName = locale.getDisplayLanguage(displayLocale);
				
				if (! nationalLanguages.contains(languageName)) {
					String script = locale.getDisplayScript(displayLocale); 
					addToGlossary(languageName, Category.REGIONAL_LANGUAGE); 
					addToGlossary(script, Category.SCRIPT); 
				}
			}
		}
	}
	
	private void addCurrencyNames(Locale locale) {
		Set<Currency> currencies = Currency.getAvailableCurrencies();
		
		for (Currency currency : currencies) { 
			CharString code = CharString.of(currency.getCurrencyCode());
			if (! code.in("XUA")) { 
				String displayName = currency.getDisplayName(locale); 
				addToGlossary(displayName, Category.CURRENCY); 
			}
		}
	}
	
	private void addTimeZones(Locale locale) {
		DateFormatSymbols symbols = new DateFormatSymbols(locale);
		String[][] zoneStrings = symbols.getZoneStrings();
		
		for (int i=0; i<zoneStrings.length; i++) {
			String code = zoneStrings[i][0];
			String displayName = zoneStrings[i][1];
			addToGlossary(displayName, Category.TIMEZONE); 
		}
	}
	

	
	

	private void addToGlossary(String displayName, Category category) {
		displayName = displayName.replace('(', ' '); 
		displayName = displayName.replace(')', ' '); 
		displayName = displayName.replaceAll("'", " "); 
		displayName = displayName.replaceAll("’", " "); 
		displayName = displayName.replaceAll("-", " "); 
		//displayName = displayName.replaceAll("�", " "); 
		displayName = displayName.replaceAll("-", " "); 
		String[] words = displayName.split(" +"); 
		
		for (String word : words) { 
			boolean toAdd = word.length() > 1 && Character.isLetter(word.charAt(0)); 
			toAdd = toAdd &= ! isAcronym(word); 
			
			if (toAdd) { 
				//category = glossary.containsKey(word) ? Category.COMMON : category;
				glossary.put(word, category);
			}
		}
	}
	
	private static boolean isAcronym(String word) {
		boolean acronym = word.equals(word.toUpperCase()); 
		return acronym;
	}

	private void addMonthNames(Locale locale) {
		DateFormatSymbols symbols = new DateFormatSymbols(locale);
		String[] months = symbols.getMonths();
		
		for (String month : months) { 
			if (! month.isEmpty()) {
				addToGlossary(month, Category.MONTH); 
			}
		}
	}
	
	private void addWeekDays(Locale locale) {
		DateFormatSymbols symbols = new DateFormatSymbols(locale);
		String[] weekdays = symbols.getWeekdays();
		
		for (String weekday : weekdays) { 
			if (! weekday.isEmpty()) {
				addToGlossary(weekday, Category.WEEK_DAY); 
			}
		}
	}
	
	public void printStats(Locale locale) {
		String glossary = "Glossary : " + language.getDisplayLanguage(locale); 
		System.out.println(glossary); 
		
		for (Category category : Category.values()) { 
			printStats(category); 
		}
		
		System.out.println(); 
	}

	
	private void printStats(Category category) {
		List<String> words = glossary.keySet().stream().filter(s -> glossary.get(s).equals(category)).collect(Collectors.toList()); 
		System.out.println("  " + category.toString() + " : " + words.size() + " words" + " " + words); 
		
	}

	public enum Category {
		COMMON,
		COUNTRY,
		CURRENCY,
		NATIONAL_LANGUAGE,
		REGIONAL_LANGUAGE,
		MONTH,
		SCRIPT,
		TIMEZONE,
		WEEK_DAY
	}

	public Locale getLanguage() {
		return language;
	}





}
