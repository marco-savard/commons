package com.marcosavard.commons.chem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FormulaFinder {
	private Map<ChemicalElement, Double> weights = new HashMap<>(); 

	public void addWeight(double quantity, ChemicalElement ca) {
		weights.put(ca, quantity); 
	}

	public Molecule findFormula() {
		Map<ChemicalElement, Double> relativeAmountByElement = new HashMap<>(); 
		
		for (ChemicalElement element : weights.keySet()) {
			double weight = weights.get(element); 
			double atomicWeight = element.getAtomicWeight(); 
			double weightInMoles = weight / atomicWeight; 
			relativeAmountByElement.put(element, weightInMoles); 
		}
		
		Double lowestWeight = Collections.min(relativeAmountByElement.values()); 
		Map<ChemicalElement, Double> atomicNumberByElement = new HashMap<>(); 
		int factor = 1;
		
		for (ChemicalElement element : relativeAmountByElement.keySet()) {
			double weightInMoles = relativeAmountByElement.get(element); 
			double atomicNumber = weightInMoles / lowestWeight; 
			atomicNumber = Math.round(atomicNumber * 10.0) / 10.0; 
			atomicNumberByElement.put(element, atomicNumber); 
			double remainder = atomicNumber - Math.floor(atomicNumber); 
			
			if (remainder == 0.5) {
				factor = 2;
			}
		}
		
		MoleculeBuilder formulaBuilder = new MoleculeBuilder(); 
		
		for (ChemicalElement element : atomicNumberByElement.keySet()) {
			double atomicNumber = atomicNumberByElement.get(element) * factor; 
			atomicNumberByElement.put(element, atomicNumber);
			formulaBuilder.add(element, (int)atomicNumber); 
		}
		
		Molecule formula = formulaBuilder.build(); 
		return formula;
	}

}
