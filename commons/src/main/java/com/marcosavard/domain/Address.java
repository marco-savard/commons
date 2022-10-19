package com.marcosavard.domain;

import java.util.Objects;

/**
 * Address represents an international address
 * Generated on 2022/10/19 15:17
 */
public abstract class Address {
  private final String civicNumber;
  private final String streetName;
  private final String noApartment;
  private final Country country;
  
  /**
   * @param country Country
   * @param streetName String
   * @param noApartment String
   * @param civicNumber String
   */
  protected Address(String civicNumber, String streetName, String noApartment, Country country) {
    if (country == null) {
      throw new IllegalArgumentException ("Parameter 'country' cannot be null");
    }
    
    if (streetName == null) {
      throw new IllegalArgumentException ("Parameter 'streetName' cannot be null");
    }
    
    if (noApartment == null) {
      throw new IllegalArgumentException ("Parameter 'noApartment' cannot be null");
    }
    
    if (civicNumber == null) {
      throw new IllegalArgumentException ("Parameter 'civicNumber' cannot be null");
    }
    
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
    sb.append("civicNumber = ").append(getCivicNumber()).append(", ");
    sb.append("streetName = ").append(getStreetName()).append(", ");
    sb.append("noApartment = ").append(getNoApartment()).append(", ");
    sb.append("country = ").append(getCountry()).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
