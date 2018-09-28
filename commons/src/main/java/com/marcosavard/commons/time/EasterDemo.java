package com.marcosavard.commons.time;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EasterDemo {

	public static void main(String[] args) {
		DateFormat format = new SimpleDateFormat("dd MMMM"); 
		
		for (int y=2010; y<2020; y++) {
			Date easterDate = Easter.getEasterDate(y);  
			String year = String.format("%d", y); 
			String msg = MessageFormat.format("Easter {0} is : {1}", year, format.format(easterDate)); 
			System.out.println(msg);
		}
	}
}
