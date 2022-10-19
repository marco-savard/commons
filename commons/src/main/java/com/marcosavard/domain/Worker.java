package com.marcosavard.domain;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Worker represents a person who can be hired by a company
 * Generated on 2022/10/19 11:17
 */
public class Worker extends Person {
  private Company employer;
  private Address officeAddress;
  
  public static final Field EMPLOYER_FIELD;
  public static final Field OFFICE_ADDRESS_FIELD;
  
  static {
    try {
      EMPLOYER_FIELD = Worker.class.getDeclaredField("employer");
      OFFICE_ADDRESS_FIELD = Worker.class.getDeclaredField("officeAddress");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * @return employer Company
   */
  public Company getEmployer() {
    return employer;
  }
  
  /**
   * @param employer Company
   */
  public void setEmployer(Company employer) {
    this.employer = employer;
  }
  
  /**
   * @return officeAddress Address
   */
  public Address getOfficeAddress() {
    return officeAddress;
  }
  
  /**
   * @param officeAddress Address
   */
  public void setOfficeAddress(Address officeAddress) {
    this.officeAddress = officeAddress;
  }
  
  
  public static Field[] getFields() {
    return new Field[] {EMPLOYER_FIELD, OFFICE_ADDRESS_FIELD};
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
    
    if (other instanceof Worker) {
      Worker that = (Worker)other;
      equal = (hashCode() == that.hashCode()) && isEqualTo(that);
    }
    
    return equal;
    
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getEmployer(), getOfficeAddress(), super.hashCode());
  }
  
  protected boolean isEqualTo(Worker that) {
    boolean equal = true;
    equal = equal && getEmployer() == null ? that.getEmployer() == null : getEmployer().equals(that.getEmployer());
    equal = equal && getOfficeAddress() == null ? that.getOfficeAddress() == null : getOfficeAddress().equals(that.getOfficeAddress());
    equal = equal && super.isEqualTo(that);
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("employer = ").append(employer).append(", ");
    sb.append("officeAddress = ").append(officeAddress).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
