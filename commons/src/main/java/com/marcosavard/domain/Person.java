package com.marcosavard.domain;

import java.lang.NoSuchFieldException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A person that has a name and a birthdate
 */
public class Person {
  public static final String CONST = "99";
  
  private String firstName;
  private String lastName;
  private LocalDate birthDate;
  private Address home;
  private Company employer;
  private List<Phone> phoneNumbers = new ArrayList<>();
  
  public static final Field FIRST_NAME;
  public static final Field LAST_NAME;
  public static final Field BIRTH_DATE;
  public static final Field HOME;
  public static final Field EMPLOYER;
  public static final Field PHONE_NUMBERS;
  
  static {
    try {
      FIRST_NAME = Person.class.getDeclaredField("firstName");
      LAST_NAME = Person.class.getDeclaredField("lastName");
      BIRTH_DATE = Person.class.getDeclaredField("birthDate");
      HOME = Person.class.getDeclaredField("home");
      EMPLOYER = Person.class.getDeclaredField("employer");
      PHONE_NUMBERS = Person.class.getDeclaredField("phoneNumbers");
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
    this.firstName = firstName;
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
    this.birthDate = birthDate;
  }
  
  /**
   * @return home Address
   */
  public Address getHome() {
    return home;
  }
  
  /**
   * @param home Address
   */
  public void setHome(Address home) {
    this.home = home;
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
    return new Field[] {FIRST_NAME, LAST_NAME, BIRTH_DATE, HOME, EMPLOYER, PHONE_NUMBERS, };
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
    return Objects.hash(getFirstName(), getLastName(), getBirthDate(), getHome(), getEmployer(), getPhoneNumbers());
  }
  
  protected boolean isEqualTo(Person that) {
    boolean equal = true;
    equal = equal && getFirstName() == null ? that.getFirstName() == null : getFirstName().equals(that.getFirstName());
    equal = equal && getLastName() == null ? that.getLastName() == null : getLastName().equals(that.getLastName());
    equal = equal && getBirthDate() == null ? that.getBirthDate() == null : getBirthDate().equals(that.getBirthDate());
    equal = equal && getHome() == null ? that.getHome() == null : getHome().equals(that.getHome());
    equal = equal && getEmployer() == null ? that.getEmployer() == null : getEmployer().equals(that.getEmployer());
    equal = equal && getPhoneNumbers() == null ? that.getPhoneNumbers() == null : getPhoneNumbers().equals(that.getPhoneNumbers());
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("firstName = " + firstName + ", ");
    sb.append("lastName = " + lastName + ", ");
    sb.append("birthDate = " + birthDate + ", ");
    sb.append("home = " + home + ", ");
    sb.append("employer = " + employer + ", ");
    sb.append("phoneNumbers = " + phoneNumbers + ", ");
    sb.append("}");
    return sb.toString();
  }
  
}
