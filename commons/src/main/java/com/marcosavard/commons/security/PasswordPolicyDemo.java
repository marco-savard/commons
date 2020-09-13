package com.marcosavard.commons.security;

public class PasswordPolicyDemo {
  private static final String[] PASSWORDS = new String[] { //
      "abc", //
      "password"};

  public static void main(String[] args) {
    PasswordPolicy policy = new PasswordPolicy();

    for (String password : PASSWORDS) {
      boolean valid = policy.validate(password.toCharArray());

      if (!valid) {
        System.out.println("Invalid password : " + password);
      }
    }


  }

}
