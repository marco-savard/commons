package com.marcosavard.commons.util;

import java.util.Date;

public class ToStringBuilderDemo {

  public static void main(String[] args) {
    // LocalDate date =  LocalDate.now();
    Date date = new Date();
    String str = ToStringBuilder.build(date);
    System.out.println(str);
  }
}
