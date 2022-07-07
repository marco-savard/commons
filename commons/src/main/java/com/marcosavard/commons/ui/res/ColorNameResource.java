package com.marcosavard.commons.ui.res;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ColorNameResource {
	private String basename; 
	
	public ColorNameResource() {
		basename = ColorNameResource.class.getName();
	}
	
	public String getColorName(String key, Locale locale) {
		String colorName; 
		
		try {
			ResourceBundle labels = ResourceBundle.getBundle(basename, locale);
			key = String.valueOf(key).replace(' ', '_');
			colorName = labels.getString(key);
		} catch (MissingResourceException ex) {
			colorName = key;
		}

		return colorName;
	}

}
