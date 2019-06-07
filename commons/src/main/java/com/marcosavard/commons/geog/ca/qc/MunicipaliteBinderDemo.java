package com.marcosavard.commons.geog.ca.qc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import com.marcosavard.commons.geog.ca.qc.Municipality.Designation;
import com.marcosavard.commons.io.csv.CsvBinder;

public class MunicipaliteBinderDemo {

  public static void main(String[] args) {
    InputStream input = Municipality.class.getResourceAsStream("Municipalites.csv");
    Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
    CsvBinder<Municipality> binder = new CsvBinder<Municipality>(reader, ',', Municipality.class);

    try {
      List<Municipality> municipalities = binder.readAll();
      printMunicipalities(municipalities);
    } catch (IOException e) {
      e.printStackTrace(System.out);
    }
  }

  private static void printMunicipalities(List<Municipality> municipalities) {
    List<Municipality> cities;

    Municipality montreal = municipalities.stream()
        .filter(m -> m.getSearchName().equals("montreal")).findFirst().orElse(null);
    System.out.println("Montreal Postal Code : " + montreal.getPostalCode());
    System.out.println();

    Municipality stoneham = municipalities.stream()
        .filter(m -> m.getSearchName().equals("stoneham-et-tewkesbury")).findFirst().orElse(null);
    Designation des = stoneham.getDesignation();
    System.out.println("Stoneham : " + des);
    System.out.println();

    cities = municipalities.stream().filter(m -> m.getName().charAt(0) == 'W')
        .collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();

    cities = municipalities.stream().filter(m -> m.getPostalCode().startsWith("G3"))
        .collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();

    cities =
        municipalities.stream().filter(m -> m.getRegionAdmin() == 6).collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();

    cities = municipalities.stream().filter(m -> m.getDivisionRegionale().equals("93"))
        .collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();

    cities = municipalities.stream()
        .filter(m -> m.getNoTelephone().startsWith("418") && m.getRegionAdmin() == 4)
        .collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();

    cities = municipalities.stream()
        .filter(m -> m.getPostalCode().charAt(1) == '0' && m.getDivisionRegionale().equals("22"))
        .collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();
  }
}
