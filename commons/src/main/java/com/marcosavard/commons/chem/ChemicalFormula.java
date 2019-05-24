package com.marcosavard.commons.chem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ChemicalFormula {
	private final Map<ChemicalElement, Integer> atomicNumbersByElement;
	private final String text; 
	
	public ChemicalFormula(Map<ChemicalElement, Integer> atomicNumbersByElement) {
		this.atomicNumbersByElement = atomicNumbersByElement; 
		StringBuilder builder = new StringBuilder(); 
		
		List<ChemicalElement> orderedElements = new ArrayList(atomicNumbersByElement.keySet()); 
		Comparator<ChemicalElement> comparator = new HillSystemChemicalElementComparator(); 
		orderedElements.sort(comparator);
		
		for (ChemicalElement element : orderedElements) { 
			Integer atomicNumber = atomicNumbersByElement.get(element); 
			builder.append(element.toString());
			builder.append((atomicNumber == 1) ? "" :  atomicNumber.toString());
		}
		
		text = builder.toString(); 
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	private static class HillSystemChemicalElementComparator implements Comparator<ChemicalElement> {
		@Override
		public int compare(ChemicalElement element1, ChemicalElement element2) {
			if (element1 == ChemicalElement.C) {
				return -1; 
			} else if (element2 == ChemicalElement.C) { 
				return 1;
			} else if (element1 == ChemicalElement.H) { 
				return -1; 
			} else if (element2 == ChemicalElement.H) { 
				return 1;
			} else {
				return element1.name().compareTo(element2.name()); 
			}
		}	
	}

}
