package com.marcosavard.commons.geog.res;

import java.nio.charset.StandardCharsets;
import com.marcosavard.commons.geog.res.WorldCities.WorldCity;
import com.marcosavard.commons.io.csv.CsvRows;

public class WorldCities extends CsvRows<WorldCity> {

  public WorldCities() {
    super("WorldCities.csv", StandardCharsets.ISO_8859_1);
    withNbHeaders(1);
    withDelimiter(';');
    withCommentCharacter('#');
  }

  public static class WorldCity extends CsvRows.CvsRow {
    public String timezoneId;
    public String country;
    public String state;
    public String latitude;
    public String longitude;
  }



}