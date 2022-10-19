package com.marcosavard.domain;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Phone 
 * Generated on 2022/10/19 11:17
 */
public class Phone {
  private final int countryCode;
  private final String number;
  private final String extension;
  private PhoneQualifier qualifier;
  private boolean active;
  
  public static final Field COUNTRY_CODE_FIELD;
  public static final Field NUMBER_FIELD;
  public static final Field EXTENSION_FIELD;
  public static final Field QUALIFIER_FIELD;
  public static final Field ACTIVE_FIELD;
  
  static {
    try {
      COUNTRY_CODE_FIELD = Phone.class.getDeclaredField("countryCode");
      NUMBER_FIELD = Phone.class.getDeclaredField("number");
      EXTENSION_FIELD = Phone.class.getDeclaredField("extension");
      QUALIFIER_FIELD = Phone.class.getDeclaredField("qualifier");
      ACTIVE_FIELD = Phone.class.getDeclaredField("active");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * @param number String
   * @param extension String
   * @param countryCode int
   */
  public Phone(int countryCode, String number, String extension) {
    if (number == null) {
      throw new IllegalArgumentException ("Parameter 'number' cannot be null");
    }
    
    this.number = number;
    this.extension = extension;
    this.countryCode = countryCode;
    
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
  
  /**
   * @param qualifier PhoneQualifier
   */
  public void setQualifier(PhoneQualifier qualifier) {
    this.qualifier = qualifier;
  }
  
  /**
   * @return active boolean
   */
  public boolean isActive() {
    return active;
  }
  
  /**
   * @param active boolean
   */
  public void setActive(boolean active) {
    this.active = active;
  }
  
  
  public static Field[] getFields() {
    return new Field[] {COUNTRY_CODE_FIELD, NUMBER_FIELD, EXTENSION_FIELD, QUALIFIER_FIELD, ACTIVE_FIELD};
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
    return Objects.hash(getCountryCode(), getNumber(), getExtension(), getQualifier(), isActive());
  }
  
  protected boolean isEqualTo(Phone that) {
    boolean equal = true;
    equal = equal && getCountryCode() == that.getCountryCode();
    equal = equal && getNumber() == null ? that.getNumber() == null : getNumber().equals(that.getNumber());
    equal = equal && getExtension() == null ? that.getExtension() == null : getExtension().equals(that.getExtension());
    equal = equal && getQualifier() == null ? that.getQualifier() == null : getQualifier().equals(that.getQualifier());
    equal = equal && isActive() == that.isActive();
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("countryCode = ").append(countryCode).append(", ");
    sb.append("number = ").append(number).append(", ");
    sb.append("extension = ").append(extension).append(", ");
    sb.append("qualifier = ").append(qualifier).append(", ");
    sb.append("active = ").append(active).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
