package com.marcosavard.commons.chem;

import java.util.HashMap;
import java.util.Map;

public class MoleculeBuilder {
  private Map<ChemicalElement, Integer> atomicNumbers = new HashMap<>();

  public void add(ChemicalElement element, int atomicNumber) {
    atomicNumbers.put(element, atomicNumber);
  }

  public Molecule build() {
    Molecule formula = Molecule.of(atomicNumbers);
    return formula;
  }

}
