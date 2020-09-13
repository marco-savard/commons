package com.marcosavard.domain;

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

  /**
   * @param name
   * @param headquarter
   */
  public Company(String name, Address headquarter) {
    this.name = name;
    this.headquarter = headquarter;

    if (name == null) {
      throw new NullPointerException("Parameter 'name' is required");
    }

    if (headquarter == null) {
      throw new NullPointerException("Parameter 'headquarter' is required");
    }
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
    if (name == null) {
      throw new NullPointerException("Parameter 'name' is required");
    }

    this.name = name;
  }

  /**
   * @return headquarter
   */
  public Address getHeadquarter() {
    return headquarter;
  }

  /**
   * @param headquarter
   */
  public void setHeadquarter(Address headquarter) {
    if (headquarter == null) {
      throw new NullPointerException("Parameter 'headquarter' is required");
    }

    this.headquarter = headquarter;
  }

  /**
   * @return phoneNumbers
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
   * @return employees
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
   * @return divisions
   */
  public List<Branch> getDivisions() {
    return divisions;
  }

  public Branch createBranchInDivisions() {
      Branch instance = new Branch();
      divisions.add(instance);
      return instance;
  }

  public void removeFromDivisions(Branch branch) {
      this.divisions.remove(branch);
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof Company) {
      Company that = (Company)other;
      equal = (hashCode() == that.hashCode()) ? isEqualTo(that) : false;
    }

    return equal;
  }

  @Override
  public int hashCode() {
    int hashCode = Objects.hash(getName(), getHeadquarter(), getPhoneNumbers(), getEmployees(), getDivisions());
    return hashCode;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("name : " + getNameString() + ", ");
    sb.append("headquarter : " + getHeadquarterString() + ", ");
    sb.append("phoneNumbers : " + getPhoneNumbersString() + ", ");
    sb.append("employees : " + getEmployeesString() + ", ");
    sb.append("divisions : " + getDivisionsString() + "");
    sb.append("}");
    return sb.toString();
  }

  private boolean isEqualTo(Company thatCompany) {
    boolean equal = true;
    equal = equal && (getName() == null ? thatCompany.getName() == null : getName().equals(thatCompany.getName()));
    equal = equal && (getHeadquarter() == null ? thatCompany.getHeadquarter() == null : getHeadquarter().equals(thatCompany.getHeadquarter()));
    equal = equal && (getPhoneNumbers() == null ? thatCompany.getPhoneNumbers() == null : getPhoneNumbers().equals(thatCompany.getPhoneNumbers()));
    equal = equal && (getEmployees() == null ? thatCompany.getEmployees() == null : getEmployees().equals(thatCompany.getEmployees()));
    equal = equal && (getDivisions() == null ? thatCompany.getDivisions() == null : getDivisions().equals(thatCompany.getDivisions()));
    return equal;
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

  private String getHeadquarterString() {
    Object value = getHeadquarter();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getPhoneNumbersString() {
    Object value = getPhoneNumbers();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getEmployeesString() {
    Object value = getEmployees();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getDivisionsString() {
    Object value = getDivisions();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

}
