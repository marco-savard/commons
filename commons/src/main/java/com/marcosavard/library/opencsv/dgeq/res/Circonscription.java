package com.marcosavard.library.opencsv.dgeq.res;

import com.marcosavard.commons.res.CsvResourceFile;

import java.nio.charset.StandardCharsets;

public class Circonscription {
  private int bsq;
  private String circonsciption;
  private int region1, region2, region3;

  public int getBsq() {
    return bsq;
  }

  public int getRegion1() {
    return region1;
  }

  public int getRegion2() {
    return region2;
  }

  public int getRegion3() {
    return region3;
  }

  public static class CsvFile extends CsvResourceFile<Circonscription> {
    protected CsvFile() {
      super("circonscriptions.csv", StandardCharsets.ISO_8859_1, Circonscription.class);
      super.withHeaderSeparator(',');
      super.withSeparator(',');
    }
  }
}
