package com.marcosavard.commons.util;

import java.util.List;

public class Joiner {
  private String delimiter;
  private String suffix;

  public static Joiner ofDelimiter(String delimiter) {
    Joiner joiner = new Joiner(delimiter);
    return joiner;
  }

  private Joiner(String delimiter) {
    this.delimiter = delimiter;
  }

  public Joiner withSuffix(String suffix) {
    this.suffix = suffix;
    return this;
  }

  public String join(List<String> list) {
    String joined;
    int nb = list.size();

    if ((suffix != null) && (nb > 1)) {
      String last = list.get(nb - 1);
      List<String> sublist = list.subList(0, nb - 1);
      joined = String.join(delimiter, sublist);
      joined = joined + suffix + last;
    } else {
      joined = String.join(delimiter, list);
    }

    return joined;
  }

}
