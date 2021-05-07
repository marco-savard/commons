package com.marcosavard.commons.geog.ca.qc.educ.res;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.marcosavard.commons.res.CsvResourceFile;

public class QuebecSchools extends CsvResourceFile<QuebecSchool> {

  public static QuebecSchools ofType(Class<QuebecSchool> type) {
		return new QuebecSchools(type); 
  }
	
  private QuebecSchools(Class type) {
    super("QuebecSchools.csv", StandardCharsets.ISO_8859_1, type);
    super.withSeparator(';');
  }









}
