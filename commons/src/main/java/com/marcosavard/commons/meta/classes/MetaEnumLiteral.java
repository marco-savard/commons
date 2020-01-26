package com.marcosavard.commons.meta.classes;

public class MetaEnumLiteral {
  private String name;

  public MetaEnumLiteral(MetaEnum owner, String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
