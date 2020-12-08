package com.marcosavard.commons.chem;

import java.text.MessageFormat;

public class MoleculeDemo {

  public static void main(String[] args) {
    printProperties(Molecule.of("(OH)2O"));
    printProperties(Molecule.of("CH4"));
    printProperties(Molecule.of("Ca2O7P4"));
    printProperties(Molecule.of("NaCl"));

    ChemicalElement H = ChemicalElement.H;
    ChemicalElement O = ChemicalElement.O;
    compareMolecules(Molecule.of("H2O"), Molecule.of(H, 2, O));

    // the 2nd does not follow Hill naming convention
    compareMolecules(Molecule.of("CH3NO2"), Molecule.of("NO2H3C"));
  }

  private static void printProperties(Molecule molecule) {
    System.out.println(molecule.toLongString());
  }

  private static void compareMolecules(Molecule molecule1, Molecule molecule2) {
    boolean equal = molecule1.equals(molecule2);
    String msg = MessageFormat.format("{0} same as {1} : {2}", molecule1, molecule2, equal);
    System.out.println(msg);
  }

}
