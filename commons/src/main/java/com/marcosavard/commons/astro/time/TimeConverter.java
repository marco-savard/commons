package com.marcosavard.commons.astro.time;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TimeConverter {

  public static ZonedDateTime toZonedDateTime(ZonedDateTime moment, ZoneOffset utc) {
    Instant instant = moment.toInstant();
    ZonedDateTime ut = instant.atZone(utc);
    return ut;
  }
}
