package com.marcosavard.commons.security;

import com.marcosavard.commons.security.Password.Strenght;
import com.marcosavard.commons.util.ToStringBuilder;

public class PasswordDemo {

  // Store passwords in String just for demo or testing purposes
  // In production code, always store passwords in char[], and clear the password
  // once the hash is computed

  public static void main(String[] args) {

    String[] entries =
        new String[] {"abc", "abc   ", "password", "P@ssw0rd", "horse equitation stable zebra"};

    for (String entry : entries) {
      Password password = Password.of(entry.toCharArray());
      int enthropy = password.getEntropy();
      Strenght strength = password.getStrenght();
      System.out.println(ToStringBuilder.build(password));
      password.clear();
    }
  }
}
