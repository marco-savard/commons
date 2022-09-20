package com.marcosavard.commons.lang;

import org.junit.Test;

import junit.framework.Assert;

public class StringUtilTest {
	
	@Test
	public void testEqualsIgnoreAccents() {
		/*
		boolean result = StringUtil.equalsIgnoreAccents("montreal", "montr�al"); 
		boolean expected = true; 
		Assert.assertEquals(expected, result);
		
		result = StringUtil.equalsIgnoreAccents("montreal", "Montr�al"); 
		expected = false; 
		Assert.assertEquals(expected, result);
		
		result = StringUtil.equalsIgnoreCaseAndAccents("montreal", "Montr�al"); 
		expected = true; 
		Assert.assertEquals(expected, result);*/
	}
	
	@Test
	public void testEqualsIgnoreAccentsNullsafe() {
		boolean result = StringUtil.equalsIgnoreCaseAndAccents("montreal", null); 
		boolean expected = false; 
		Assert.assertEquals(expected, result);
		
		result = StringUtil.equalsIgnoreCaseAndAccents(null, "Montr�al"); 
		expected = false; 
		Assert.assertEquals(expected, result);
		
		result = StringUtil.equalsIgnoreCaseAndAccents("", null); 
		expected = false; 
		Assert.assertEquals(expected, result);
		
		result = StringUtil.equalsIgnoreCaseAndAccents(null, null); 
		expected = true; 
		Assert.assertEquals(expected, result);
	}

}
