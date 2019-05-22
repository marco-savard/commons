package com.marcosavard.commons.money;

import java.text.MessageFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Hello world!
 *
 */
public class MoneyAmountDemo {
	
    public static void main( String[] args )  {
    	Currency usd = Currency.getInstance(Locale.US); 
    	Currency cad = Currency.getInstance(Locale.CANADA); 
    	final int SCALE = 3; 
    	
    	MoneyAmount m1 = MoneyAmount.of(10.10, usd); 
    	MoneyAmount m2 = MoneyAmount.of(10.20, usd); 
    	
    	MoneyAmount sum = m1.add(m2); 
    	String msg = MessageFormat.format("{0} + {1} = {2}", m1, m2, sum); 
    	System.out.println(msg);
    	
    	MoneyAmount sumPlusTax = sum.multiply(1.07); 
    	msg = MessageFormat.format("{0} * 1.07% = {1}", sum, sumPlusTax); 
    	System.out.println(msg);
    	
    	boolean equal = sum.equals(MoneyAmount.of(20.03, usd)); 
    	msg = MessageFormat.format("{0} + {1} = $20.30 is : {2}", m1, m2, equal); 
    	System.out.println(msg);
    	
    	MoneyAmount converted = sum.convert(1.25, cad); 
    	msg = MessageFormat.format("{0} gives {1}", sum, converted); 
    	System.out.println(msg);
    	
    	//compute simple interest
    	MoneyAmount principal = MoneyAmount.of(3000, usd); 
    	MoneyAmount earn = principal.computeSimpleInterest(0.025, 10);  
    	msg = MessageFormat.format("{0} at 2.5%/year during 10 years gives {1}", principal, earn); 
    	System.out.println(msg);
    	
    	//compute compound interest
    	principal = MoneyAmount.of(3000, usd); 
    	earn = principal.computeCompoundInterest(0.025, 12, 10, SCALE);  
    	msg = MessageFormat.format("{0} at 2.5%/month during 10 years gives {1}", principal, earn); 
    	System.out.println(msg);
    	
    	try {
    		sum = m1.add(MoneyAmount.of(1, cad)); 
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    }
}



