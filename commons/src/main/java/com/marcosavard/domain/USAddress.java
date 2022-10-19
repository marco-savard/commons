package com.marcosavard.domain;

import java.util.Objects;

/**
 * USAddress represents a U.S. address
 * Generated on 2022/10/19 15:04
 */
public class USAddress extends Address {
  private final String stateCode;
  private final String zipCode;
  
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
    if (zipCode == null) {
      throw new IllegalArgumentException ("Parameter 'zipCode' cannot be null");
    }
    
    if (stateCode == null) {
      throw new IllegalArgumentException ("Parameter 'stateCode' cannot be null");
    }
    
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
    sb.append("stateCode = ").append(stateCode).append(", ");
    sb.append("zipCode = ").append(zipCode).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
