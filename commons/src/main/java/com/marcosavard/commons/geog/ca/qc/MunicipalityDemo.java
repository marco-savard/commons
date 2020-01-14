package com.marcosavard.commons.geog.ca.qc;

import java.text.MessageFormat;

public class MunicipalityDemo {

  public static void main(String[] args) {
    Municipality city1, city2;

    city1 = Municipality.of("Anse St-jean");
    city2 = Municipality.of("L'Anse Saint-Jean");
    compare(city1, city2);

    city1 = Municipality.of("Île-aux-Coudres");
    city2 = Municipality.of("Isle aux Coudres");
    compare(city1, city2);

    city1 = Municipality.of("Île Verte");
    city2 = Municipality.of("L'Ile-verte");
    compare(city1, city2);

    city1 = Municipality.of("Mnt. Pleasant");
    city2 = Municipality.of("Mt Pleasant");
    compare(city1, city2);

    city1 = Municipality.of("Montreal ");
    city2 = Municipality.of("Montréal");
    compare(city1, city2);

    city1 = Municipality.of("Mont Tremblant");
    city2 = Municipality.of("Mt-Tremblant");
    compare(city1, city2);

    city1 = Municipality.of("St-Jean");
    city2 = Municipality.of("Saint-Jean");
    compare(city1, city2);

    city1 = Municipality.of("St. Paul");
    city2 = Municipality.of("Saint-Paul");
    compare(city1, city2);
  }

  private static void compare(Municipality city1, Municipality city2) {
    String equal = city1.equals(city2) ? "is the same as" : "differs from";
    System.out.println(MessageFormat.format("''{0}'' {1} ''{2}''", city1, equal, city2));

  }

}
