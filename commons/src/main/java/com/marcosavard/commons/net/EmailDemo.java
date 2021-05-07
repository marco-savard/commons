package com.marcosavard.commons.net;

public class EmailDemo {

	public static void main(String[] args) {
		Email email = Email.of("msavard@gmail.com"); 
		boolean valid = email.validate();
	}

}
