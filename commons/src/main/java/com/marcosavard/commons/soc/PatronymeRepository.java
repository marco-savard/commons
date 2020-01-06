package com.marcosavard.commons.soc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.marcosavard.commons.io.csv.CsvReader;
import com.marcosavard.commons.text.WordDistance;

/**
 * Build a patronyme repository based on the content of a patronyme text file.
 * 
 * @author Marco
 *
 */
public class PatronymeRepository {
  private static final String PATRONYME_RESOURCE = "Patronymes.csv";
  private static List<Patronyme> patronymes = new ArrayList<>();

  /**
   * Get the list of the 1000 most common patronymes in Quebec.
   * 
   * @return the list of common patronymes
   */
  public static List<Patronyme> getPatronymes() {
    if (patronymes.isEmpty()) {
      InputStream input = PatronymeRepository.class.getResourceAsStream(PATRONYME_RESOURCE);
      Reader r = new InputStreamReader(input);

      try {
        CsvReader cr = CsvReader.of(r);
        patronymes = readPatronymes(cr);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

    return patronymes;
  }

  /**
   * From the repository, return the patronyme which is the closest to name. For instance
   * getClosestPatronyme("Tramblay") will return "Tremblay".
   *
   * @param name that could be misspelled
   * @return the patronyme that is the most similar
   */
  public static Patronyme findMostSimilarPatronyme(String name) {
    List<Patronyme> patronymes = PatronymeRepository.getPatronymes();
    int closestDistance = Integer.MAX_VALUE;
    Patronyme closestPatronyme = null;

    for (Patronyme p : patronymes) {
      int distance = WordDistance.levenshteinDistance(p.getName(), name);

      if (distance < closestDistance) {
        closestDistance = distance;
        closestPatronyme = p;
      }
    }
    return closestPatronyme;
  }

  //
  // private methods
  //

  private static List<Patronyme> readPatronymes(CsvReader cr) throws IOException {
    List<Patronyme> patronymes = new ArrayList<>();
    String[] columns = cr.readHeaderColumns();

    while (cr.hasNext()) {
      String[] line = cr.readNext();
      if (line.length > 0) {
        Patronyme patronyme = readPatronyme(columns, line);
        patronymes.add(patronyme);
      }
    }
    return patronymes;
  }

  private static Patronyme readPatronyme(String[] columns, String[] line) {
    String name = readValue(columns, line, "Nom");
    Patronyme patronyme = new Patronyme(name);
    return patronyme;
  }

  private static String readValue(String[] columns, String[] line, String fieldName) {
    int idx = Arrays.asList(columns).indexOf(fieldName);
    String value = line[idx];
    return value;
  }



}
