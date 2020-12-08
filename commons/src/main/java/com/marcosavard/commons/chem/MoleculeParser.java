package com.marcosavard.commons.chem;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoleculeParser {
  private Pattern pattern = Pattern.compile("([(-)])|([A-Z][a-z]*)|([0-9]*)");

  public Map<String, Integer> parse(String formula) {
    List<Object> list = formulaToList(formula);
    list = unflatten(list, "()");
    list = normalize(list);
    list = factorize(list, 1);
    Map<String, Integer> molecule = countAtoms(list);
    return molecule;
  }

  // parse the formula and return a list of strings
  // (FeO2)3 -> [(, Fe, O, 2, ), 3]
  private List<Object> formulaToList(String formula) {
    Matcher m = pattern.matcher(formula);
    List<Object> list = new ArrayList<>();

    while (m.find()) {
      Object value = getNotNullValue(m);

      if (value != null) {
        list.add(value);
      }
    }

    return list;
  }

  // (, Fe, O, 2, ), 3] -> [[Fe, O, 2], 3]
  private List<Object> unflatten(List<Object> original, String separators) {
    String opening = String.valueOf(separators.charAt(0));
    String closing = String.valueOf(separators.charAt(1));
    List<Object> target = new ArrayList<>();
    Deque<List<Object>> stack = new ArrayDeque<>();
    stack.add(target);

    for (Object item : original) {
      if (opening.equals(item)) {
        List<Object> current = stack.peekLast();
        List<Object> sublist = new ArrayList<>();
        current.add(sublist);
        stack.add(sublist);
      } else if (closing.equals(item)) {
        stack.pollLast();
      } else {
        List<Object> current = stack.peekLast();
        current.add(item);
      }
    }
    return target;
  }

  @SuppressWarnings("unchecked")
  private List<Object> normalize(List<Object> original) {
    List<Object> normalized = new ArrayList<>();

    for (int i = 0; i < original.size(); i++) {
      Object current = original.get(i);

      if (current instanceof List) {
        normalized.add(normalize((List<Object>) current));
      } else {
        normalized.add(current);
      }

      boolean last = (i == original.size() - 1);
      Object next = last ? null : original.get(i + 1);

      if (!(current instanceof Integer) && !(next instanceof Integer)) {
        normalized.add(1);
      }
    }

    return normalized;
  }

  private List<Object> factorizeList(List<Object> original, int factor) {
    List<Object> factorized = new ArrayList<>();
    Object previous = null;

    for (Object item : original) {
      if (item instanceof Integer) {
        int product = factor * (Integer) item;
        List<Object> factorizedItem = factorize(previous, product);
        factorized.addAll(factorizedItem);
      } else {
        previous = item;
      }
    }

    return factorized;
  }

  private List<Object> factorize(Object item, int factor) {
    List<Object> factorized = new ArrayList<>();

    if (item instanceof List) {
      List list = factorizeList((List) item, factor);
      factorized.addAll(list);
    } else if (item instanceof String) {
      factorized.add((String) item);
      factorized.add(factor);
    }

    return factorized;
  }

  private Map<String, Integer> countAtoms(List<Object> list) {
    Map<String, Integer> molecule = new TreeMap<>();
    String previous = null;

    for (Object item : list) {
      if (item instanceof String) {
        previous = (String) item;
      } else if (item instanceof Integer) {
        int factor = (Integer) item;
        Integer previousCount = molecule.get(previous);
        int count = (previousCount == null) ? factor : factor + previousCount;
        molecule.put(previous, count);
      }
    }

    return molecule;
  }

  private Object getNotNullValue(Matcher m) {
    String s1 = m.group(1);
    String s2 = m.group(2);
    String s3 = m.group(3);
    Object value = null;

    if ((s1 != null) && !s1.isEmpty()) {
      value = s1;
    } else if ((s2 != null) && !s2.isEmpty()) {
      value = s2;
    } else if ((s3 != null) && !s3.isEmpty()) {
      value = Integer.valueOf(s3);
    }

    return value;
  }
}
