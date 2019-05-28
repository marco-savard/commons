package com.marcosavard.commons.geom;

import java.text.MessageFormat;

import com.marcosavard.commons.geom.Vector.AngleUnit;

public class VectorDemo {

	public static void main(String[] args) {
		Vector boatVelocity = Vector.ofPolarCoordinates(20, 0, AngleUnit.DEGREES);
		Vector currentVelocity = Vector.ofPolarCoordinates(15, 90, AngleUnit.DEGREES);
		Vector sum = currentVelocity.add(boatVelocity); 
		
		String msg = MessageFormat.format("{0} + {1} = {2}", currentVelocity, boatVelocity, sum );
		System.out.println(msg);
		
		msg = MessageFormat.format("len={0}, angle={1}}", sum.getLength(), sum.getAngleInDegrees());
		System.out.println(msg);
	}

}
