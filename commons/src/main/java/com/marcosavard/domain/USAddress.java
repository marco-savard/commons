package com.marcosavard.domain;

import java.lang.NoSuchFieldException;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * A US address
 */
public class USAddress extends Address {
  private final String stateCode;
  private final String zipCode;
  
  public static final Field STATE_CODE;
  public static final Field ZIP_CODE;
  
  static {
    try {
      STATE_CODE = USAddress.class.getDeclaredField("stateCode");
      ZIP_CODE = USAddress.class.getDeclaredField("zipCode");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * @param country Country
   * @param zipCode two-letter ZIP code
   * @param streetName String
   * @param noApartment String
   * @param stateCode String
   * @param civicNumber String
   */
  public USAddress(String civicNumber, String streetName, String noApartment, Country country, String stateCode, String zipCode) {
    super(civicNumber, streetName, noApartment, country);
    this.zipCode = zipCode;
    this.stateCode = stateCode;
    
  }
  
  /**
   * @return stateCode String
   */
  public String getStateCode() {
    return stateCode;
  }
  
  /**
   * @return zipCode two-letter ZIP code
   */
  public String getZipCode() {
    return zipCode;
  }
  
  
  public static Field[] getFields() {
    return new Field[] {STATE_CODE, ZIP_CODE, };
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
    
    if (other instanceof USAddress) {
      USAddress that = (USAddress)other;
      equal = (hashCode() == that.hashCode()) && isEqualTo(that);
    }
    
    return equal;
    
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getStateCode(), getZipCode(), super.hashCode());
  }
  
  protected boolean isEqualTo(USAddress that) {
    boolean equal = true;
    equal = equal && getStateCode() == null ? that.getStateCode() == null : getStateCode().equals(that.getStateCode());
    equal = equal && getZipCode() == null ? that.getZipCode() == null : getZipCode().equals(that.getZipCode());
    equal = equal && super.isEqualTo(that);
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("stateCode = " + stateCode + ", ");
    sb.append("zipCode = " + zipCode + ", ");
    sb.append("}");
    return sb.toString();
  }
  
}
