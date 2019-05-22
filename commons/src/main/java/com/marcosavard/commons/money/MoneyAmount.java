package com.marcosavard.commons.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Currency;

public class MoneyAmount {
	private BigDecimal value; 
	private Currency currency; 

	public static MoneyAmount of(double value, Currency currency) {
		return new MoneyAmount(value, currency); 
	}
	
	public static MoneyAmount of(BigDecimal value, Currency currency) {
		return new MoneyAmount(value, currency); 
	}
		
	public MoneyAmount add(MoneyAmount that) {
		verifyCurrency(that, "Adding {0} to {1} is not allowed"); 
		
		BigDecimal addition = value.add(that.value);  
		return MoneyAmount.of(addition, currency); 
	}	

	public MoneyAmount subtract(MoneyAmount that) {
		verifyCurrency(that, "Substracting {0} from {1} is not allowed"); 
		
		BigDecimal subtraction = value.subtract(that.value);  
		return MoneyAmount.of(subtraction, currency); 
	}
	
	public MoneyAmount multiply(double multiplicant) {
		BigDecimal product = value.multiply(BigDecimal.valueOf(multiplicant)); 
		return MoneyAmount.of(product, currency);
	}
	
	public MoneyAmount divide(double divisor, int scale) {
		BigDecimal division = value.divide(BigDecimal.valueOf(divisor), scale, RoundingMode.HALF_EVEN); 
		return MoneyAmount.of(division, currency);
	}
	
	public MoneyAmount computeSimpleInterest(double interestRate, int periods) {
		BigDecimal interest = this.value.multiply(BigDecimal.valueOf(interestRate * periods)); 
		BigDecimal principalWithInterest = this.value.add(interest); 
		return MoneyAmount.of(principalWithInterest, currency);
	}
	
	public MoneyAmount computeCompoundInterest(double interestRate, int frequency, int periods, int scale) {
		BigDecimal quotient = BigDecimal.valueOf(interestRate).divide(BigDecimal.valueOf(frequency), scale, RoundingMode.HALF_EVEN);
		BigDecimal base = quotient.add(BigDecimal.valueOf(1.0)); 
		BigDecimal interest = base.pow(frequency * periods); 
		BigDecimal principalWithInterest = this.value.multiply(interest); 
		return MoneyAmount.of(principalWithInterest, currency); 
	}
	
	public MoneyAmount convert(double conversionRate, Currency targetCurrency) {
		BigDecimal product = value.multiply(BigDecimal.valueOf(conversionRate)); 
		return MoneyAmount.of(product, targetCurrency);
	}
	
	public int compareTo(MoneyAmount that) {
		verifyCurrency(that, "Comparing {0} to {1} is not allowed"); 
	
		int comparison = value.compareTo(that.value); 
		return comparison;
	}
	
	@Override
	public boolean equals(Object that) {
		boolean equal = false; 
		
		if (that instanceof MoneyAmount) {
			MoneyAmount thatAmount = (MoneyAmount)that;
			equal = value.equals(thatAmount.value); 
		}
		
		return equal;
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	@Override
	public String toString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		formatter.setCurrency(currency);
		String formatted = formatter.format(value); 
		return formatted; 
	}
	
	//
	// private 
	//
	
	private MoneyAmount(double doubleValue, Currency currency) {
		value = BigDecimal.valueOf(doubleValue); 
		this.currency = currency;
	}

	private MoneyAmount(BigDecimal bigDecimal, Currency currency) {
		value = bigDecimal; 
		this.currency = currency;
	}
	
	private void verifyCurrency(MoneyAmount that, String pattern) {
		if (! this.currency.equals(that.currency)) {
			String msg = MessageFormat.format(pattern, that, this); 
			throw new ArithmeticException(msg); 
		}
	}








}
