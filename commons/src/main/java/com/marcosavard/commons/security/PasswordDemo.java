package com.marcosavard.commons.security;

import java.text.MessageFormat;

import com.marcosavard.commons.security.Password.Strenght;

public class PasswordDemo {

	//Store passwords in String just for demo or testing purposes
	//In production code, always store passwords in char[], and clear the password
	//once the hash is computed
	
	public static void main(String[] args) {

		String[] passwords = new String[] {
			"abc", 
			"password",
			"P@ssw0rd",
			"horse equitation stable zebra" 
		};
		
		for (String pw : passwords) {
			int enthropy = Password.computeEntropy(pw.toCharArray()); 
			Strenght strength = Password.getStrenght(enthropy);
			String msg = MessageFormat.format("  ''{0}'' enthropy = {1} bits ({2})", pw, enthropy, strength);
			System.out.println(msg);
		}
		
		
	}

}
