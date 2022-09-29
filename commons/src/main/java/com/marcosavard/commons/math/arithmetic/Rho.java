package com.marcosavard.commons.math.arithmetic;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.math.Range;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Rho {
	List<Integer> list;
	int[] dimensions;

	
	public static Rho of(Range range, int... dim) {
		return new Rho(range, dim);
	}
	
	public static Rho of(List<Integer> list, int... dim) {
		return new Rho(list, dim);
	}
	
	private Rho(List<Integer> list, int... dim) {
		this.list = list;
		this.dimensions = dim;
	}
	
	public List<List<Integer>> toList() {
		int len = Math.min(list.size(), product(dimensions));
		List displayList = list.subList(0,  len); 
		
		for (int dim = dimensions.length - 1; dim >= 0; dim--) { 
			displayList = split(displayList, dimensions[dim]); 
		}
		
		return displayList;
	}
	
	public List<List<Integer>> toListOld() {
		for (int dimension : dimensions) {
			List rows = split(list, dimension); 
			
			List row = list.subList(0, dimension-1);
			Console.println(row);
		}
		Console.println(); 
		
		List<List<Integer>> nested = new ArrayList<>(); 
		List<Integer> row = new ArrayList<>(); 
		row.add(3);
		nested.add(row); 
		return nested;
	}



	public int[] toArray() {
		int[] array = new int[3];
		return array;
	}
	
	@Override
	public String toString() {
		return toString("0");
	}
	
	public String toString(String decimalFormat) {
		int len = Math.min(list.size(), product(dimensions));
		List displayList = list.subList(0,  len); 
		
		for (int dim = dimensions.length - 1; dim >= 0; dim--) { 
			displayList = split(displayList, dimensions[dim]); 
		}
		
		return toString(displayList, decimalFormat); 
	}
	
	private String toString(List displayList, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);
		return toString(displayList, df);
	}
	
	private String toString(List displayList, DecimalFormat decimalFormat) {
		List<String> strings = new ArrayList<>();
		
		for (Object item : displayList) {
			if (item instanceof List) {
				String str = "[" + toString((List)item, decimalFormat) + "]";
				strings.add(str);
			} else if (item instanceof Number) {
				Number number = (Number)item;
				String formatted = decimalFormat.format(number); 
				strings.add(formatted);
			}
		}
		
		return String.join(" ", strings) ;
	}

	private int product(int[] array) {
		int product = 1;
		for (int dim = 0; dim < dimensions.length; dim++) { 
			product *= dimensions[dim]; 
		}
		
		return product;
	}

	private List split(List list, int length) {
		List<List> splitted = new ArrayList<>(); 
		
		int nb = (list.size() + length -1) / length; 
		for (int i=0; i<nb; i++) {
			List row = list.subList(i*length, Math.min((i+1)*length, list.size()));
			splitted.add(row);
		}
		
		return splitted;
	}

}
