package com.marcosavard.commons.security;

import java.text.MessageFormat;
import java.util.Locale;

public class SsnDemo {

  public static void main(String[] args) {
    // demoSinEquality();
    demoSinValidation();
  }

  private static void demoSinEquality() {
    Locale ca = Locale.CANADA;
    Ssn ssn1 = Ssn.of(ca, "046454286");
    Ssn ssn2 = Ssn.of(ca, "046-454-286 ");
    String pat = "  {0} equal to {1} : {2}";
    boolean equal = ssn1.equals(ssn2);
    String msg = MessageFormat.format(pat, ssn1, ssn2, equal);
    System.out.println(msg);
  }

  private static void demoSinValidation() {
    Locale ca = Locale.CANADA;
    Ssn valid = Ssn.of(ca, "046454286");
    Ssn ssn2 = Ssn.of(ca, "641211198");

    String pat = " {0} is valid: {1}";
    String msg = MessageFormat.format(pat, ssn2.toDisplayString(), ssn2.isValid());
    System.out.println(msg);
  }

}
