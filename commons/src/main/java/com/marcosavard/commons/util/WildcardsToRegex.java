package com.marcosavard.commons.util;

public class WildcardsToRegex {
	
	/**
	 * Convert a limited but simpler wildcard pattern to a standard regex. 
	 * 
	 * @param wildcards
	 * @return
	 */
	public static String toRegex(String wildcards) {
		String regex = wildcards.replaceAll("\\?", ".");  
		regex =  regex.replaceAll("\\*", ".+"); 
		return regex; 
	}

}
