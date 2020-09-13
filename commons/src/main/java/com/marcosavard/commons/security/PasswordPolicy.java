package com.marcosavard.commons.security;

// based on https://cheatsheetseries.owasp.org/cheatsheets/Authentication_Cheat_Sheet.html
public class PasswordPolicy {
  private static final int DEFAULT_MINIMAL_LENGTH = 8;
  private static final int DEFAULT_MAXIMAL_LENGTH = 64;
  private static final int DEFAULT_MAXIMAL_ATTEMPS = 5;
  private static final long RESPONSE_TIME = 1000L; // 1 second

  private int minimalLength = 8;
  private int maximalLength = 64;
  private int maximalAttempts = DEFAULT_MAXIMAL_ATTEMPS;
  // TODO minimalStrenght in bits, minimalAge, maximalAgeForTemporary, etc.

  public PasswordPolicy() {
    minimalLength = DEFAULT_MINIMAL_LENGTH;
    maximalLength = DEFAULT_MAXIMAL_LENGTH;
  }

  public int getMaxAttempts() {
    return maximalAttempts;
  }

  public boolean validate(char[] password) {
    boolean valid = password.length >= minimalLength;
    valid &= password.length <= maximalLength;
    return valid;
  }

}
