package com.marcosavard.commons.util;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class UniqueList<T> extends ArrayList<T> { 
	@Override 
	public boolean add(T element) { 
		boolean added = false; 
		
		if (! super.contains(element)) { 
			super.add(element); 
			added = true; 
		}
		
		return added;
	}
}
