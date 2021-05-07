package com.marcosavard.commons.net;

public class Email {
	private String address; 

	private Email(String address) {
		// TODO Auto-generated constructor stub
	}

	public boolean validate() {
		// match regex
		return false;
	}

	public static Email of(String address) {
		Email email = new Email(address); 
		return email;
	}

}
