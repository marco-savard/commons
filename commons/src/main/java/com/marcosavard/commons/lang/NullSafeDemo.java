package com.marcosavard.commons.lang;

import java.util.List;

//No 1 Mistake : http://www.javacoffeebreak.com/articles/toptenerrors.html
//One-billion dollar error : https://en.wikipedia.org/wiki/Tony_Hoare
//See Effective Java, Clean Code
//see Item #38 of the Second Edition of Effective Java (Item 23 in First Edition).
//Use String.valueOf Rather than toString

//4 different places throwing a NPE
//String property = untrustedObject.getChildA().getChildB().getProperty().toString();


public class NullSafeDemo {

	public static void main(String[] args) {
		demoNullSafeString();
		demoNullSafeList();
		demoNullSafeObject();
		demoNullSafeInteger();
		demoNullSafeEquals();
		demoNullSafeHashCode();
		demoIndexSafe();
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
	
	private static void demoNullSafeObject() {
		Object obj = null;
        int code = NullSafe.of(obj).hashCode();
        System.out.println("hashcode = " + code); 
		System.out.println(); 
	}
	
	private static void demoNullSafeInteger() {
		Integer number = null;
		int i = NullSafe.of(number).intValue(); 
		System.out.println("i = " + i); 
		System.out.println(); 
	}
	
	private static void demoIndexSafe() {
		String qwerty = "qwerty"; 
		int idx = -1; 
		char c;
		
		try {
			c = qwerty.charAt(idx);
		} catch (StringIndexOutOfBoundsException e) {
			c = '\0';
		}
		
		System.out.println("c = " + c); 
		
		c = ((idx >=0 ) && (idx < qwerty.length())) ? qwerty.charAt(idx) : '\0';
		System.out.println("c = " + c);  
		
		c = IndexSafe.of(qwerty, idx) ? qwerty.charAt(idx) : '\0';
		System.out.println("c = " + c);  
		
		System.out.println(); 
		
	}



}
