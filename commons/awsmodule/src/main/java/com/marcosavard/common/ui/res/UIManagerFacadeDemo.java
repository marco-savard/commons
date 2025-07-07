package com.marcosavard.common.ui.res;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class UIManagerFacadeDemo {

	public static void main(String[] args) {	
		System.out.println("[UIManager]");
		UIDefaults defaults = UIManager.getDefaults(); 
		printDefault(defaults);
		
		System.out.println("[UIManagerFacade]");
		defaults = UIManagerFacade.getDefaults(); 
		printDefault(defaults);
	}

	private static void printDefault(UIDefaults defaults) {
		Locale locale = Locale.FRENCH;

		String key = "AbstractButton.clickText";
		Object value = defaults.get(key, locale); 
		System.out.println(key + " = " + value);
		
		defaults.setDefaultLocale(locale);
		value = defaults.get(key); 
		System.out.println(key + " = " + value);
		
		List<Object> list = Collections.list(defaults.keys()); 
		System.out.println(list.size() + " keys : " + list);
		
		list = new ArrayList<>(defaults.keySet()); 
		System.out.println(list.size() + " keySet() : " + list);
		System.out.println();
	}

}
