package com.marcosavard.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Company represents a company
 * Generated on 2022/10/19 11:17
 */
public class Company {
  private String name;
  private Address headquarter;
  private List<Worker> employees = new ArrayList<>();
  private List<Phone> phoneNumbers = new ArrayList<>();
  private List<Branch> divisions = new ArrayList<>();
  
  public static final Field NAME_FIELD;
  public static final Field HEADQUARTER_FIELD;
  public static final Field EMPLOYEES_FIELD;
  public static final Field PHONE_NUMBERS_FIELD;
  public static final Field DIVISIONS_FIELD;
  
  static {
    try {
      NAME_FIELD = Company.class.getDeclaredField("name");
      HEADQUARTER_FIELD = Company.class.getDeclaredField("headquarter");
      EMPLOYEES_FIELD = Company.class.getDeclaredField("employees");
      PHONE_NUMBERS_FIELD = Company.class.getDeclaredField("phoneNumbers");
      DIVISIONS_FIELD = Company.class.getDeclaredField("divisions");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
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
    if (name == null) {
      throw new IllegalArgumentException ("Parameter 'name' cannot be null");
    }
    
    this.name = name;
  }
  
  /**
   * @return headquarter Address
   */
  public Address getHeadquarter() {
    return headquarter;
  }
  
  /**
   * @param headquarter Address
   */
  public void setHeadquarter(Address headquarter) {
    if (headquarter == null) {
      throw new IllegalArgumentException ("Parameter 'headquarter' cannot be null");
    }
    
    this.headquarter = headquarter;
  }
  
  /**
   * @return employees List
   */
  public List<Worker> getEmployees() {
    return employees;
  }
  
  public void addToEmployees(Worker worker) {
    this.employees.add(worker);
  }
  
  public void removeFromEmployees(Worker worker) {
    this.employees.remove(worker);
  }
  
  /**
   * @return phoneNumbers List
   */
  public List<Phone> getPhoneNumbers() {
    return phoneNumbers;
  }
  
  public void addToPhoneNumbers(Phone phone) {
    this.phoneNumbers.add(phone);
  }
  
  public void removeFromPhoneNumbers(Phone phone) {
    this.phoneNumbers.remove(phone);
  }
  
  /**
   * @return divisions List
   */
  public List<Branch> getDivisions() {
    return divisions;
  }
  
  public void addToDivisions(Branch branch) {
    this.divisions.add(branch);
  }
  
  public void removeFromDivisions(Branch branch) {
    this.divisions.remove(branch);
  }
  
  
  public static Field[] getFields() {
    return new Field[] {NAME_FIELD, HEADQUARTER_FIELD, EMPLOYEES_FIELD, PHONE_NUMBERS_FIELD, DIVISIONS_FIELD};
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
    
    if (other instanceof Company) {
      Company that = (Company)other;
      equal = (hashCode() == that.hashCode()) && isEqualTo(that);
    }
    
    return equal;
    
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getName(), getHeadquarter(), getEmployees(), getPhoneNumbers(), getDivisions());
  }
  
  protected boolean isEqualTo(Company that) {
    boolean equal = true;
    equal = equal && getName() == null ? that.getName() == null : getName().equals(that.getName());
    equal = equal && getHeadquarter() == null ? that.getHeadquarter() == null : getHeadquarter().equals(that.getHeadquarter());
    equal = equal && getEmployees() == null ? that.getEmployees() == null : getEmployees().equals(that.getEmployees());
    equal = equal && getPhoneNumbers() == null ? that.getPhoneNumbers() == null : getPhoneNumbers().equals(that.getPhoneNumbers());
    equal = equal && getDivisions() == null ? that.getDivisions() == null : getDivisions().equals(that.getDivisions());
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("name = ").append(name).append(", ");
    sb.append("headquarter = ").append(headquarter).append(", ");
    sb.append("employees = ").append(employees).append(", ");
    sb.append("phoneNumbers = ").append(phoneNumbers).append(", ");
    sb.append("divisions = ").append(divisions).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
