package com.marcosavard.commons.meta.classes;

public class MetaPackage {
  private MetaModel model;
  private String packageName;

  public MetaPackage(MetaModel model, String packageName) {
    this.model = model;
    this.packageName = packageName;
  }

  public MetaModel getModel() {
    return model;
  }

  public String getName() {
    return packageName;
  }

}
