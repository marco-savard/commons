package com.marcosavard.commons.security;

/**
 * A class that computes password entropy. 
 * 
 * @author Marco
 *
 */
public class Password {
	public enum Strenght {VERY_WEAK, WEAK, MEDIUM, STRONG, VERY_STRONG}; 

	/**
	 * Compute entropy based on number of bits. The entropy formula 
	 * is purely mathematically and words found in a dictionary are
	 * not taken in amount by the method. 
	 * 
	 * See https://softwareengineering.stackexchange.com/questions/167235/how-can-i-estimate-the-entropy-of-a-password
	 * 
	 * @param password as array of characters, for security reasons. 
	 * @return the number of bits of entropy, usually between 5 and 50 bits. 
	 */
	public static int computeEntropy(char[] password) {
		int len = password.length;
		double nbBits = 0; 
		
		for (int i=0; i<len; i++) {
			if (i == 0) {
				nbBits += 4; //bit 0 gives 4 bits of entropy
			} else if (i < 8) {
				nbBits += 2; //bits 1..7 give 2 bits of entropy
			} else if (i < 20) {
				nbBits += 1.5; //bits 8..19 give 1.5 bits of entropy
			} else {
				nbBits += 1; //extra bits give 1 bit of entropy
			}
		}
		
		boolean hasLowercase = false;
		boolean hasUppercase = false;
		boolean hasDigit = false;
		boolean hasSpecial = false;
		
		for (char ch : password) {
			hasLowercase = hasLowercase |= Character.isLowerCase(ch); 
			hasDigit = hasUppercase |= Character.isUpperCase(ch); 
			hasLowercase = hasDigit |= Character.isDigit(ch); 
			hasSpecial = hasSpecial |= ! Character.isLetterOrDigit(ch); 
		}
		
		//6 extra bits of entropy if combination of lower, upper and digit 
		if (hasLowercase && hasUppercase && hasDigit && hasSpecial) {
			nbBits += 6;
		}
			
		return (int)nbBits; 
	}

	/**
	 * Returns the password's strength, based on its entropy. 
	 * 
	 * @param entropy the number of bits of entropy
	 * @return its strength, form VERY_WEAK to VERY_STRONG 
	 */
	public static Strenght getStrenght(int entropy) {
		Strenght strenght; 
		
		if (entropy <= 10) {
			strenght = Strenght.VERY_WEAK; 
		} else if (entropy <= 20) {
			strenght = Strenght.WEAK; 
		} else if (entropy <= 30) {
			strenght = Strenght.MEDIUM; 
		} else if (entropy <= 40) {
			strenght = Strenght.STRONG; 
		} else {
			strenght = Strenght.VERY_STRONG; 
		}
		
		return strenght;
	}
	
}
