package com.marcosavard.domain;

import java.util.Objects;

/**
 * Branch
 */
public class Branch {
  private Address location;
  private String name;

  /**
   * @return location
   */
  public Address getLocation() {
    return location;
  }

  /**
   * @param location
   */
  public void setLocation(Address location) {
    this.location = location;
  }

  /**
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof Branch) {
      Branch that = (Branch)other;
      equal = (hashCode() == that.hashCode()) ? isEqualTo(that) : false;
    }

    return equal;
  }

  @Override
  public int hashCode() {
    int hashCode = Objects.hash(getLocation(), getName());
    return hashCode;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("location : " + getLocationString() + ", ");
    sb.append("name : " + getNameString() + "");
    sb.append("}");
    return sb.toString();
  }

  private boolean isEqualTo(Branch thatBranch) {
    boolean equal = true;
    equal = equal && (getLocation() == null ? thatBranch.getLocation() == null : getLocation().equals(thatBranch.getLocation()));
    equal = equal && (getName() == null ? thatBranch.getName() == null : getName().equals(thatBranch.getName()));
    return equal;
  }

  private String getLocationString() {
    Object value = getLocation();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getNameString() {
    Object value = getName();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

}
