package com.marcosavard.domain;

import java.lang.NoSuchFieldException;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * An international address
 */
public abstract class Address {
  private final String civicNumber;
  private final String streetName;
  private final String noApartment;
  private final Country country;
  
  public static final Field CIVIC_NUMBER;
  public static final Field STREET_NAME;
  public static final Field NO_APARTMENT;
  public static final Field COUNTRY;
  
  static {
    try {
      CIVIC_NUMBER = Address.class.getDeclaredField("civicNumber");
      STREET_NAME = Address.class.getDeclaredField("streetName");
      NO_APARTMENT = Address.class.getDeclaredField("noApartment");
      COUNTRY = Address.class.getDeclaredField("country");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * @param country Country
   * @param streetName String
   * @param noApartment String
   * @param civicNumber String
   */
  protected Address(String civicNumber, String streetName, String noApartment, Country country) {
    this.country = country;
    this.streetName = streetName;
    this.noApartment = noApartment;
    this.civicNumber = civicNumber;
    
  }
  
  /**
   * @return civicNumber String
   */
  public String getCivicNumber() {
    return civicNumber;
  }
  
  /**
   * @return streetName String
   */
  public String getStreetName() {
    return streetName;
  }
  
  /**
   * @return noApartment String
   */
  public String getNoApartment() {
    return noApartment;
  }
  
  /**
   * @return country Country
   */
  public Country getCountry() {
    return country;
  }
  
  
  public static Field[] getFields() {
    return new Field[] {CIVIC_NUMBER, STREET_NAME, NO_APARTMENT, COUNTRY, };
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
    
    if (other instanceof Address) {
      Address that = (Address)other;
      equal = (hashCode() == that.hashCode()) && isEqualTo(that);
    }
    
    return equal;
    
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getCivicNumber(), getStreetName(), getNoApartment(), getCountry());
  }
  
  protected boolean isEqualTo(Address that) {
    boolean equal = true;
    equal = equal && getCivicNumber() == null ? that.getCivicNumber() == null : getCivicNumber().equals(that.getCivicNumber());
    equal = equal && getStreetName() == null ? that.getStreetName() == null : getStreetName().equals(that.getStreetName());
    equal = equal && getNoApartment() == null ? that.getNoApartment() == null : getNoApartment().equals(that.getNoApartment());
    equal = equal && getCountry() == null ? that.getCountry() == null : getCountry().equals(that.getCountry());
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("civicNumber = " + civicNumber + ", ");
    sb.append("streetName = " + streetName + ", ");
    sb.append("noApartment = " + noApartment + ", ");
    sb.append("country = " + country + ", ");
    sb.append("}");
    return sb.toString();
  }
  
}
