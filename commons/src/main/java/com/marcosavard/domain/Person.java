package com.marcosavard.domain;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Person represents a person who has a name and a birthdate
 * Generated on 2022/10/19 11:17
 */
public class Person {
  public static final String CONST = "99";
  
  private String firstName;
  private String middleName;
  private String lastName;
  private LocalDate birthDate;
  private Address homeAddress;
  private List<Phone> phoneNumbers = new ArrayList<>();
  
  public static final Field FIRST_NAME_FIELD;
  public static final Field MIDDLE_NAME_FIELD;
  public static final Field LAST_NAME_FIELD;
  public static final Field BIRTH_DATE_FIELD;
  public static final Field HOME_ADDRESS_FIELD;
  public static final Field PHONE_NUMBERS_FIELD;
  
  static {
    try {
      FIRST_NAME_FIELD = Person.class.getDeclaredField("firstName");
      MIDDLE_NAME_FIELD = Person.class.getDeclaredField("middleName");
      LAST_NAME_FIELD = Person.class.getDeclaredField("lastName");
      BIRTH_DATE_FIELD = Person.class.getDeclaredField("birthDate");
      HOME_ADDRESS_FIELD = Person.class.getDeclaredField("homeAddress");
      PHONE_NUMBERS_FIELD = Person.class.getDeclaredField("phoneNumbers");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
  
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
  
  
  public static Field[] getFields() {
    return new Field[] {FIRST_NAME_FIELD, MIDDLE_NAME_FIELD, LAST_NAME_FIELD, BIRTH_DATE_FIELD, HOME_ADDRESS_FIELD, PHONE_NUMBERS_FIELD};
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
