package com.marcosavard.commons.chem;

public class MoleculeBuilderDemo {

  public static void main(String[] args) {
    System.out.println("List of alkanes : ");

    for (int i = 1; i <= 10; i++) {
      int nbCarbons = i;
      int nhHydrogens = 2 + i * 2;
      MoleculeBuilder builder = new MoleculeBuilder();
      builder.add(ChemicalElement.C, nbCarbons);
      builder.add(ChemicalElement.H, nhHydrogens);
      Molecule formula = builder.build();
      System.out.println("  " + formula);
    }
  }
}
