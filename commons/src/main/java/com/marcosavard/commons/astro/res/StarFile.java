package com.marcosavard.commons.astro.res;

import com.marcosavard.commons.res.CsvResourceFile;

import java.nio.charset.StandardCharsets;

class StarFile extends CsvResourceFile<Star> {

  static StarFile ofType(Class<Star> type) {
    return new StarFile(type);
  }

  private StarFile(Class type) {
    super("StarFile.csv", StandardCharsets.UTF_8, type);
    super.withSeparator(';');
    super.withQuoteChar('\"');
    super.withCommentPrefix("#");
    // super.withNbHeaders(1);
  }
}
