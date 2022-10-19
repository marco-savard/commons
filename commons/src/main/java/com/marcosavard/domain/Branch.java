package com.marcosavard.domain;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Branch 
 * Generated on 2022/10/19 11:17
 */
public class Branch {
  private Company ownerCompany;
  private Address location;
  private String name;
  
  public static final Field OWNER_COMPANY_FIELD;
  public static final Field LOCATION_FIELD;
  public static final Field NAME_FIELD;
  
  static {
    try {
      OWNER_COMPANY_FIELD = Branch.class.getDeclaredField("ownerCompany");
      LOCATION_FIELD = Branch.class.getDeclaredField("location");
      NAME_FIELD = Branch.class.getDeclaredField("name");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * @return ownerCompany Company
   */
  public Company getOwnerCompany() {
    return ownerCompany;
  }
  
  /**
   * @param ownerCompany Company
   */
  public void setOwnerCompany(Company ownerCompany) {
    this.ownerCompany = ownerCompany;
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
    return new Field[] {OWNER_COMPANY_FIELD, LOCATION_FIELD, NAME_FIELD};
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
    return Objects.hash(getOwnerCompany(), getLocation(), getName());
  }
  
  protected boolean isEqualTo(Branch that) {
    boolean equal = true;
    equal = equal && getOwnerCompany() == null ? that.getOwnerCompany() == null : getOwnerCompany().equals(that.getOwnerCompany());
    equal = equal && getLocation() == null ? that.getLocation() == null : getLocation().equals(that.getLocation());
    equal = equal && getName() == null ? that.getName() == null : getName().equals(that.getName());
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("ownerCompany = ").append(ownerCompany).append(", ");
    sb.append("location = ").append(location).append(", ");
    sb.append("name = ").append(name).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
