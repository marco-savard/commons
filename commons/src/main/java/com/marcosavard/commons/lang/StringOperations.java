package com.marcosavard.commons.lang;

interface StringOperations {
  public String concat(String suffix);

  public boolean endsWith(String suffix);

  public boolean equalsIgnoreCase(String that);

  public int indexOf(String str);

  public int indexOf(String str, int index);

  public int lastIndexOf(String str);

  public int lastIndexOf(String str, int index);

  public String replace(String original, String replacement);

  public String replaceAll(String original, String replacement);

  public String[] split(String delimiter);

  public boolean startsWith(String prefix);

  public String substring(int beginIndex);

  public String substring(int beginIndex, int endIndex);

  public String toUpperCase();

  public String toLowerCase();

  public String trim();
}
