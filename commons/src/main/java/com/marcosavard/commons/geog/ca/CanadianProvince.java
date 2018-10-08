package com.marcosavard.commons.geog.ca;

/**
 * An enumeration for Canadian provinces and territories. Codes
 * follow naming convention of Canada Post.  
 * 
 * @author Marco
 *
 */
public enum CanadianProvince {
	NL("Newfoundland and Labrador", "Terre-Neuve-et-Labrador"),
	NS("Nova Scotia", "Nouvelle-�cosse"),
	PE("Prince Edward Island", "�le-du-Prince-�douard"),
	NB("New Brunswick", "Nouveau-Brunswick"),
	QC("Quebec", "Qu�bec"),
	ON("Ontario"),
	MB("Manitoba"),
	SK("Saskatchewan"),
	AB("Alberta"),
	BC("British Columnbia", "Colombie-Britannique"),
	NU("Nunavut"),
	NT("North West Territories", "Territoires du Nord-Ouest"),
	YK("Yukon"); 
	
	private final String englishName, frenchName;
	public String getEnglishName() {return englishName; }
	public String getFrenchName() {return frenchName; }
	
	private CanadianProvince(String englishName, String frenchName) {
		this.englishName = englishName;
		this.frenchName = frenchName;
	}
	
	//if French and English names are the same
	private CanadianProvince(String name) {
		this.englishName = name;
		this.frenchName = name;
	}
}
