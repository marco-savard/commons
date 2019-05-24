package com.marcosavard.commons.chem;

public class FormulaBuilderDemo {

	public static void main(String[] args) {
		System.out.println("List of alkanes : "); 
		
		for (int i=1; i<=10; i++) {
			FormulaBuilder builder = new FormulaBuilder(); 
			builder.add(ChemicalElement.C, i);
			builder.add(ChemicalElement.H, 2 + i * 2);
			ChemicalFormula formula = builder.build(); 
			System.out.println("  " + formula); 
		}
	}
}
