package com.marcosavard.commons.math.type;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Objects;

/**
 * Credits : https://introcs.cs.princeton.edu/java/32class/Complex.java.html 
 * TODO x + yj notation
 * methods : log, pow, modulus 
 * @author Marco
 *
 */
@SuppressWarnings("serial")
public class ComplexNumber extends Number {
	
	public static final ComplexNumber IMAGINARY_NUMBER = ComplexNumber.of(0, -1);
	private final Number real, imaginary;

	public static ComplexNumber of(Number real, Number imaginary) {
		return new ComplexNumber(real, imaginary); 
	}
	
	private ComplexNumber(Number real, Number imaginary) {
		this.real = real;
		this.imaginary = imaginary; 
	}

	public ComplexNumber addTo(ComplexNumber other) {
		Number r = real.doubleValue() + other.real.doubleValue(); 
		Number i = imaginary.doubleValue() + other.imaginary.doubleValue(); 
		ComplexNumber summation = ComplexNumber.of(r, i); 
		return summation;
	}
	
	public ComplexNumber substractFrom(ComplexNumber other) {
		Number r = real.doubleValue() - other.real.doubleValue(); 
		Number i = imaginary.doubleValue() - other.imaginary.doubleValue(); 
		ComplexNumber difference = ComplexNumber.of(r, i); 
		return difference;
	}
	
	public ComplexNumber multiplyBy(ComplexNumber other) {
		double r = (real.doubleValue() * other.real.doubleValue()) - (imaginary.doubleValue() * other.imaginary.doubleValue()); 
		double i = (real.doubleValue() * other.imaginary.doubleValue()) + (imaginary.doubleValue() * other.real.doubleValue()); 
		ComplexNumber product = ComplexNumber.of(r, i); 
		return product;
	}
	
	public ComplexNumber dividePer(ComplexNumber other) {
		ComplexNumber division = this.multiplyBy(other.inverse()); 
		return division;
	}
	
	// aka reciprocal
	public ComplexNumber inverse() {
		double r = real.doubleValue();
		double i = imaginary.doubleValue(); 
		double scale = (r * r) + (i * i);
		return ComplexNumber.of(r / scale, - i / scale); 
	}
	
    // conjugate : X-axis mirrored value
    public ComplexNumber conjugate() {
    	double i = imaginary.doubleValue(); 
        return ComplexNumber.of(real, -i);
    }

	public ComplexNumber scale(double scale) {
		double r = real.doubleValue();
		double i = imaginary.doubleValue(); 
		return ComplexNumber.of(r * scale, i * scale); 
	}
	
    // return a new Complex object whose value is the complex exponential of this
    public ComplexNumber exp() {
		double r = real.doubleValue();
		double i = imaginary.doubleValue(); 
        return ComplexNumber.of(Math.exp(r) * Math.cos(i), Math.exp(r) * Math.sin(i));
    }
    
    // return a new Complex object whose value is the complex sine of this
    public ComplexNumber sin() {
		double r = real.doubleValue();
		double i = imaginary.doubleValue(); 
        return ComplexNumber.of(Math.sin(r) * Math.cosh(i), Math.cos(r) * Math.sinh(i));
    }
    
    // return a new Complex object whose value is the complex cosine of this
    public ComplexNumber cos() {
		double r = real.doubleValue();
		double i = imaginary.doubleValue(); 
        return new ComplexNumber(Math.cos(r) * Math.cosh(i), -Math.sin(r) * Math.sinh(i));
    }
    
    // return a new Complex object whose value is the complex tangent of this
    public ComplexNumber tan() {
        return sin().dividePer(cos());
    }
    
    // return abs/modulus/magnitude
    public double abs() {
		double r = real.doubleValue();
		double i = imaginary.doubleValue(); 
        return Math.hypot(r, i);
    }

    // return angle/phase/argument, normalized to be between -pi and pi
    public double phase() {
		double r = real.doubleValue();
		double i = imaginary.doubleValue(); 
        return Math.atan2(i, r);
    }
	
	@Override 
	public boolean equals(Object other) { 
		boolean equal = (other instanceof ComplexNumber); 
		equal = equal && this.real == ((ComplexNumber)other).real; 
		equal = equal && this.imaginary == ((ComplexNumber)other).imaginary; 
		return equal; 
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(real, imaginary);
	}
	
	@Override
	public String toString() { 
		ComplexNumberFormat numberFormat = ComplexNumberFormat.ofDefaultLocale(); 
		String formatted = numberFormat.format(this); 
		return formatted; 
	}
	
	public String toDisplayString(Locale locale) { 
		ComplexNumberFormat numberFormat = ComplexNumberFormat.ofLocale(locale); 
		String formatted = numberFormat.format(this); 
		return formatted; 
	}

	public static Number valueOf(String str) {
		ComplexNumberFormat numberFormat = ComplexNumberFormat.ofDefaultLocale();  
		Number parsed = numberFormat.parse(str);
		return parsed;
	}

	@Override
	public int intValue() {
		return (int)real;
	}

	@Override
	public long longValue() {
		return (long)real;
	}

	@Override
	public float floatValue() {
		return (float)real;
	}

	@Override
	public double doubleValue() {
		return real.doubleValue();
	}

	public static class ComplexNumberFormat extends DecimalFormat {
		public static final ComplexNumberFormat DEFAULT_LOCALE_FORMAT = new ComplexNumberFormat(Locale.getDefault(), "i"); 
		private NumberFormat numberFormatter; 
		private String imaginaryPart; 
		
		public static ComplexNumberFormat ofDefaultLocale() {
			return DEFAULT_LOCALE_FORMAT;
		}
		
		public static ComplexNumberFormat ofLocale(Locale locale) {
			ComplexNumberFormat numberFormat = new ComplexNumberFormat(locale, "i"); 
			return numberFormat;
		}
		
		public static ComplexNumberFormat ofFormat(String imaginaryPart) {
			ComplexNumberFormat numberFormat = new ComplexNumberFormat(Locale.getDefault(), imaginaryPart); 
			return numberFormat;
		}
		
		public static ComplexNumberFormat ofFormat(String imaginaryPart, Locale locale) {
			ComplexNumberFormat numberFormat = new ComplexNumberFormat(locale, imaginaryPart); 
			return numberFormat;
		}
		
		//the constructor
		private ComplexNumberFormat(Locale locale, String imaginaryPart) {
			this.numberFormatter = NumberFormat.getNumberInstance(locale); 
			this.imaginaryPart = imaginaryPart;
		}

		public String format(Number number) { 
			boolean complex = (number instanceof ComplexNumber); 
			String formatted = complex ? format((ComplexNumber)number) : numberFormatter.format(number); 
			return formatted;
		}
		
		public String format(ComplexNumber number) { 
			boolean complex = (number.imaginary.doubleValue() != 0);
			String formatted = complex ? formatComplex(number) : numberFormatter.format(number.real); 
			return formatted;
		}
		
		private String formatComplex(ComplexNumber number) {
			boolean imaginary = (number.real.doubleValue() == 0); 
			String format = imaginary ? "{1}{2}" : "{0} + {1}{2}";
			String r = numberFormatter.format(number.real); 
			String i = numberFormatter.format(number.imaginary); 
			String formatted = MessageFormat.format(format, r, i, imaginaryPart); 
			return formatted;
		}
		
		@Override
		public Number parse(String text) { 
			boolean complex = text.contains(imaginaryPart); 
			String splitted[] = complex ? text.split("\\+|" + imaginaryPart) : new String[] {text}; 
			splitted = (complex && splitted.length == 1) ? new String[] {"0", splitted[0]} : splitted; 
			Number real, imaginary; 
			
			try {
				real = numberFormatter.parse(splitted[0]);  
				imaginary = complex ? super.parse(splitted[1]) : 0;
			} catch (ParseException e) {
				real = Double.NaN; 
				imaginary = Double.NaN;
			} 
			
			Number parsed = complex ? ComplexNumber.of(real, imaginary) : real;
			return parsed; 
		}
	}


}
