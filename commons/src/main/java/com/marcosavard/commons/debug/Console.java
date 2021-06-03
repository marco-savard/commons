package com.marcosavard.commons.debug;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Arrays;

import com.marcosavard.commons.util.ToStringBuilder;

public class Console {
  private static PrintStream output = System.out; 
  
  public static void setOutput(PrintStream printStream) {
	  output = printStream;
  }

  public static void println(double value) {
	 output.printf("%.3f", value);
	 output.println();
  }
    
  public static void println(int[] array) {
	String s = Arrays.toString(array);   
	output.println(s);
  }
  
  public static void println(long[] array) {
	String s = Arrays.toString(array);   
	output.println(s);
  }
  
  public static void println(short[] array) {
	String s = Arrays.toString(array);   
	output.println(s);
  }
  
  public static void println(int[][] array) {
	String s = Arrays.deepToString(array);   
	output.println(s);
  }
  
  public static void println(long[][] array) {
	String s = Arrays.deepToString(array);   
	output.println(s);
  }
  
  public static void println(short[][] array) {
	String s = Arrays.deepToString(array);   
	output.println(s);
  }
  
  public static <T> void println(T[] array) {
	String s = Arrays.toString(array);   
	output.println(s);
  }
  
  public static void println(Object value) {
	  String str = ToStringBuilder.build(value); 
	  output.println(str);
  }

  public static void println(String pattern, Object... parameters) {
    String line = MessageFormat.format(pattern, parameters);
    output.println(line);
  }

  public static void println() {
	  output.println();
  }

}
