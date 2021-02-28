package com.marcosavard.commons.geog.res;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WorldArea {
	private static Map<String, WorldArea> worldAreas = new HashMap<>(); 
	private String area; 

	public static WorldArea of(String area) {
		WorldArea worldArea = worldAreas.get(area); 
		
		if (worldArea == null) { 
			worldArea = new WorldArea(area); 
			worldAreas.put(area, worldArea);
		}

		return worldArea;
	}
	
	public WorldArea(String area) {
		this.area = area;
	}

	public String getDisplayName() {
		return getDisplayName(Locale.getDefault());
	}

	public String getDisplayName(Locale displayLocale) {
		String displayName; 
		
		if (area.equals("Africa")) { 
			displayName = getDisplayNameAfrica(displayLocale);
		} else if (area.equals("America")) { 
			displayName = getDisplayNameAmerica(displayLocale);
		} else if (area.equals("Antarctica")) { 
			displayName = getDisplayNameAntarctica(displayLocale);
		} else if (area.equals("Asia")) { 
			displayName = getDisplayNameAsia(displayLocale);
		} else if (area.equals("Atlantic")) { 
			displayName = getDisplayNameAtlantic(displayLocale);
		} else if (area.equals("Australia")) { 
			displayName = getDisplayNameAustralia(displayLocale);
		} else if (area.equals("Europe")) { 
			displayName = getDisplayNameEurope(displayLocale);
		} else {
			displayName = area;
		}
		
		return displayName;
	}

	private String getDisplayNameAntarctica(Locale displayLocale) {
		List<Locale> locales = Arrays.asList(Locale.getAvailableLocales()); 
		Locale antartircaLocale = locales.stream().filter(l -> l.toString().equals("en_AQ")).findFirst().orElse(null);
		String antarctica = antartircaLocale.getDisplayCountry(displayLocale); 
		return antarctica;
	}

	private String getDisplayNameAtlantic(Locale displayLocale) {
		return area;
	}

	private String getDisplayNameAfrica(Locale displayLocale) {
		List<Locale> locales = Arrays.asList(Locale.getAvailableLocales()); 
		Locale southAfricaLocale = locales.stream().filter(l -> l.toString().equals("en_ZA")).findFirst().orElse(null);
		String southAfrica = southAfricaLocale.getDisplayCountry(displayLocale); 
		return southAfrica;
	}

	private String getDisplayNameEurope(Locale displayLocale) {
		List<Locale> locales = Arrays.asList(Locale.getAvailableLocales()); 
		Locale europeLocale = locales.stream().filter(l -> l.toString().equals("en_150")).findFirst().orElse(null);
		String europe = europeLocale.getDisplayCountry(displayLocale); 
		return europe;
	}

	private String getDisplayNameAustralia(Locale displayLocale) {
		List<Locale> locales = Arrays.asList(Locale.getAvailableLocales()); 
		Locale australiaLocale = locales.stream().filter(l -> l.toString().equals("en_AU")).findFirst().orElse(null);
		String australia = australiaLocale.getDisplayCountry(displayLocale); 
		return australia;
	}

	private String getDisplayNameAsia(Locale displayLocale) {
		List<Locale> locales = Arrays.asList(Locale.getAvailableLocales()); 
		Locale indonesiaLocale = locales.stream().filter(l -> l.toString().equals("in_ID")).findFirst().orElse(null);
		String indonesia = indonesiaLocale.getDisplayCountry(displayLocale); 
		return indonesia;
	}

	private String getDisplayNameAmerica(Locale displayLocale) {
		List<Locale> locales = Arrays.asList(Locale.getAvailableLocales()); 
		Locale latinAmericaLocale = locales.stream().filter(l -> l.toString().equals("es_419")).findFirst().orElse(null);
		String latinAmerica = latinAmericaLocale.getDisplayCountry(displayLocale); 
		return latinAmerica;
	}

}
