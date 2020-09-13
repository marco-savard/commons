package com.marcosavard.commons.security;

import java.util.HashMap;
import java.util.Map;

public class Authentification {
  private static Authentification accountManager;
  private static final long SLEEP_DURATION = 1000L;
  private PasswordHasher passwordHasher;
  private PasswordPolicy policy;
  private Map<String, Account> accounts = new HashMap<>();

  private Authentification() {
    passwordHasher = new PasswordHasher.SimplePasswordHasher();
    policy = new PasswordPolicy();
  }

  public static Authentification getInstance() {
    if (accountManager == null) {
      accountManager = new Authentification();
    }
    return accountManager;
  }

  public Account createAccount(String username) {
    Account account = new Account(username, createTemporaryPassword());
    accounts.put(username, account);
    return account;
  }

  public char[] sendEmail(Account user) {
    char[] temporaryPassord = null;
    return temporaryPassord;
  }

  public Account login(String email, char[] password) throws AuthentificationFailedException {
    long startTime = System.currentTimeMillis();
    Account account = accounts.get(email);
    boolean success = false;

    if (account != null) {
      int nbAttempts = account.getAttempts();

      if (nbAttempts <= policy.getMaxAttempts()) {

        if (account.isPending()) {
          if (account.getTemporaryPassword().equals(password)) {
            account.loginAsTemporary();
            success = true;
          }
        } else if (account.isLoggedOut()) {
          String storedHash = account.getPasswordHash();
          String hash = passwordHasher.hash(password);

          if (storedHash.equals(hash)) {
            account.login();
            success = true;
          }
        }
      }
    }

    // sleep to prevent timing attack
    sleep(startTime);

    if (!success) {
      account.incrementAttempts();
      throw new AuthentificationFailedException();
    }


    return account;
  }

  public void changePassword(Account user, char[] password) {
    // TODO validate
    boolean valid = true;

    if (valid) {
      String hash = passwordHasher.hash(password);
      user.setPasswordHash(hash);
    }
  }

  public Account forgotPassword(String email) {
    Account account = accounts.get(email);

    if (account.isLoggedOut()) {
      account.setTemporaryPassword(createTemporaryPassword());
    }

    return account;
  }

  private char[] createTemporaryPassword() {
    return "temporary".toCharArray();
  }

  private void sleep(long startTime) {
    long processingTime = System.currentTimeMillis() - startTime;
    long remaining = Math.max(0, SLEEP_DURATION - processingTime);
    try {
      Thread.sleep(remaining);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static class AuthentificationFailedException extends Exception {

  }



}
