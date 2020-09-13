package com.marcosavard.commons.security;

import java.util.Arrays;

public abstract class PasswordHasher {
  public abstract String hash(char[] password);

  public static class SimplePasswordHasher extends PasswordHasher {
    public String hash(char[] password) {
      int hashCode = 0;

      for (Character ch : password) {
        hashCode += ch.hashCode() % (17 * 19);
      }

      String hash = String.valueOf(hashCode);
      Arrays.fill(password, ' '); // once used, password is cleared
      return hash;
    }
  }



}
