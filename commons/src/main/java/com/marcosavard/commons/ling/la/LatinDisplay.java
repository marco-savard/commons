package com.marcosavard.commons.ling.la;

import com.marcosavard.commons.lang.StringUtil;

public class LatinDisplay {

	public static String toDisplayString(String givenString) {
		String displayString = StringUtil.stripAccents(givenString);
		displayString = displayString.toUpperCase();
		displayString = displayString.replace('J', 'I');
		displayString = displayString.replace('U', 'V');	
		displayString = displayString.replaceAll("W", "VV");	
		return displayString;
	}
	
}
