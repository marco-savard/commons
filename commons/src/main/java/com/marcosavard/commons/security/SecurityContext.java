package com.marcosavard.commons.security;

import com.marcosavard.commons.security.PasswordHasher.SimplePasswordHasher;

public class SecurityContext {
  private static SecurityContext context;
  private PasswordPolicy passwordPolicy;
  private PasswordHasher passwordHasher;

  public static SecurityContext getContext() {
    if (context == null) {
      context = new SecurityContext();
    }

    return context;
  }

  private SecurityContext() {
    passwordHasher = new SimplePasswordHasher();
    passwordPolicy = new PasswordPolicy();
  }

  public PasswordHasher getPasswordHasher() {
    return passwordHasher;
  }

  public PasswordPolicy getPasswordPolicy() {
    return passwordPolicy;
  }

}
