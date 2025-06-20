package com.marcosavard.common.lang.safe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IndexSafe {
	public static <T> List<T> of(List<T> unsafe) {
		return new IndexSafeList(unsafe);
	}

	public static <T> List<T> of(T[] unsafe) {
		return new IndexSafeList(Arrays.asList(unsafe));
	}

	public static CharSequence of(String unsafe) {
		return new IndexSafeCharSequence(unsafe);
	}

	public static <T> T get(List<T> list, int idx) {
		T item = (idx < list.size()) ? list.get(idx) : null;
		return item;
	}

	public static <T> T get(T[] array, int idx) {
		T item = (idx < array.length) ? array[idx] : null;
		return item;
	}

	public static int get(int[] array, int idx) {
		int item = (idx < array.length) ? array[idx] : 0;
		return item;
	}

    private static class IndexSafeList<T> extends ArrayList {
		private final List<T> safeList;

		public IndexSafeList(List<T> unsafe) {
			safeList = (List<T>)NullSafe.of(unsafe);
		}

		@Override
		public T get(int idx) {
			boolean safe = (idx >= 0) && (idx < safeList.size());
			return safe ? safeList.get(idx) : null;
		}
	}

	private static class IndexSafeCharSequence implements CharSequence {
		private final String safeString;

		public IndexSafeCharSequence(String unsafe) {
			safeString = (String)NullSafe.of(unsafe);
		}

		@Override
		public char charAt(int idx) {
			boolean safe = (idx >= 0) && (idx < safeString.length());
			return safe ? safeString.charAt(idx) : Character.MIN_VALUE;
		}

		@Override
		public int length() {
			return safeString.length();
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			boolean safe = (start >= 0) && (end < safeString.length() && (start <= end));
			return safe ? safeString.subSequence(start, end) : String.valueOf((String)null);
		}

		@Override
		public String toString() {
			return safeString.toString();
		}
	}

}
