package com.marcosavard.commons.soc;

import com.marcosavard.commons.util.StringUtil;

public class Surname {
	public enum Gender {MALE, FEMALE}; 
	
	private String sanitizedName; 
	private Gender gender; 

	public Surname(String name, Gender gender) {
		sanitizedName = name.trim().toLowerCase(); 
		sanitizedName = StringUtil.capitalize(sanitizedName); 
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return sanitizedName;
	}

	public Gender getGender() {
		return gender;
	}

}
