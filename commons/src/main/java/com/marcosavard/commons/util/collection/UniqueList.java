package com.marcosavard.commons.util.collection;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class UniqueList<T> extends NullSafeList<T> {

	public UniqueList() {
	}

	public UniqueList(List<T> items) {
		for (T item : items) {
			this.add(item);
		}
	}

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
