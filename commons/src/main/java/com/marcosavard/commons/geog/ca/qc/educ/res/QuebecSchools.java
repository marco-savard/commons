package com.marcosavard.commons.geog.ca.qc.educ.res;

import java.nio.charset.StandardCharsets;
import com.marcosavard.commons.res.CommaSeparatedValueResource;

public class QuebecSchools extends CommaSeparatedValueResource {

  public QuebecSchools() {
    super("QuebecSchools.csv", StandardCharsets.ISO_8859_1);
    super.withNbHeaders(1);
    super.withDelimiter(';');
  }



}
