package com.marcosavard.commons.geog.ca.qc.res;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.marcosavard.commons.res.CsvResourceFile;

public class Municipalities extends CsvResourceFile<Municipalite> {

  public static Municipalities ofType(Class<Municipalite> type) {
		return new Municipalities(type); 
  }
	
  private Municipalities(Class type) {
    super("Municipalites.csv", StandardCharsets.UTF_8, type);
    super.withSeparator(',');
  }









}
