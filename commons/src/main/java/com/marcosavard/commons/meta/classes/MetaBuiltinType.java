package com.marcosavard.commons.meta.classes;

import java.text.MessageFormat;

public class MetaBuiltinType extends MetaDataType {
  private static MetaPackage builtinPackage = null;

  public MetaBuiltinType(MetaModel model, String name) {
    super(getBuiltinPackage(model), name);
  }

  private static MetaPackage getBuiltinPackage(MetaModel model) {
    if (builtinPackage == null) {
      builtinPackage = new MetaPackage(model, "java.lang");
    }

    return builtinPackage;
  }

  @Override
  public String toString() {
    String msg = MessageFormat.format("data type {0}", this.getName());
    return msg;
  }

}
