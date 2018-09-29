package com.marcosavard.commons.security;

import java.text.MessageFormat;

public class SocialInsuranceNumberDemo {

	public static void main(String[] args) {
		SocialInsuranceNumber[] numbers = new SocialInsuranceNumber[] {
			new SocialInsuranceNumber("046 454 286 "),
			new SocialInsuranceNumber("641 211 198"),
		}; 
		
		for (SocialInsuranceNumber sin : numbers) {
			String msg = MessageFormat.format("  {0} is valid: {1}", sin.toDisplayString(), sin.isValid()); 
			System.out.println(msg);
		}
	}

}
