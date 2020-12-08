package com.marcosavard.commons.chem;

import java.util.Map;

public class MoleculeParserDemo {

  public static void main(String[] args) {
    MoleculeParser parser = new MoleculeParser();

    demo(parser, "H2O");
    demo(parser, "(FeO2)3");
    demo(parser, "C6H12OH");
    demo(parser, "C6H12OH(FeO)2");
    demo(parser, "(ON(SO3)2)");
    demo(parser, "K4(ON(SO3)2)2"); // fremy salt
  }

  private static void demo(MoleculeParser parser, String formula) {
    System.out.println("formula : " + formula);
    Map<String, Integer> molecule = parser.parse(formula);
    System.out.println("molecule : " + molecule);
    System.out.println();
  }

}
