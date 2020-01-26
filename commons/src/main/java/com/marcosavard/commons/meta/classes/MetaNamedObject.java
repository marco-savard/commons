package com.marcosavard.commons.meta.classes;

public abstract class MetaNamedObject {
  private String name;
  private String description;

  protected MetaNamedObject(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    if (description == null) {
      description = buildDefaultDescription();
    }

    return description;
  }

  private String buildDefaultDescription() {
    return name;
  }

}
