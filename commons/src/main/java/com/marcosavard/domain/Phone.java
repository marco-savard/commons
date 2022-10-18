package com.marcosavard.domain;

import java.lang.NoSuchFieldException;
import java.lang.reflect.Field;
import java.util.Objects;

public class Phone {
  private final int countryCode;
  private final String number;
  private final String extension;
  private final PhoneQualifier qualifier;
  
  public static final Field COUNTRY_CODE;
  public static final Field NUMBER;
  public static final Field EXTENSION;
  public static final Field QUALIFIER;
  
  static {
    try {
      COUNTRY_CODE = Phone.class.getDeclaredField("countryCode");
      NUMBER = Phone.class.getDeclaredField("number");
      EXTENSION = Phone.class.getDeclaredField("extension");
      QUALIFIER = Phone.class.getDeclaredField("qualifier");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * @param number String
   * @param extension String
   * @param countryCode int
   * @param qualifier PhoneQualifier
   */
  public Phone(int countryCode, String number, String extension, PhoneQualifier qualifier) {
    this.number = number;
    this.extension = extension;
    this.countryCode = countryCode;
    this.qualifier = qualifier;
    
    if (number == null) {
      throw new IllegalArgumentException ("Parameter 'number' is required");
    }
    
  }
  
  /**
   * @return countryCode int
   */
  public int getCountryCode() {
    return countryCode;
  }
  
  /**
   * @return number String
   */
  public String getNumber() {
    return number;
  }
  
  /**
   * @return extension String
   */
  public String getExtension() {
    return extension;
  }
  
  /**
   * @return qualifier PhoneQualifier
   */
  public PhoneQualifier getQualifier() {
    return qualifier;
  }
  
  
  public static Field[] getFields() {
    return new Field[] {COUNTRY_CODE, NUMBER, EXTENSION, QUALIFIER, };
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
    
    if (other instanceof Phone) {
      Phone that = (Phone)other;
      equal = (hashCode() == that.hashCode()) && isEqualTo(that);
    }
    
    return equal;
    
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getCountryCode(), getNumber(), getExtension(), getQualifier());
  }
  
  protected boolean isEqualTo(Phone that) {
    boolean equal = true;
    equal = equal && getCountryCode() == that.getCountryCode();
    equal = equal && getNumber() == null ? that.getNumber() == null : getNumber().equals(that.getNumber());
    equal = equal && getExtension() == null ? that.getExtension() == null : getExtension().equals(that.getExtension());
    equal = equal && getQualifier() == null ? that.getQualifier() == null : getQualifier().equals(that.getQualifier());
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("countryCode = " + countryCode + ", ");
    sb.append("number = " + number + ", ");
    sb.append("extension = " + extension + ", ");
    sb.append("qualifier = " + qualifier + ", ");
    sb.append("}");
    return sb.toString();
  }
  
}
