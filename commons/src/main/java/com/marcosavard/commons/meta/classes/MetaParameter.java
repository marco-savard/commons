package com.marcosavard.commons.meta.classes;

public class MetaParameter {
  private String name;
  private MetaDataType type;
  private boolean readonly;
  private boolean required;

  public MetaParameter(String name, MetaDataType type) {
    this.name = name;
    this.type = type;
  }

  public void setReadOnly(boolean readonly) {
    this.readonly = readonly;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public String getTypeName() {
    return type.getName();
  }

  public String getName() {
    return name;
  }

  public MetaDataType getType() {
    return this.type;
  }

  @Override
  public String toString() {
    String s = getName();
    return s;
  }

  public boolean isReadOnly() {
    return readonly;
  }

  public boolean isRequired() {
    return required;
  }



}
