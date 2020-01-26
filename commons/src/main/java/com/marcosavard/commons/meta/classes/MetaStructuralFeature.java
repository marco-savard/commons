package com.marcosavard.commons.meta.classes;

public abstract class MetaStructuralFeature extends MetaNamedObject {
  private MetaClass owner;
  private String value;
  private MetaVisibility visibility;
  private boolean isStatic = false;
  private boolean isFinal = false;
  private boolean readonly = false;
  private boolean required = false;
  private boolean collection = false;

  protected MetaStructuralFeature(MetaClass owner, String name) {
    super(name);
    this.owner = owner;
    this.collection = collection;
  }

  public abstract String getTypeName();

  public boolean isCollection() {
    return collection;
  }

  public void setCollection(boolean collection) {
    this.collection = collection;
  }

  public MetaVisibility getVisibility() {
    return this.visibility;
  }

  public String getVisibilityModifier() {
    String modifier;

    if (this.visibility == MetaVisibility.PUBLIC) {
      modifier = "public";
    } else if (this.visibility == MetaVisibility.PROTECTED) {
      modifier = "protected";
    } else if (this.visibility == MetaVisibility.PRIVATE) {
      modifier = "private";
    } else {
      modifier = "";
    }

    return modifier;
  }

  public void setVisibility(MetaVisibility visibility) {
    this.visibility = visibility;
  }

  public boolean isStatic() {
    return isStatic;
  }

  public void setStatic(boolean isStatic) {
    this.isStatic = isStatic;
  }

  public boolean isFinal() {
    return isFinal;
  }

  public void setFinal(boolean isFinal) {
    this.isFinal = isFinal;
  }

  public boolean isConstant() {
    return (isStatic && isFinal);
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean isReadOnly() {
    return readonly;
  }

  public void setReadOnly(boolean readonly) {
    this.readonly = readonly;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public String getGetterName() {
    String typename = getTypeName();
    String verb = "boolean".equals(typename) ? "is" : "get";
    String getterName = verb + capitalize(getName());
    return getterName;
  }

  private String capitalize(String name) {
    String capitalized = Character.toUpperCase(name.charAt(0)) + name.substring(1);
    return capitalized;
  }

  public boolean isRequired() {
    return required;
  }

  public MetaClass getOwner() {
    return owner;
  }



}
