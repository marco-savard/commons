package com.marcosavard.commons.geog.ca;

import java.text.MessageFormat;

public class PostalCodeDemo {

  private static final String[] POSTAL_CODES = new String[] {"A0A B1A", "G1A A1A"};

  public static void main(String[] args) {
    for (String code : POSTAL_CODES) {
      PostalCode postalCode = PostalCode.of(code);

      PostalCode.Region region = postalCode.getRegion();
      CanadianProvince province = postalCode.getProvince();
      String msg = MessageFormat.format("  {0} : province={1} region={2}",
          postalCode.toDisplayString(), province, region);
      System.out.println(msg);
    }
  }

}
