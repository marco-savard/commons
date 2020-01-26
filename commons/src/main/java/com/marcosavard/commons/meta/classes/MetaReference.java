package com.marcosavard.commons.meta.classes;

public class MetaReference extends MetaStructuralFeature {
  public enum ReferenceType {
    COMPOSITION, AGGREGATION
  }

  private MetaClass owner;
  private String fieldName;
  private MetaClass opposite;
  private ReferenceType referenceType;

  public MetaReference(MetaClass owner, String name, MetaClass opposite,
      ReferenceType referenceType) {
    super(owner, name);
    this.opposite = opposite;
    this.referenceType = referenceType;
  }

  @Override
  public String getTypeName() {
    return opposite.getName();
  }

  public ReferenceType getReferenceType() {
    return referenceType;
  }

  public boolean isComposition() {
    return referenceType == ReferenceType.COMPOSITION;
  }

}
