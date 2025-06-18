package com.marcosavard.common.lang.safe;

import java.util.List;
import java.util.Objects;

//No 1 Mistake : http://www.javacoffeebreak.com/articles/toptenerrors.html
//One-billion dollar error : https://en.wikipedia.org/wiki/Tony_Hoare
//See Effective Java, Clean Code
//see Item #38 of the Second Edition of Effective Java (Item 23 in First Edition).
//Use String.valueOf Rather than toString

//4 different places throwing a NPE
//String property = untrustedObject.getChildA().getChildB().getProperty().toString();

//"I call it my billion-dollar mistake. It was the invention of the null reference in 1965.
//At that time, I was designing the first comprehensive type system for references in an object oriented language (ALGOL W).
//My goal was to ensure that all use of references should be absolutely safe, with checking performed automatically
//by the compiler. But I couldn’t resist the temptation to put in a null reference, simply because
//it was so easy to implement. This has led to innumerable errors, vulnerabilities, and system crashes,
// which have probably caused a billion dollars of pain and damage in the last forty years.”
// (Tony Hoare, Null References:The Billion Dollar Mistake, 2009)


public class NullSafeDemo {

	public static void main(String[] args) {
		demoCountingCharactersWithoutNullSafe();
		demoCountingCharactersWithApache();
		demoCountingCharactersWithNullSafe();
		demoNullSafeString();
		demoNullSafeList();
		demoNullSafeValues();
		demoNullSafePrimitiveList();
		demoNullSafeObjectArrays();
		demoNullSafePrimitiveArrays();
		demoNullSafeEquals();
		demoNullSafeHashCode();
		demoCoalese();
	}

	private static void demoCountingCharactersWithoutNullSafe() {
		System.out.println("Counting number of characters in list of words");
		List<String> words = getWordList();
		int totalCount = 0;

		if (words != null) {
			for (String word : words) {
				if (word != null) {
					totalCount += word.length();
				}
			}
		}

		System.out.println("  totalCount = " + totalCount);
		System.out.println();
	}

	private static void demoCountingCharactersWithApache() {
		System.out.println("Counting number of characters in list of words");
		List<String> words = getWordList();
		int totalCount = 0;

		if (! Objects.isNull(words)) { //or org.apache.commons.collections4.CollectionUtils.isNotEmpty()
			for (String word : words) {
				if (! Objects.isNull(word)) { //or org.apache.commons.lang3.StringUtils.isNotEmpty()
					totalCount += word.length();
				}
			}
		}

		System.out.println("  totalCount = " + totalCount);
		System.out.println();
	}

	private static void demoCountingCharactersWithNullSafe() {
		System.out.println("Counting number of characters in list of words");
		List<String> words = getWordList();
		int totalCount = 0;

		for (String word : NullSafe.of(words)) {
			totalCount += NullSafe.of(word).length();
		}

		System.out.println("  totalCount = " + totalCount);
		System.out.println();
	}

	private static List<String> getWordList() {
		return null;
	}

	private static void demoNullSafeString() {
		System.out.println("Length of a null String"); 
		String str = null;
		int len;
		
		//Method 1 : tedious
		try {
			len = str.length();
		} catch (NullPointerException e) {
			len = 0;
			System.out.println("  len = " + len); 
		}
	 
		//Method 2 : hard-to-read, increase cyclomatic complexity
		len = (str == null) ? 0 : str.length();
		System.out.println("  len = " + len); 
		
		//Method 3 : short, self-explanatory
		len = NullSafe.of(str).length();
		System.out.println("  len = " + len); 
		System.out.println(); 
	}

	private static void demoNullSafeList() {
		System.out.println("Length of a null list"); 
		List<String> list = null;
		int len;
		
		try {
			len = list.size();
		} catch (NullPointerException e) {
			len = 0;
			System.out.println("  len = " + len); 
		}
	 
		len = (list == null) ? 0 : list.size();
		System.out.println("  len = " + len); 
		
		len = NullSafe.of(list).size();
		System.out.println("  len = " + len); 
		System.out.println(); 
	}

	private static void demoNullSafeValues() {
		Boolean booleanValue = null;
		Byte byteValue = null;
		Character charValue = null;
		Short shortValue = null;
		Integer integerValue = null;
		Long longValue = null;
		Float floatValue = null;
		Double doubleValue = null;

		System.out.println("b = " + NullSafe.of(booleanValue).booleanValue());
		System.out.println("bt = " + NullSafe.of(byteValue).byteValue());
		System.out.println("c = " + NullSafe.of(charValue).charValue());
		System.out.println("s = " + NullSafe.of(shortValue).shortValue());
		System.out.println("i = " + NullSafe.of(integerValue).intValue());
		System.out.println("l = " + NullSafe.of(longValue).longValue());
		System.out.println("f = " + NullSafe.of(floatValue).floatValue());
		System.out.println("d = " + NullSafe.of(doubleValue).doubleValue());
		System.out.println();
	}

	private static void demoNullSafePrimitiveList() {
		List<Boolean> booleanArray = null;
		List<Byte> byteArray = null;
		List<Character> charArray = null;
		List<Short> shortArray = null;
		List<Integer> intArray = null;
		List<Long> longArray = null;
		List<Float> floatArray = null;
		List<Double> doubleArray = null;

		System.out.println("b[] = " + NullSafe.of(booleanArray).size());
		System.out.println("by[] = " + NullSafe.of(byteArray).size());
		System.out.println("c[] = " + NullSafe.of(charArray).size());
		System.out.println("s[] = " + NullSafe.of(shortArray).size());
		System.out.println("i[] = " + NullSafe.of(intArray).size());
		System.out.println("l[] = " + NullSafe.of(longArray).size());
		System.out.println("f[] = " + NullSafe.of(floatArray).size());
		System.out.println("d[] = " + NullSafe.of(doubleArray).size());
		System.out.println();
	}

	private static void demoNullSafeObjectArrays() {
		Boolean[] booleanArray = null;
		Byte[] byteArray = null;
		Character[] charArray = null;
		Short[] shortArray = null;
		Integer[] intArray = null;
		Long[] longArray = null;
		Float[] floatArray = null;
		Double[] doubleArray = null;

		System.out.println("b[] = " + NullSafe.of(booleanArray).length);
		System.out.println("by[] = " + NullSafe.of(byteArray).length);
		System.out.println("c[] = " + NullSafe.of(charArray).length);
		System.out.println("s[] = " + NullSafe.of(shortArray).length);
		System.out.println("i[] = " + NullSafe.of(intArray).length);
		System.out.println("l[] = " + NullSafe.of(longArray).length);
		System.out.println("f[] = " + NullSafe.of(floatArray).length);
		System.out.println("d[] = " + NullSafe.of(doubleArray).length);
		System.out.println();
	}

	private static void demoNullSafePrimitiveArrays() {
		boolean[] booleanArray = null;
		byte[] byteArray = null;
		char[] charArray = null;
		short[] shortArray = null;
		int[] intArray = null;
		long[] longArray = null;
		float[] floatArray = null;
		double[] doubleArray = null;

		System.out.println("b[] = " + NullSafe.of(booleanArray).length);
		System.out.println("by[] = " + NullSafe.of(byteArray).length);
		System.out.println("c[] = " + NullSafe.of(charArray).length);
		System.out.println("s[] = " + NullSafe.of(shortArray).length);
		System.out.println("i[] = " + NullSafe.of(intArray).length);
		System.out.println("l[] = " + NullSafe.of(longArray).length);
		System.out.println("f[] = " + NullSafe.of(floatArray).length);
		System.out.println("d[] = " + NullSafe.of(doubleArray).length);
		System.out.println();
	}

	private static void demoNullSafeEquals() {
		System.out.println("Null-safe equals()"); 
		String s1=null, s2=null;
		boolean equal;
		
		equal = NullSafe.equals(s1, s2);
		System.out.println(" (null == null) : " + equal); 
		
		equal = NullSafe.equals(s1, "");
		System.out.println(" (null == \"\") : " + equal); 
		
		equal = NullSafe.equals("", "");
		System.out.println(" (\\\"\\\" == \"\") : " + equal); 
		System.out.println(); 
	}
	
	private static void demoNullSafeHashCode() {
		String s1=null; 
		int hashCode = NullSafe.hashCode(s1); 
		System.out.println("hashCode = " + hashCode); 
		System.out.println(); 
	}

	private static void demoCoalese() {
		String nullString = null;
		String safeString = NullSafe.coalesce(nullString, "");
		System.out.println("safeString.length() = " + safeString.length());
		System.out.println();
	}
	




}
