package com.marcosavard.domain;

import java.util.Objects;

/**
 * Phone
 */
public class Phone {
  private final String number;
  private final String extension;
  private final PhoneQualifier qualifier;
  private final int countryCode;

  /**
   * @param number
   * @param extension
   * @param qualifier
   * @param countryCode
   */
  public Phone(String number, String extension, PhoneQualifier qualifier, int countryCode) {
    this.number = number;
    this.extension = extension;
    this.qualifier = qualifier;
    this.countryCode = countryCode;

    if (number == null) {
      throw new NullPointerException("Parameter 'number' is required");
    }

    if (extension == null) {
      throw new NullPointerException("Parameter 'extension' is required");
    }

    if (qualifier == null) {
      throw new NullPointerException("Parameter 'qualifier' is required");
    }
  }

  /**
   * @return number
   */
  public String getNumber() {
    return number;
  }

  /**
   * @return extension
   */
  public String getExtension() {
    return extension;
  }

  /**
   * @return qualifier
   */
  public PhoneQualifier getQualifier() {
    return qualifier;
  }

  /**
   * @return countryCode
   */
  public int getCountryCode() {
    return countryCode;
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof Phone) {
      Phone that = (Phone)other;
      equal = (hashCode() == that.hashCode()) ? isEqualTo(that) : false;
    }

    return equal;
  }

  @Override
  public int hashCode() {
    int hashCode = Objects.hash(getNumber(), getExtension(), getQualifier(), getCountryCode());
    return hashCode;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("number : " + getNumberString() + ", ");
    sb.append("extension : " + getExtensionString() + ", ");
    sb.append("qualifier : " + getQualifierString() + ", ");
    sb.append("countryCode : " + getCountryCodeString() + "");
    sb.append("}");
    return sb.toString();
  }

  private boolean isEqualTo(Phone thatPhone) {
    boolean equal = true;
    equal = equal && (getNumber() == null ? thatPhone.getNumber() == null : getNumber().equals(thatPhone.getNumber()));
    equal = equal && (getExtension() == null ? thatPhone.getExtension() == null : getExtension().equals(thatPhone.getExtension()));
    equal = equal && (getQualifier() == null ? thatPhone.getQualifier() == null : getQualifier().equals(thatPhone.getQualifier()));
    equal = equal && (getCountryCode() == thatPhone.getCountryCode());
    return equal;
  }

  private String getNumberString() {
    Object value = getNumber();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getExtensionString() {
    Object value = getExtension();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getQualifierString() {
    Object value = getQualifier();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

  private String getCountryCodeString() {
    Object value = getCountryCode();
    String s;

    if (value instanceof String) {
      s = "\"" + value + "\"";
    } else {
      s = (value == null) ? null : value.toString();
    }

    return s;
  }

}
