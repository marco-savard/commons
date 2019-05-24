package com.marcosavard.commons.chem;

import java.util.List;

public class ChemicalElementDemo {

	public static void main(String[] args) {
		for (int i=0; i<5; i++) {
			List<ChemicalElement> period = ChemicalElement.getPeriod(i+1); 
			
			for (int j=0; j<period.size(); j++) {
				String name = period.get(j).name(); 
				String sep = (name.length() == 1) ? "   " : "  "; 
				System.out.print(name  + sep);
			}
			
			System.out.println();
		}
	}
}
