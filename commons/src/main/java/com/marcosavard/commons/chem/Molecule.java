package com.marcosavard.commons.chem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Molecule {
  private final Map<ChemicalElement, Integer> atomicNumbersByElement;
  private final String text;
  private final double molecularWeight;

  private static final Pattern integerPattern = Pattern.compile("-?\\d+");

  public static Molecule of(String chemicalFormula) {
    // insert "1" in atom-atom boundary
    chemicalFormula =
        chemicalFormula.replaceAll("(?<=[A-Z])(?=[A-Z])|(?<=[a-z])(?=[A-Z])|(?<=\\D)$", "1");

    // split at letter-digit or digit-letter boundary
    String regex = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";
    String[] elementsOrNumbers = chemicalFormula.split(regex);
    Map<ChemicalElement, Integer> atomicNumbersByElement = new HashMap<>();
    ChemicalElement foundElement = null;

    for (String elementOrNumber : elementsOrNumbers) {
      boolean numeric = isNumeric(elementOrNumber);

      if (!numeric) {
        foundElement = ChemicalElement.of(elementOrNumber);

        if (foundElement == null) {
          throw new UnknownChemicalElementException(elementOrNumber);
        }
      } else {
        int atomicNumber = Integer.valueOf(elementOrNumber);
        if (foundElement != null) {
          atomicNumbersByElement.put(foundElement, atomicNumber);
        }
      }
    }

    Molecule molecule = Molecule.of(atomicNumbersByElement);
    return molecule;
  }

  public static Molecule of(Object... parameters) {
    Map<ChemicalElement, Integer> atomicNumbersByElement = new HashMap<>();
    ChemicalElement foundElement = null;

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

  public static Molecule of(Map<ChemicalElement, Integer> atomicNumbers) {
    Molecule molecule = new Molecule(atomicNumbers);
    return molecule;
  }

  private Molecule(Map<ChemicalElement, Integer> atomicNumbersByElement) {
    this.atomicNumbersByElement = atomicNumbersByElement;
    StringBuilder builder = new StringBuilder();

    List<ChemicalElement> orderedElements = new ArrayList<>(atomicNumbersByElement.keySet());
    Comparator<ChemicalElement> comparator = new HillSystemChemicalElementComparator();
    orderedElements.sort(comparator);
    double totalWeight = 0.0;

    for (ChemicalElement element : orderedElements) {
      Integer atomicNumber = atomicNumbersByElement.get(element);
      builder.append(element.toString());
      builder.append((atomicNumber == 1) ? "" : atomicNumber.toString());

      totalWeight += element.getAtomicWeight() * atomicNumber;
    }

    text = builder.toString();
    molecularWeight = totalWeight;
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
    return text;
  }

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

  private static boolean isNumeric(String strNum) {
    if (strNum == null) {
      return false;
    }
    return integerPattern.matcher(strNum).matches();
  }

  public double getMolecularWeight() {
    return molecularWeight;
  }



}
