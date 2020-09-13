package com.marcosavard.commons.security;

import java.text.MessageFormat;

public class Account {
  private enum AccountStatus {
    PENDING, LOGGED_IN_AS_TEMPORARY, LOGGED_IN, LOGGED_OUT
  };

  private AccountStatus status = AccountStatus.PENDING;
  private String email;
  private String passwordHash;
  private char[] temporaryPassword;
  private int nbAttempts = 0;

  Account(String email, char[] temporaryPassword) {
    this.email = email;
    setTemporaryPassword(temporaryPassword);
  }

  void setTemporaryPassword(char[] temporaryPassword) {
    this.temporaryPassword = temporaryPassword;
    status = AccountStatus.PENDING;
  }

  char[] getTemporaryPassword() {
    return temporaryPassword;
  }

  String getPasswordHash() {
    return passwordHash;
  }

  void setPasswordHash(String hash) {
    passwordHash = hash;
    status = AccountStatus.LOGGED_IN;
  }

  void incrementAttempts() {
    nbAttempts++;
    String msg = MessageFormat.format("nb attempts = {0}", nbAttempts);
    System.out.println(msg);
  }

  int getAttempts() {
    return nbAttempts;
  }

  void logout() {
    status = AccountStatus.LOGGED_OUT;
  }

  @Override
  public String toString() {
    String msg = MessageFormat.format("{0} {1}", email, status);
    return msg;
  }

  public boolean isPending() {
    return status == AccountStatus.PENDING;
  }

  public boolean isLoggedOut() {
    return status == AccountStatus.LOGGED_OUT;
  }

  public void loginAsTemporary() {
    status = AccountStatus.LOGGED_IN_AS_TEMPORARY;
  }

  void login() {
    status = AccountStatus.LOGGED_IN;
  }



}
