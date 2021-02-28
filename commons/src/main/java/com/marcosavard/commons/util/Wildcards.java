package com.marcosavard.commons.util;

public class Wildcards {
	
	/**
	 * Convert a limited but simpler wildcard pattern to a standard regex. 
	 * 
	 * @param wildcard string
	 * @return the regular expression string
	 */
	public static String toRegex(String wildcard) {
		String regex = wildcard.replaceAll("\\?", ".");  
		regex =  regex.replaceAll("\\*", ".+"); 
		return regex; 
	}

}
