package com.marcosavard.commons.security;

import com.marcosavard.commons.debug.ExceptionHandler;
import com.marcosavard.commons.security.Authentification.AuthentificationFailedException;

public class AuthentificationDemo {
  private static ExceptionHandler exceptionHandler = new ExceptionHandler();

  public static void main(String[] args) {

    createValidAccount();

    // testValidConnection();
    // testForgotPassword();

    // testTimingAttack();
    testBruteForceAttack();
    // test long-password attack
    // test aging attack
    // test time-out attack

    System.out.println("Success");
  }



  private static void createValidAccount() {
    Authentification manager = Authentification.getInstance();

    // create
    Account user = manager.createAccount("user@company.com");
    manager.sendEmail(user);
    System.out.println(user);

    // choose permanent password
    try {
      user = manager.login("user@company.com", user.getTemporaryPassword());

      System.out.println(user);

      manager.changePassword(user, "abc".toCharArray());
      System.out.println(user);

      user.logout();
      System.out.println(user);
    } catch (AuthentificationFailedException e) {
      exceptionHandler.handle(e);
    }
  }

  private static void testValidConnection() {
    Authentification manager = Authentification.getInstance();
    Account user;

    try {
      user = manager.login("user@company.com", "abc".toCharArray());
      System.out.println(user);

      user.logout();
      System.out.println(user);

    } catch (AuthentificationFailedException e) {
      exceptionHandler.handle(e);
    }

  }

  private static void testForgotPassword() {
    Authentification manager = Authentification.getInstance();

    Account user = manager.forgotPassword("user@company.com");
    manager.sendEmail(user);
    System.out.println(user);

    // choose permanent password
    try {
      user = manager.login("user@company.com", user.getTemporaryPassword());
      manager.changePassword(user, "abc".toCharArray());
      System.out.println(user);

    } catch (AuthentificationFailedException e) {
      exceptionHandler.handle(e);
    }
  }

  private static void testTimingAttack() {
    Authentification manager = Authentification.getInstance();
    long startTime, duration;
    Account user;

    try {
      startTime = System.currentTimeMillis();
      user = manager.login("user@company.com", "abc".toCharArray());
      duration = System.currentTimeMillis() - startTime;
      System.out.println("  duration = " + duration);

      startTime = System.currentTimeMillis();
      user = manager.login("user@company.com", "blabla".toCharArray());
      duration = System.currentTimeMillis() - startTime;
      System.out.println("  duration = " + duration);

    } catch (AuthentificationFailedException e) {
      exceptionHandler.handle(e);
    }
  }

  private static void testBruteForceAttack() {
    Authentification manager = Authentification.getInstance();
    int nb = 10;

    Account user;
    try {
      user = manager.login("user@company.com", "abc".toCharArray());
      user.logout();
      System.out.println("  user = " + user);
    } catch (AuthentificationFailedException e) {
      exceptionHandler.handle(e);
    }

    for (int i = 0; i < nb; i++) {
      char[] password = String.valueOf(i).toCharArray();
      try {
        user = manager.login("user@company.com", password);
        System.out.println("  user = " + user);
      } catch (AuthentificationFailedException e) {
        System.out.println("    ..wrong password");
        // ignore and continue the attack
      }
    } // end for

    // try to reconnect
    try {
      user = manager.login("user@company.com", "abc".toCharArray());
      System.out.println("  user = " + user);
    } catch (AuthentificationFailedException e) {
      exceptionHandler.handle(e);
    }

  }

}
