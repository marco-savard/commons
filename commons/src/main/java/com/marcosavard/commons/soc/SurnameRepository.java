package com.marcosavard.commons.soc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.marcosavard.commons.io.csv.CsvReader;

public class SurnameRepository {
  private static final String FEMALE_RESOURCE = "filles1980-2017.csv";
  private static final String MALE_RESOURCE = "gars1980-2017.csv";

  private static List<Surname> allSurnames = new ArrayList<>();

  public static List<Surname> getSurnames() {
    if (allSurnames.isEmpty()) {
      readResource(MALE_RESOURCE, Surname.Gender.MALE);
      readResource(FEMALE_RESOURCE, Surname.Gender.FEMALE);
    }

    return allSurnames;
  }

  //
  // private methods
  //

  private static void readResource(String resource, Surname.Gender gender) {
    InputStream input = PatronymeRepository.class.getResourceAsStream(resource);
    Reader r = new InputStreamReader(input);

    try {
      CsvReader cr = new CsvReader(r, 1, ',');
      List<Surname> surnames = readSurnames(cr, gender);
      allSurnames.addAll(surnames);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }

  }

  private static List<Surname> readSurnames(CsvReader cr, Surname.Gender gender)
      throws IOException {
    List<Surname> surnames = new ArrayList<>();
    String[] columns = cr.readHeaderColumns();

    while (cr.hasNext()) {
      String[] line = cr.readNext();
      if (line.length > 0) {
        readSurname(surnames, columns, line, gender);
      }
    }
    return surnames;
  }

  private static void readSurname(List<Surname> surnames, String[] columns, String[] line,
      Surname.Gender gender) {
    String name = readValue(columns, line, "Pr�nom/Ann�e");
    int occurrences = Integer.parseInt(readValue(columns, line, "2017"));

    if (occurrences >= 50) {
      Surname surname = new Surname(name, gender);
      surnames.add(surname);
    }
  }

  private static String readValue(String[] columns, String[] line, String fieldName) {
    int idx = Arrays.asList(columns).indexOf(fieldName);
    String value = line[idx];
    return value;
  }

}
