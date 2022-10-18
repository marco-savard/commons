package com.marcosavard.domain;

import java.lang.NoSuchFieldException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Company
 */
public class Company {
  private String name;
  private Address headquarter;
  private List<Phone> phoneNumbers = new ArrayList<>();
  private List<Person> employees = new ArrayList<>();
  private List<Branch> divisions = new ArrayList<>();
  
  public static final Field NAME;
  public static final Field HEADQUARTER;
  public static final Field PHONE_NUMBERS;
  public static final Field EMPLOYEES;
  public static final Field DIVISIONS;
  
  static {
    try {
      NAME = Company.class.getDeclaredField("name");
      HEADQUARTER = Company.class.getDeclaredField("headquarter");
      PHONE_NUMBERS = Company.class.getDeclaredField("phoneNumbers");
      EMPLOYEES = Company.class.getDeclaredField("employees");
      DIVISIONS = Company.class.getDeclaredField("divisions");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * @param headquarter Address
   * @param name String
   */
  public Company(String name, Address headquarter) {
    this.headquarter = headquarter;
    this.name = name;
    
    if (headquarter == null) {
      throw new IllegalArgumentException ("Parameter 'headquarter' is required");
    }
    
    if (name == null) {
      throw new IllegalArgumentException ("Parameter 'name' is required");
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
      throw new IllegalArgumentException ("Parameter 'name' is required");
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
      throw new IllegalArgumentException ("Parameter 'headquarter' is required");
    }
    
    this.headquarter = headquarter;
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
   * @return employees List
   */
  public List<Person> getEmployees() {
    return employees;
  }
  
  public void addToEmployees(Person person) {
    this.employees.add(person);
  }
  
  public void removeFromEmployees(Person person) {
    this.employees.remove(person);
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
    return new Field[] {NAME, HEADQUARTER, PHONE_NUMBERS, EMPLOYEES, DIVISIONS, };
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
    return Objects.hash(getName(), getHeadquarter(), getPhoneNumbers(), getEmployees(), getDivisions());
  }
  
  protected boolean isEqualTo(Company that) {
    boolean equal = true;
    equal = equal && getName() == null ? that.getName() == null : getName().equals(that.getName());
    equal = equal && getHeadquarter() == null ? that.getHeadquarter() == null : getHeadquarter().equals(that.getHeadquarter());
    equal = equal && getPhoneNumbers() == null ? that.getPhoneNumbers() == null : getPhoneNumbers().equals(that.getPhoneNumbers());
    equal = equal && getEmployees() == null ? that.getEmployees() == null : getEmployees().equals(that.getEmployees());
    equal = equal && getDivisions() == null ? that.getDivisions() == null : getDivisions().equals(that.getDivisions());
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("name = " + name + ", ");
    sb.append("headquarter = " + headquarter + ", ");
    sb.append("phoneNumbers = " + phoneNumbers + ", ");
    sb.append("employees = " + employees + ", ");
    sb.append("divisions = " + divisions + ", ");
    sb.append("}");
    return sb.toString();
  }
  
}
