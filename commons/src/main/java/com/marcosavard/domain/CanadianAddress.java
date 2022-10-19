package com.marcosavard.domain;

import java.util.Objects;

/**
 * CanadianAddress represents a Canadian address
 * Generated on 2022/10/19 15:17
 */
public class CanadianAddress extends Address {
  private final String provinceCode;
  private final String postalCode;
  
  /**
   * @param country Country
   * @param streetName String
   * @param provinceCode String
   * @param postalCode String
   * @param noApartment String
   * @param civicNumber String
   */
  public CanadianAddress(String civicNumber, String streetName, String noApartment, Country country, String provinceCode, String postalCode) {
    super(civicNumber, streetName, noApartment, country);
    if (provinceCode == null) {
      throw new IllegalArgumentException ("Parameter 'provinceCode' cannot be null");
    }
    
    if (postalCode == null) {
      throw new IllegalArgumentException ("Parameter 'postalCode' cannot be null");
    }
    
    this.provinceCode = provinceCode;
    this.postalCode = postalCode;
    
  }
  
  /**
   * @return provinceCode String
   */
  public String getProvinceCode() {
    return provinceCode;
  }
  
  /**
   * @return postalCode String
   */
  public String getPostalCode() {
    return postalCode;
  }
  
  
  @Override
  public boolean equals(Object other) {
    boolean equal = false;
    
    if (other instanceof CanadianAddress) {
      CanadianAddress that = (CanadianAddress)other;
      equal = (hashCode() == that.hashCode()) && isEqualTo(that);
    }
    
    return equal;
    
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getProvinceCode(), getPostalCode(), super.hashCode());
  }
  
  protected boolean isEqualTo(CanadianAddress that) {
    boolean equal = true;
    equal = equal && getProvinceCode() == null ? that.getProvinceCode() == null : getProvinceCode().equals(that.getProvinceCode());
    equal = equal && getPostalCode() == null ? that.getPostalCode() == null : getPostalCode().equals(that.getPostalCode());
    equal = equal && super.isEqualTo(that);
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("provinceCode = ").append(getProvinceCode()).append(", ");
    sb.append("postalCode = ").append(getPostalCode()).append(", ");
    sb.append("civicNumber = ").append(getCivicNumber()).append(", ");
    sb.append("streetName = ").append(getStreetName()).append(", ");
    sb.append("noApartment = ").append(getNoApartment()).append(", ");
    sb.append("country = ").append(getCountry()).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
