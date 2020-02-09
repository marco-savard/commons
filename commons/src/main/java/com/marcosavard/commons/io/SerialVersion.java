package com.marcosavard.commons.io;

public class SerialVersion {

  public static long of(Class<?> claz) {
    return claz.hashCode();
  }

}
