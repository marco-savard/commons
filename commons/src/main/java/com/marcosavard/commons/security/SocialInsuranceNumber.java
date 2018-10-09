package com.marcosavard.commons.security;

/**
 * Create a Social Insurance Number, and tells if it is a valid SIN. 
 * 
 * @author Marco
 *
 */
public class SocialInsuranceNumber {
	private String checkedNumber; 
	
	/**
	 * Create a Social Insurance Number, blanks and dashed ignored. 
	 * 
	 * @param string not sanitized value
	 */
	public SocialInsuranceNumber(String string) {
		checkedNumber = string.replaceAll(" ", "").replaceAll("-", "");
	}
	
	@Override
	public String toString() {
		return checkedNumber;
	}

	/**
	 * A string in human-readable formal
	 * 
	 * @return formated string
	 */
	public String toDisplayString() {
		String displayString = checkedNumber.substring(0, 3) + " " +
			checkedNumber.substring(3, 6) + " " +
			checkedNumber.substring(6, 9);
		return displayString;
	}

	/**
	 * Tell if it is a valid Social Insurance Number. 
	 * 
	 * @return true if valid
	 */
	public boolean isValid() {	
		boolean valid = checkedNumber.matches("^\\d{9}$"); 
		
		if (valid) {
			int sum = 0;
			
			for (int i=0; i<9; i++) {
				int digit = checkedNumber.charAt(i) - 48; 
				int mult = ((i % 2) == 0) ? 1 : 2; 
				int prod = digit * mult; 
				prod = (prod >= 10) ? (prod / 10) + (prod % 10) : prod;
				sum += prod;
			}
			
			valid = (sum % 10) == 0;
		}
		
		return valid;
	}

}
