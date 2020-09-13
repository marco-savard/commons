package com.marcosavard.domain;

import java.util.Objects;

/**
 * A US address
 */
public class USAddress extends Address {
  private final String stateCode;
  private final String zipCode;

  /**
   * @param civicNumber
   * @param streetName
   * @param noAppartment
   * @param country
   * @param stateCode
   * @param zipCode
   */
  public USAddress(String civicNumber, String streetName, String noAppartment, Country country, String stateCode, String zipCode) {
    super(civicNumber, streetName, noAppartment, country);
    this.stateCode = stateCode;
    this.zipCode = zipCode;

    if (civicNumber == null) {
      throw new NullPointerException("Parameter 'civicNumber' is required");
    }

    if (streetName == null) {
      throw new NullPointerException("Parameter 'streetName' is required");
    }

    if (noAppartment == null) {
      throw new NullPointerException("Parameter 'noAppartment' is required");
    }

    if (country == null) {
      throw new NullPointerException("Parameter 'country' is required");
    }

    if (stateCode == null) {
      throw new NullPointerException("Parameter 'stateCode' is required");
    }

    if (zipCode == null) {
      throw new NullPointerException("Parameter 'zipCode' is required");
    }
  }

  /**
   * @return stateCode
   */
  public String getStateCode() {
    return stateCode;
  }

  /**
   * @return two-letter ZIP code
   */
  public String getZipCode() {
    return zipCode;
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof USAddress) {
      USAddress that = (USAddress)other;
      equal = (hashCode() == that.hashCode()) ? isEqualTo(that) : false;
    }

    return equal;
  }

  @Override
  public int hashCode() {
    int hashCode = Objects.hash(getStateCode(), getZipCode());
    return hashCode;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("stateCode : " + getStateCodeString() + ", ");
    sb.append("zipCode : " + getZipCodeString() + "");
    sb.append("}");
    return sb.toString();
  }

  private boolean isEqualTo(USAddress thatUSAddress) {
    boolean equal = true;
    equal = equal && (getStateCode() == null ? thatUSAddress.getStateCode() == null : getStateCode().equals(thatUSAddress.getStateCode()));
    equal = equal && (getZipCode() == null ? thatUSAddress.getZipCode() == null : getZipCode().equals(thatUSAddress.getZipCode()));
    return equal;
  }

  private String getStateCodeString() {
    Object value = getStateCode();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getZipCodeString() {
    Object value = getZipCode();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

}
