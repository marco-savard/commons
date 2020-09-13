package com.marcosavard.domain;

import java.util.Objects;

/**
 * A Canadian address
 */
public class CanadianAddress extends Address {
  private final String provinceCode;
  private final String postalCode;

  /**
   * @param civicNumber
   * @param streetName
   * @param noAppartment
   * @param country
   * @param provinceCode
   * @param postalCode
   */
  public CanadianAddress(String civicNumber, String streetName, String noAppartment, Country country, String provinceCode, String postalCode) {
    super(civicNumber, streetName, noAppartment, country);
    this.provinceCode = provinceCode;
    this.postalCode = postalCode;

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

    if (provinceCode == null) {
      throw new NullPointerException("Parameter 'provinceCode' is required");
    }

    if (postalCode == null) {
      throw new NullPointerException("Parameter 'postalCode' is required");
    }
  }

  /**
   * @return provinceCode
   */
  public String getProvinceCode() {
    return provinceCode;
  }

  /**
   * @return postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof CanadianAddress) {
      CanadianAddress that = (CanadianAddress)other;
      equal = (hashCode() == that.hashCode()) ? isEqualTo(that) : false;
    }

    return equal;
  }

  @Override
  public int hashCode() {
    int hashCode = Objects.hash(getProvinceCode(), getPostalCode());
    return hashCode;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("provinceCode : " + getProvinceCodeString() + ", ");
    sb.append("postalCode : " + getPostalCodeString() + "");
    sb.append("}");
    return sb.toString();
  }

  private boolean isEqualTo(CanadianAddress thatCanadianAddress) {
    boolean equal = true;
    equal = equal && (getProvinceCode() == null ? thatCanadianAddress.getProvinceCode() == null : getProvinceCode().equals(thatCanadianAddress.getProvinceCode()));
    equal = equal && (getPostalCode() == null ? thatCanadianAddress.getPostalCode() == null : getPostalCode().equals(thatCanadianAddress.getPostalCode()));
    return equal;
  }

  private String getProvinceCodeString() {
    Object value = getProvinceCode();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getPostalCodeString() {
    Object value = getPostalCode();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

}
