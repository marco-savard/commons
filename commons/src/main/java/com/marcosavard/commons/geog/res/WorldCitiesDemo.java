package com.marcosavard.commons.geog.res;

import java.util.List;
import com.marcosavard.commons.geog.res.WorldCities.WorldCity;

public class WorldCitiesDemo {

  public static void main(String[] args) {
    // get data
    WorldCities worldCities = new WorldCities();
    List<WorldCity> rows = worldCities.getRows();

    int nb = Math.min(15, rows.size());
    List<WorldCity> firstRows = rows.subList(0, nb);

    for (WorldCity value : firstRows) {
      System.out.println("  value : " + value.toString());
    }

    System.out.println("Done");

    // worldCities.loadAll();
    // List<String> columns = worldCities.getColumns();
    // List<String[]> rows = worldCities.getRows();

  }

}
