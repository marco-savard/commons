package com.marcosavard.commons.geog.world;

import com.marcosavard.commons.io.csv.CsvRows;

import java.nio.charset.StandardCharsets;

public class WorldCityResource extends CsvRows<WorldCityResource.Data> {

  public WorldCityResource() {
    super("WorldCity.csv", StandardCharsets.UTF_8);
    withNbHeaders(1);
    withDelimiter(';');
    withCommentCharacter('#');
  }

  public static class Data extends CvsRow {
    public String continent;
    public String country;
    public String region;
    public double latitude;
    public double longitude;
    public String capital;
    public String enName;
    public String frName;
  }
}
