package com.marcosavard.commons.chem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Molecule {
  private final Map<ChemicalElement, Integer> atomicNumbersByElement;
  private final double molecularWeight;
  private final String name;

  public static Molecule of(String formula) {
    MoleculeParser parser = new MoleculeParser();
    Map<String, Integer> atoms = parser.parse(formula);
    Molecule molecule = Molecule.ofNamedElements(atoms);
    return molecule;
  }

  private static Molecule ofNamedElements(Map<String, Integer> namedElements) {
    Map<ChemicalElement, Integer> atomicNumbersByElement = toElements(namedElements);
    Molecule molecule = Molecule.of(atomicNumbersByElement);
    return molecule;
  }

  private static Map<ChemicalElement, Integer> toElements(Map<String, Integer> namedElements) {
    Map<ChemicalElement, Integer> elements = new HashMap<>();

    for (String namedElement : namedElements.keySet()) {
      ChemicalElement element = ChemicalElement.of(namedElement);

      if (element == null) {
        throw new UnknownChemicalElementException(namedElement);
      }

      int atomicNumber = namedElements.get(namedElement);
      elements.put(element, atomicNumber);
    }

    return elements;
  }

  public static Molecule of(ChemicalElement element, Object... others) {
    Map<ChemicalElement, Integer> atomicNumbersByElement = new HashMap<>();
    ChemicalElement foundElement = null;
    List<Object> parameters = new ArrayList<>();
    parameters.add(element);

    for (Object other : others) {
      parameters.add(other);
    }

    for (Object parameter : parameters) {
      if (parameter instanceof ChemicalElement) {
        foundElement = (ChemicalElement) parameter;
      } else if (parameter instanceof Integer) {
        atomicNumbersByElement.put(foundElement, (Integer) parameter);
        foundElement = null;
      }
    }

    if (foundElement != null) {
      atomicNumbersByElement.put(foundElement, 1);
    }

    Molecule molecule = Molecule.of(atomicNumbersByElement);
    return molecule;
  }

  public static Molecule of(Map<ChemicalElement, Integer> atomicNumbersByElement) {
    Molecule molecule = new Molecule(atomicNumbersByElement);
    return molecule;
  }


  private Molecule(Map<ChemicalElement, Integer> atomicNumbersByElement) {
    this.atomicNumbersByElement = atomicNumbersByElement;
    this.molecularWeight = computeMolecularWeight(atomicNumbersByElement);
    this.name = buildMoleculeName(atomicNumbersByElement);
  }

  private static double computeMolecularWeight(Map<ChemicalElement, Integer> elements) {
    double totalWeight = 0.0;

    for (ChemicalElement element : elements.keySet()) {
      Integer atomicNumber = elements.get(element);
      totalWeight += element.getAtomicWeight() * atomicNumber;
    }

    return totalWeight;
  }

  private static String buildMoleculeName(Map<ChemicalElement, Integer> elements) {
    List<ChemicalElement> orderedElements = new ArrayList<>(elements.keySet());
    Comparator<ChemicalElement> comparator = new HillSystemChemicalElementComparator();
    orderedElements.sort(comparator);
    StringBuilder builder = new StringBuilder();

    for (ChemicalElement element : orderedElements) {
      Integer atomicNumber = elements.get(element);
      builder.append(element.toString());
      builder.append((atomicNumber == 1) ? "" : atomicNumber.toString());
    }

    String name = builder.toString();
    return name;
  }

  public double getMolecularWeight() {
    return molecularWeight;
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof Molecule) {
      Molecule that = (Molecule) other;
      equal = atomicNumbersByElement.equals(that.atomicNumbersByElement);
    }

    return equal;
  }

  @Override
  public int hashCode() {
    return atomicNumbersByElement.hashCode();
  }

  @Override
  public String toString() {
    return name;
  }

  public String toLongString() {
    return name + " - " + molecularWeight + " g/mol";
  }

  // inner class
  private static class HillSystemChemicalElementComparator implements Comparator<ChemicalElement> {
    @Override
    public int compare(ChemicalElement element1, ChemicalElement element2) {
      int group1 = element1.getAtomicGroup();
      int group2 = element2.getAtomicGroup();

      if (element1 == ChemicalElement.C) {
        return -1;
      } else if (element2 == ChemicalElement.C) {
        return 1;
      } else if (element1 == ChemicalElement.H) {
        return -1;
      } else if (element2 == ChemicalElement.H) {
        return 1;
      } else if (group1 != group2) {
        return group1 - group2;
      } else {
        return element1.name().compareTo(element2.name());
      }
    }
  }

}
