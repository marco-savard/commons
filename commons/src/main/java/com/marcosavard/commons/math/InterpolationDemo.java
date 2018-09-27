package com.marcosavard.commons.math;

import java.text.MessageFormat;

public class InterpolationDemo {

	public static void main(String[] args) {
		//define a mile-to-kilometer converter
		Interpolation mileToKmConverter = new Interpolation(); 
		mileToKmConverter.define(65, 100);
		
		double miles = 150; 
		double km = mileToKmConverter.interpolate(miles);  
		
		String msg = MessageFormat.format(" {0} miles equals {1} km", miles, km); 
		System.out.println(msg);

		//define a Celcius-to-Farhenheit converter
		Interpolation celciusToFarhenheitConverter = new Interpolation(0, 32); 
		celciusToFarhenheitConverter.define(100, 212);
		
		double celcius = 37; 
		double farhenheit = celciusToFarhenheitConverter.interpolate(celcius);  
		
		msg = MessageFormat.format(" {0} Celcius equals {1} Farhenheit", celcius, farhenheit); 
		System.out.println(msg);
	}

}
