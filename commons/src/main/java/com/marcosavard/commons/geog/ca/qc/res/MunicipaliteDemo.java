package com.marcosavard.commons.geog.ca.qc.res;

import java.text.MessageFormat;

public class MunicipaliteDemo {

  public static void main(String[] args) {
    Municipalite city1, city2;

    city1 = Municipalite.of("Anse St-jean");
    city2 = Municipalite.of("L'Anse Saint-Jean");
    compare(city1, city2);

    city1 = Municipalite.of("Ile-aux-Coudres");
    city2 = Municipalite.of("Isle aux Coudres");
    compare(city1, city2);

    city1 = Municipalite.of("Ile Verte");
    city2 = Municipalite.of("L'Ile-verte");
    compare(city1, city2);

    city1 = Municipalite.of("Mnt. Pleasant");
    city2 = Municipalite.of("Mt Pleasant");
    compare(city1, city2);

    city1 = Municipalite.of("Montreal ");
    city2 = Municipalite.of("Montréal");
    compare(city1, city2);

    city1 = Municipalite.of("Mont Tremblant");
    city2 = Municipalite.of("Mt-Tremblant");
    compare(city1, city2);

    city1 = Municipalite.of("St-Jean");
    city2 = Municipalite.of("Saint-Jean");
    compare(city1, city2);

    city1 = Municipalite.of("St. Paul");
    city2 = Municipalite.of("Saint-Paul");
    compare(city1, city2);
  }

  private static void compare(Municipalite city1, Municipalite city2) {
    String equal = city1.equals(city2) ? "is the same as" : "differs from";
    System.out.println(MessageFormat.format("''{0}'' {1} ''{2}''", city1, equal, city2));

  }

}
