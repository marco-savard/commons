package com.marcosavard.commons.dbms;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.marcosavard.commons.io.csv.CsvReader;
import com.marcosavard.commons.io.csv.CsvReaderBuilder;

public class DataTableDemo {

  public static void main(String[] args) {
    try {
      System.out.println("Cities:");
      DataTable cities = DataTable.of(readCities());
      DataTable provinces = DataTable.of(readProvinces());
      cities.getValues().forEach(s -> System.out.println("  " + String.join(", ", s)));
      System.out.println();

      // inner join city's province code (c[1]) to province's code (p[0])
      System.out.println("Inner Join:");
      DataTable inner = cities.innerJoin(provinces).on((c, p) -> c[1].equals(p[0]));
      inner.getValues().forEach(s -> System.out.println("  " + String.join(", ", s)));
      System.out.println();

      // left join city's province code (c[1]) to province's code (p[0])
      System.out.println("Left Join:");
      DataTable left = cities.leftJoin(provinces).on((c, p) -> c[1].equals(p[0]));
      left.getValues().forEach(s -> System.out.println("  " + String.join(", ", s)));
      System.out.println();

      // keep city name and province name
      System.out.println("Select:");
      DataTable table4 = left.select(a -> new String[] {a[0], a[3]});
      table4.getValues().forEach(s -> System.out.println("  " + String.join(", ", s)));
      System.out.println();

      System.out.println("Success ");
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

  private static String[] CITIES =
      new String[] {"Toronto,ON", "Montreal,QC", "Vancouver,BC", "Calgary,AL"};

  private static List<String[]> readCities() throws IOException {
    List<String[]> cities = new ArrayList<>();

    for (String city : CITIES) {
      cities.add(city.split(","));
    }
    return cities;
  }

  private static List<String[]> readProvinces() throws IOException {
    InputStream input = DataTableDemo.class.getResourceAsStream("CanadianProvinces.csv");
    Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
    CsvReader cr = new CsvReaderBuilder(reader).withSeparator(',').build();
    List<String[]> provinces = cr.readAll();
    cr.close();
    reader.close();
    return provinces;
  }


}
