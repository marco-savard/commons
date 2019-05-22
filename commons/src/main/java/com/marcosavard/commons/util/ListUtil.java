package com.marcosavard.commons.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
	/**
	 * Converts a list of arbitrary elements into [element1, element2.. ]
	 * 
	 * @param list of arbitrary elements 
	 * @return string formatted as [element1, element2.. ]
	 */
	public static String toString(List<Object> list) {
		List<String> strings = toStringList(list); 
		String joined = "[" + String.join(", ", strings) + "]";
		return joined; 
	}

	/**
	 * Converts a list of arbitrary elements into a list of strings
	 * 
	 * @param list of arbitrary elements 
	 * @return list of strings
	 */
	public static List<String> toStringList(List<Object> list) {
		List<String> strings = new ArrayList<>();
		
		for (Object element : list) {
			strings.add(element.toString()); 
		}
		
		return strings;
	}

}
