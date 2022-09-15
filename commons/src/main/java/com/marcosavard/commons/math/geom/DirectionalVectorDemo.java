package com.marcosavard.commons.math.geom;

import java.text.MessageFormat;

import com.marcosavard.commons.math.geom.DirectionalVector.AngleUnit;

public class DirectionalVectorDemo {

	public static void main(String[] args) {
		DirectionalVector boatVelocity = DirectionalVector.ofPolarCoordinates(20, 0, AngleUnit.DEGREES);
		DirectionalVector currentVelocity = DirectionalVector.ofPolarCoordinates(15, 90, AngleUnit.DEGREES);
		DirectionalVector sum = currentVelocity.add(boatVelocity); 
		
		String msg = MessageFormat.format("{0} + {1} = {2}", currentVelocity, boatVelocity, sum );
		System.out.println(msg);
		
		msg = MessageFormat.format("len={0}, angle={1}}", sum.getLength(), sum.getAngleInDegrees());
		System.out.println(msg);
	}

}
