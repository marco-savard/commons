package com.marcosavard.commons.chem;

import java.text.MessageFormat;
import java.util.List;

public class ChemicalElementDemo {

	public static void main(String[] args) {
		listElements(); 
		printPeriodicTable(); 
	}

	private static void listElements() {
		ChemicalElement[] elements = ChemicalElement.values(); 
		System.out.println("List of Chemical Elements");
		
		for (ChemicalElement element : elements) {
			String name = element.name(); 
			ChemicalElement.Category category = element.getCategory(); 
			String msg = MessageFormat.format("  {0} {1} {2}", name, element.getAtomicGroup(), category); 
			System.out.println(msg);
		}
		System.out.println();
	}

	private static void printPeriodicTable() {
		System.out.println("Periodic Table of Elements");
		
		for (int i=0; i<5; i++) {
			List<ChemicalElement> period = ChemicalElement.getPeriod(i+1); 
			
			for (int j=1; j<=ChemicalElement.NOBLE_GAS_GROUP; j++) {
				ChemicalElement element = ChemicalElement.findElement(period, j); 
				String name = (element == null) ? "" : element.name();
				name = padRight(name, 4); 
				
				String sep = "    ".substring(0, 4 - name.length()); 
				System.out.print(name  + sep);
			}
			
			System.out.println();
		}
		
		System.out.println();
	}

	private static String padRight(String original, int len) {
		String padded = String.format("%-" + len + "s", original);  
		return padded;
	}
}
