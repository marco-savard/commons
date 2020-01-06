package com.marcosavard.commons.security.ca;

import com.marcosavard.commons.security.Ssn;

public class SocialInsuranceNumber extends Ssn {

  public static Ssn of(String text) {
    Ssn ssn = new SocialInsuranceNumber(text);
    return ssn;
  }

  private SocialInsuranceNumber(String text) {
    text = (text == null) ? "" : text;
    canonicalNumber = text.replaceAll(" ", "").replaceAll("-", "");
  }

  @Override
  public String toDisplayString() {
    String displayString = canonicalNumber.substring(0, 3) + " " + canonicalNumber.substring(3, 6)
        + " " + canonicalNumber.substring(6, 9);
    return displayString;
  }

  @Override
  public boolean isValid() {
    boolean valid = canonicalNumber.matches("^\\d{9}$");

    if (valid) {
      int sum = 0;

      for (int i = 0; i < 9; i++) {
        int digit = canonicalNumber.charAt(i) - 48;
        int mult = ((i % 2) == 0) ? 1 : 2;
        int prod = digit * mult;
        prod = (prod >= 10) ? (prod / 10) + (prod % 10) : prod;
        sum += prod;
      }

      valid = (sum % 10) == 0;
    }

    return valid;
  }



}
