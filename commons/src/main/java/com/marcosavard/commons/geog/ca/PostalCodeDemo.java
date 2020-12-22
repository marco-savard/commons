package com.marcosavard.commons.geog.ca;

import java.text.MessageFormat;
import java.util.Locale;

public class PostalCodeDemo {

  private static final String[] POSTAL_CODES = new String[] {"A0A B1A", "G1A A1A"};

  public static void main(String[] args) {
    demoPostalCodeEquality();
    findProvinceByPostalCode();
  }

  private static void demoPostalCodeEquality() {
    PostalCode postalCode1 = PostalCode.of("G1A A1A ");
    PostalCode postalCode2 = PostalCode.of("g1aa1a");
    boolean equal = postalCode1.equals(postalCode2);
    System.out.println("Are equal : " + equal);
  }

  private static void findProvinceByPostalCode() {
    Locale fr = Locale.FRENCH;

    for (String code : POSTAL_CODES) {
      PostalCode postalCode = PostalCode.of(code);

      PostalCode.Region region = postalCode.getRegion();
      String province = postalCode.getProvince().getDisplayName(fr);
      String msg = MessageFormat.format("  {0} : province={1} region={2}",
          postalCode.toDisplayString(), province, region);
      System.out.println(msg);
    }
  }

}
