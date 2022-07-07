package com.marcosavard.commons.ui.res;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class UIManagerFacade {

	public static UIDefaults getDefaults() {
		UIDefaults uiDefaults = new ExtendedUIDefaults(UIManager.getDefaults()); 
		return uiDefaults;
	}
	
	private static class ExtendedUIDefaults extends UIDefaults {
		UIDefaults uiDefaults; 
		
		public ExtendedUIDefaults(UIDefaults uiDefaults) {
			this.uiDefaults = uiDefaults;
		}
		
		@Override
		public boolean containsKey(Object key) {
			String packageName = UIManagerFacade.class.getPackageName();
			ResourceBundle labels = ResourceBundle.getBundle(packageName + ".UIDefaults", Locale.ENGLISH);
			boolean contained = labels.containsKey(String.valueOf(key)) ? true : uiDefaults.contains(key);
			return contained; 
		}
		
		@Override
		public Object get(Object key) {
			return get(key, getDefaultLocale());
		}
		
		@Override
		public Object get(Object key, Locale locale) {
			String packageName = UIManagerFacade.class.getPackageName();
			ResourceBundle labels = ResourceBundle.getBundle(packageName + ".UIDefaults", locale);
			Object value = labels.getString(String.valueOf(key));
			value = (value == null) ? uiDefaults.get(key) : value; 
			return value;
		}
		
		@Override
		public synchronized Enumeration<Object> keys() {
			return uiDefaults.keys();
	    }
		
		@Override
		public Set<Object> keySet() {
			List<Object> list = Collections.list(uiDefaults.keys());
			String packageName = UIManagerFacade.class.getPackageName();
			ResourceBundle labels = ResourceBundle.getBundle(packageName + ".UIDefaults", Locale.ENGLISH);
			list.addAll(Collections.list(labels.getKeys())); 
			Set<Object> set = new HashSet<>(list);
			return set;
		}	
		
		@Override
	    public Locale getDefaultLocale() {
	        return uiDefaults.getDefaultLocale();
	    }
	    
		@Override
		public void setDefaultLocale(Locale locale) {
			uiDefaults.setDefaultLocale(locale);
		}
		 
	}

}
