package com.marcosavard.domain;

import java.lang.NoSuchFieldException;
import java.lang.reflect.Field;
import java.util.Objects;

public class Branch {
  private Address location;
  private String name;
  
  public static final Field LOCATION;
  public static final Field NAME;
  
  static {
    try {
      LOCATION = Branch.class.getDeclaredField("location");
      NAME = Branch.class.getDeclaredField("name");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * @return location Address
   */
  public Address getLocation() {
    return location;
  }
  
  /**
   * @param location Address
   */
  public void setLocation(Address location) {
    this.location = location;
  }
  
  /**
   * @return name String
   */
  public String getName() {
    return name;
  }
  
  /**
   * @param name String
   */
  public void setName(String name) {
    this.name = name;
  }
  
  
  public static Field[] getFields() {
    return new Field[] {LOCATION, NAME, };
  }
  
  /**
   * @param field
   * @return the value for this field
   */
  public Object get(Field field) throws IllegalAccessException {
    return field.get(this);
  }
  
  /**
   * @param field
   * @param value to be assigned
   */
  public void set(Field field, Object value) throws IllegalAccessException {
    field.set(this, value);
  }
  
  @Override
  public boolean equals(Object other) {
    boolean equal = false;
    
    if (other instanceof Branch) {
      Branch that = (Branch)other;
      equal = (hashCode() == that.hashCode()) && isEqualTo(that);
    }
    
    return equal;
    
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getLocation(), getName());
  }
  
  protected boolean isEqualTo(Branch that) {
    boolean equal = true;
    equal = equal && getLocation() == null ? that.getLocation() == null : getLocation().equals(that.getLocation());
    equal = equal && getName() == null ? that.getName() == null : getName().equals(that.getName());
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("location = " + location + ", ");
    sb.append("name = " + name + ", ");
    sb.append("}");
    return sb.toString();
  }
  
}
