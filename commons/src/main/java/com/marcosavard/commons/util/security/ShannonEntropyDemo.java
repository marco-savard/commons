package com.marcosavard.commons.util.security;

public class ShannonEntropyDemo {

	public static void main(String[] args) {
		 String[] strings = {
			      "1223334444",
			      "1223334444555555555", 
			      "122333", 
			      "1227774444",
			      "aaBBcccDDDD",
			      "1234567890abcdefghijklmnopqrstuvwxyz",
			      "Rosetta Code",
			    };
			 
			    for (String str : strings) {
			      double entropy = ShannonEntropy.getEntropy(str);
			      System.out.printf("Shannon entropy of %40s: %.12f%n", "\"" + str + "\"", entropy);
			    }
			    return;
			  }



}
