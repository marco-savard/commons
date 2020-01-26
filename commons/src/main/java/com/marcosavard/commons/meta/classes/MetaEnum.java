package com.marcosavard.commons.meta.classes;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MetaEnum extends MetaDataType {
  private List<MetaEnumLiteral> literals = new ArrayList<>();

  public MetaEnum(MetaPackage pack, String name) {
    super(pack, name);
  }

  public void createLiteral(String name) {
    MetaEnumLiteral literal = new MetaEnumLiteral(this, name);
    literals.add(literal);
  }

  public List<MetaEnumLiteral> getLiterals() {
    return literals;
  }

  @Override
  public String toString() {
    String msg = MessageFormat.format("enum {0}", this.getName());
    return msg;
  }



}
