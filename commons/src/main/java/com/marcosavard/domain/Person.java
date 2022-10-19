package com.marcosavard.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Person represents a person who has a name and a birthdate
 * Generated on 2022/10/19 15:04
 */
public class Person {
  public static final String CONST = "99";
  
  private String firstName;
  private String middleName;
  private String lastName;
  private LocalDate birthDate;
  private Address homeAddress;
  private List<Phone> phoneNumbers = new ArrayList<>();
  
  /**
   * @return firstName String
   */
  public String getFirstName() {
    return firstName;
  }
  
  /**
   * @param firstName String
   */
  public void setFirstName(String firstName) {
    if (firstName == null) {
      throw new IllegalArgumentException ("Parameter 'firstName' cannot be null");
    }
    
    this.firstName = firstName;
  }
  
  /**
   * @return middleName String
   */
  public String getMiddleName() {
    return middleName;
  }
  
  /**
   * @param middleName String
   */
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }
  
  /**
   * @return lastName String
   */
  public String getLastName() {
    return lastName;
  }
  
  /**
   * @param lastName String
   */
  public void setLastName(String lastName) {
    if (lastName == null) {
      throw new IllegalArgumentException ("Parameter 'lastName' cannot be null");
    }
    
    this.lastName = lastName;
  }
  
  /**
   * @return birthDate LocalDate
   */
  public LocalDate getBirthDate() {
    return birthDate;
  }
  
  /**
   * @param birthDate LocalDate
   */
  public void setBirthDate(LocalDate birthDate) {
    if (birthDate == null) {
      throw new IllegalArgumentException ("Parameter 'birthDate' cannot be null");
    }
    
    this.birthDate = birthDate;
  }
  
  /**
   * @return homeAddress Address
   */
  public Address getHomeAddress() {
    return homeAddress;
  }
  
  /**
   * @param homeAddress Address
   */
  public void setHomeAddress(Address homeAddress) {
    this.homeAddress = homeAddress;
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
  
  
  @Override
  public boolean equals(Object other) {
    boolean equal = false;
    
    if (other instanceof Person) {
      Person that = (Person)other;
      equal = (hashCode() == that.hashCode()) && isEqualTo(that);
    }
    
    return equal;
    
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getFirstName(), getMiddleName(), getLastName(), getBirthDate(), getHomeAddress(), getPhoneNumbers());
  }
  
  protected boolean isEqualTo(Person that) {
    boolean equal = true;
    equal = equal && getFirstName() == null ? that.getFirstName() == null : getFirstName().equals(that.getFirstName());
    equal = equal && getMiddleName() == null ? that.getMiddleName() == null : getMiddleName().equals(that.getMiddleName());
    equal = equal && getLastName() == null ? that.getLastName() == null : getLastName().equals(that.getLastName());
    equal = equal && getBirthDate() == null ? that.getBirthDate() == null : getBirthDate().equals(that.getBirthDate());
    equal = equal && getHomeAddress() == null ? that.getHomeAddress() == null : getHomeAddress().equals(that.getHomeAddress());
    equal = equal && getPhoneNumbers() == null ? that.getPhoneNumbers() == null : getPhoneNumbers().equals(that.getPhoneNumbers());
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("firstName = ").append(firstName).append(", ");
    sb.append("middleName = ").append(middleName).append(", ");
    sb.append("lastName = ").append(lastName).append(", ");
    sb.append("birthDate = ").append(birthDate).append(", ");
    sb.append("homeAddress = ").append(homeAddress).append(", ");
    sb.append("phoneNumbers = ").append(phoneNumbers).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
