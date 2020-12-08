package com.marcosavard.commons.chem;

public class FormulaFinderDemo {

  public static void main(String[] args) {
    FormulaFinder formulaFinder = new FormulaFinder();
    formulaFinder.addWeight(25.3, ChemicalElement.Ca);
    formulaFinder.addWeight(39.2, ChemicalElement.P);
    formulaFinder.addWeight(35.5, ChemicalElement.O);
    Molecule molecule = formulaFinder.findFormula();
    System.out.println("molecule : " + molecule);

  }

}
