package com.marcosavard.domain;

import java.util.Objects;

/**
 * An international address
 */
public abstract class Address {
  private final String civicNumber;
  private final String streetName;
  private final String noAppartment;
  private final Country country;

  /**
   * @param civicNumber
   * @param streetName
   * @param noAppartment
   * @param country
   */
  protected Address(String civicNumber, String streetName, String noAppartment, Country country) {
    this.civicNumber = civicNumber;
    this.streetName = streetName;
    this.noAppartment = noAppartment;
    this.country = country;

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
  }

  /**
   * @return civicNumber
   */
  public String getCivicNumber() {
    return civicNumber;
  }

  /**
   * @return streetName
   */
  public String getStreetName() {
    return streetName;
  }

  /**
   * @return noAppartment
   */
  public String getNoAppartment() {
    return noAppartment;
  }

  /**
   * @return country
   */
  public Country getCountry() {
    return country;
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof Address) {
      Address that = (Address)other;
      equal = (hashCode() == that.hashCode()) ? isEqualTo(that) : false;
    }

    return equal;
  }

  @Override
  public int hashCode() {
    int hashCode = Objects.hash(getCivicNumber(), getStreetName(), getNoAppartment(), getCountry());
    return hashCode;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("civicNumber : " + getCivicNumberString() + ", ");
    sb.append("streetName : " + getStreetNameString() + ", ");
    sb.append("noAppartment : " + getNoAppartmentString() + ", ");
    sb.append("country : " + getCountryString() + "");
    sb.append("}");
    return sb.toString();
  }

  private boolean isEqualTo(Address thatAddress) {
    boolean equal = true;
    equal = equal && (getCivicNumber() == null ? thatAddress.getCivicNumber() == null : getCivicNumber().equals(thatAddress.getCivicNumber()));
    equal = equal && (getStreetName() == null ? thatAddress.getStreetName() == null : getStreetName().equals(thatAddress.getStreetName()));
    equal = equal && (getNoAppartment() == null ? thatAddress.getNoAppartment() == null : getNoAppartment().equals(thatAddress.getNoAppartment()));
    equal = equal && (getCountry() == null ? thatAddress.getCountry() == null : getCountry().equals(thatAddress.getCountry()));
    return equal;
  }

  private String getCivicNumberString() {
    Object value = getCivicNumber();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getStreetNameString() {
    Object value = getStreetName();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getNoAppartmentString() {
    Object value = getNoAppartment();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getCountryString() {
    Object value = getCountry();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

}
