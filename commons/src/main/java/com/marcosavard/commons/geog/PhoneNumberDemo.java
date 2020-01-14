package com.marcosavard.commons.geog;

public class PhoneNumberDemo {

  public static void main(String[] args) {
    PhoneNumber[] phoneNumbers = new PhoneNumber[] { //
        PhoneNumber.of("418 123-4567"), //
        PhoneNumber.of("418 224-2244"), //
    };

    String[] patterns = new String[] {"{0} {1}-{2}", "{0}.{1}.{2}", "({0}) {1}-{2}",};

    for (String pattern : patterns) {
      System.out.println("Phone numbers");
      for (PhoneNumber phoneNumber : phoneNumbers) {
        System.out.println("  " + phoneNumber.toDisplayString(pattern));
      }
      System.out.println();
    }
  }

}
