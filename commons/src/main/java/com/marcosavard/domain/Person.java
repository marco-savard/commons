package com.marcosavard.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A person that has a name and a birth date
 */
public class Person {
  public static final String CONST = "99";

  private String firstName;
  private String lastName;
  private Address home;
  private Company employer;
  private List<Phone> phoneNumbers = new ArrayList<>();

  /**
   * @return firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return home
   */
  public Address getHome() {
    return home;
  }

  /**
   * @param home
   */
  public void setHome(Address home) {
    this.home = home;
  }

  /**
   * @return employer
   */
  public Company getEmployer() {
    return employer;
  }

  /**
   * @param employer
   */
  public void setEmployer(Company employer) {
    this.employer = employer;
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

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof Person) {
      Person that = (Person)other;
      equal = (hashCode() == that.hashCode()) ? isEqualTo(that) : false;
    }

    return equal;
  }

  @Override
  public int hashCode() {
    int hashCode = Objects.hash(getFirstName(), getLastName(), getHome(), getEmployer(), getPhoneNumbers());
    return hashCode;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("firstName : " + getFirstNameString() + ", ");
    sb.append("lastName : " + getLastNameString() + ", ");
    sb.append("home : " + getHomeString() + ", ");
    sb.append("employer : " + getEmployerString() + ", ");
    sb.append("phoneNumbers : " + getPhoneNumbersString() + "");
    sb.append("}");
    return sb.toString();
  }

  private boolean isEqualTo(Person thatPerson) {
    boolean equal = true;
    equal = equal && (getFirstName() == null ? thatPerson.getFirstName() == null : getFirstName().equals(thatPerson.getFirstName()));
    equal = equal && (getLastName() == null ? thatPerson.getLastName() == null : getLastName().equals(thatPerson.getLastName()));
    equal = equal && (getHome() == null ? thatPerson.getHome() == null : getHome().equals(thatPerson.getHome()));
    equal = equal && (getEmployer() == null ? thatPerson.getEmployer() == null : getEmployer().equals(thatPerson.getEmployer()));
    equal = equal && (getPhoneNumbers() == null ? thatPerson.getPhoneNumbers() == null : getPhoneNumbers().equals(thatPerson.getPhoneNumbers()));
    return equal;
  }

  private String getFirstNameString() {
    Object value = getFirstName();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getLastNameString() {
    Object value = getLastName();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getHomeString() {
    Object value = getHome();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getEmployerString() {
    Object value = getEmployer();
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

}
