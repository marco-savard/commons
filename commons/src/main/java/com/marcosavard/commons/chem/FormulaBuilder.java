package com.marcosavard.commons.chem;

import java.util.HashMap;
import java.util.Map;

public class FormulaBuilder {
	private Map<ChemicalElement, Integer> atomicNumbers = new HashMap();

	public void add(ChemicalElement element, int atomicNumber) {
		atomicNumbers.put(element, atomicNumber); 
	}

	public ChemicalFormula build() {
		ChemicalFormula formula = new ChemicalFormula(atomicNumbers);
		return formula;
	}

}
