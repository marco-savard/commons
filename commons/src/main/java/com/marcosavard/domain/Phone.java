package com.marcosavard.domain;

import java.util.Objects;

/**
 * Phone 
 * Generated on 2022/10/19 15:17
 */
public class Phone {
  private final int countryCode;
  private final String number;
  private final String extension;
  private PhoneQualifier qualifier;
  private boolean active;
  
  /**
   * @param number String
   * @param extension String
   * @param countryCode int
   */
  public Phone(int countryCode, String number, String extension) {
    if (number == null) {
      throw new IllegalArgumentException ("Parameter 'number' cannot be null");
    }
    
    if (extension == null) {
      throw new IllegalArgumentException ("Parameter 'extension' cannot be null");
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
    sb.append("countryCode = ").append(getCountryCode()).append(", ");
    sb.append("number = ").append(getNumber()).append(", ");
    sb.append("extension = ").append(getExtension()).append(", ");
    sb.append("qualifier = ").append(getQualifier()).append(", ");
    sb.append("active = ").append(isActive()).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
