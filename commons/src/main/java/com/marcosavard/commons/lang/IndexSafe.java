package com.marcosavard.commons.lang;

import java.util.Collection;
import java.util.List;

public class IndexSafe {

	public static <T> boolean of(Collection<T> collection, int idx) {
		boolean safe = (collection != null) && (idx >= 0) && (idx < collection.size());
		return safe;
	}

	public static <T> boolean of(List<T> list, int idx) {
		boolean safe = (list != null) && (idx >= 0) && (idx < list.size());
		return safe;
	}

	public static <T> boolean of(T[] array, int idx) {
		boolean safe = (array != null) && (idx >= 0) && (idx < array.length);
		return safe;
	}

	// verify if safe before calling String.charAt()
	public static <T> boolean of(String s, int idx) {
		boolean safe = (s != null) && (idx >= 0) && (idx < s.length());
		return safe;
	}

	// verify if safe before calling String.substring()
	public static <T> boolean of(String s, int startIdx, int endIdx) {
		boolean safe = (s != null) && (startIdx >= 0) && (endIdx < s.length() && (startIdx < endIdx));
		return safe;
	}


}
