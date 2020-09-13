package com.marcosavard.commons.security;

import java.text.MessageFormat;

public class PasswordStrenghtDemo {

  public static void main(String[] args) {
    String[] candidates = new String[] { //
        "12345678", "maaakkkee", "password", "Password", "P@$$w0rd", "abcdefgh", "12345678",
        "qwertyui", //
        "password", "password1", "password123456789" //
    };

    for (String candidate : candidates) {
      Password password = Password.of(candidate.toCharArray());
      PasswordStrenght strenght = PasswordStrenght.of(password);
      String formatted = MessageFormat.format("{0} : strengh = {1}", candidate, strenght);
      System.out.println(formatted);
    }
  }

}
