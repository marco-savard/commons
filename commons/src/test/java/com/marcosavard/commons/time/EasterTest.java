package com.marcosavard.commons.time;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Assert;
import org.junit.Test;

public class EasterTest {
	
	public static void main(String[] args) {
		EasterTest test = new EasterTest();
		test.testEaster();
	}

	@Test
	public void testEaster() {
		//Easter 1818 is expected to occur March 22nd (earliest possible date)
		LocalDate easter1818 = Easter.getEasterLocalDate(1818); 
		Assert.assertEquals(easter1818.getMonth(), Month.MARCH);
		Assert.assertEquals(easter1818.getDayOfMonth(), 22);
		
		//Easter 2000 is expected to occur April 1st
		LocalDate easter2000 = Easter.getEasterLocalDate(2000); 
		Assert.assertEquals(easter2000.getMonth(), Month.APRIL);
		Assert.assertEquals(easter2000.getDayOfMonth(), 23);
		
		//Easter 2008 is expected to occur March 23rd
		LocalDate easter2008 = Easter.getEasterLocalDate(2008); 
		Assert.assertEquals(easter2008.getMonth(), Month.MARCH);
		Assert.assertEquals(easter2008.getDayOfMonth(), 23);
		
		//Easter 2018 is expected to occur April 1st
		LocalDate easter2018 = Easter.getEasterLocalDate(2018); 
		Assert.assertEquals(easter2018.getMonth(), Month.APRIL);
		Assert.assertEquals(easter2018.getDayOfMonth(), 1);
		
		//Easter 2019 is expected to occur April 21st
		LocalDate easter2019 = Easter.getEasterLocalDate(2019); 
		Assert.assertEquals(easter2019.getMonth(), Month.APRIL);
		Assert.assertEquals(easter2019.getDayOfMonth(), 21);
	}

}
