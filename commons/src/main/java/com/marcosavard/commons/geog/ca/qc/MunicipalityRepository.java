package com.marcosavard.commons.geog.ca.qc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.marcosavard.commons.io.csv.CsvBinder;

public class MunicipalityRepository {
  private List<Municipality> municipalities;

  public static MunicipalityRepository getMunicipalityRepository() {
    MunicipalityRepository repository = new MunicipalityRepository();
    return repository;
  }

  private MunicipalityRepository() {
    InputStream input = Municipality.class.getResourceAsStream("Municipalites.csv");
    Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
    CsvBinder<Municipality> binder = new CsvBinder<Municipality>(reader, ',', Municipality.class);

    try {
      municipalities = binder.readAll();
      reader.close();
    } catch (IOException e) {
      municipalities = new ArrayList<>();
    }
  }

  public List<Municipality> getMunicipalities() {
    return municipalities;
  }

}
