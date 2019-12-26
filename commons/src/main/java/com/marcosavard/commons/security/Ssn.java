package com.marcosavard.commons.security;

import java.util.Locale;
import com.marcosavard.commons.security.ca.SocialInsuranceNumber;

/**
 * Create a Social Insurance Number, and tells if it is a valid SIN.
 * 
 * @author Marco
 *
 */
public abstract class Ssn {
  protected String canonicalNumber;

  /**
   * Create a Social Insurance Number, blanks and dashed ignored.
   * 
   * @param string not sanitized value
   */
  public static Ssn of(Locale locale, String text) {
    Ssn ssn = null;

    if (Locale.CANADA.equals(locale)) {
      ssn = SocialInsuranceNumber.of(text);
    }

    return ssn;
  }

  @Override
  public String toString() {
    return canonicalNumber;
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof Ssn) {
      Ssn otherSin = (Ssn) other;
      equal = canonicalNumber.equals(otherSin.toString());
    }

    return equal;
  }

  /**
   * A string in human-readable formal
   * 
   * @return formated string
   */
  public abstract String toDisplayString();



  /**
   * Tell if it is a valid Social Insurance Number.
   * 
   * @return true if valid
   */
  public abstract boolean isValid();



}
