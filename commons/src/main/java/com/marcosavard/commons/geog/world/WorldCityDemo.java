package com.marcosavard.commons.geog.world;

import com.marcosavard.commons.geog.GeoLocation;
import com.marcosavard.commons.math.arithmetic.PseudoRandom;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class WorldCityDemo {

  public static void main(String[] args) {
    WorldCityResource ressource = new WorldCityResource();
    List<WorldCityResource.Data> allCities = ressource.getRows();
    PseudoRandom pr = new PseudoRandom(3);

    for (int i = 0; i < 100; i++) {
      findCity(allCities, pr, i);
    }
  }

  private static void findCity(List<WorldCityResource.Data> allCities, PseudoRandom pr, int i) {
    int i1 = pr.nextInt(allCities.size());
    WorldCityResource.Data fromCity = allCities.get(i1);
    int i2 = pr.nextInt(16);
    List<WorldCityResource.Data> cities = findCitiesByDirection(allCities, fromCity, i2);
    WorldCityResource.Data city = findClosestCity(cities, fromCity);

    GeoLocation fromPos = GeoLocation.of(fromCity.latitude, fromCity.longitude);
    GeoLocation cityPos = city == null ? null : GeoLocation.of(city.latitude, city.longitude);

    int dist = (int) ((city == null) ? 0 : fromPos.findDistanceFrom(cityPos));
    int bearing = (int) ((city == null) ? 0 : fromPos.findInitialBearingTo(cityPos));
    String dir = GeoLocation.bearingToOrientationText(bearing);

    if (city != null) {
      String question =
          MessageFormat.format(
              "Q {0} : Ville a {1} km au {2} de {3}", i, dist, dir, fromCity.frName);

      System.out.println(question);
      System.out.println("  Rep : " + city.frName);
      System.out.println();
    }
  }

  private static WorldCityResource.Data findClosestCity(
      List<WorldCityResource.Data> cities, WorldCityResource.Data fromCity) {
    GeoLocation pos = GeoLocation.of(fromCity.latitude, fromCity.longitude);
    double closestDistance = Double.MAX_VALUE;
    WorldCityResource.Data closestCity = null;

    for (WorldCityResource.Data c : cities) {
      double dist = pos.findDistanceFrom(GeoLocation.of(c.latitude, c.longitude));
      if (dist > 1.0 && dist < closestDistance) {
        closestDistance = dist;
        closestCity = c;
      }
    }

    return closestCity;
  }

  private static List<WorldCityResource.Data> findCitiesByDirection(
      List<WorldCityResource.Data> cities, WorldCityResource.Data fromCity, int dir) {
    List<WorldCityResource.Data> foundCities;

    do {
      foundCities = findCitiesByDirection2(cities, fromCity, dir);
      dir = ++dir % 16;
    } while (foundCities.isEmpty());

    return foundCities;
  }

  private static List<WorldCityResource.Data> findCitiesByDirection2(
      List<WorldCityResource.Data> cities, WorldCityResource.Data fromCity, int dir) {
    GeoLocation fromPos = GeoLocation.of(fromCity.latitude, fromCity.longitude);
    List<WorldCityResource.Data> foundCities = new ArrayList<>();

    for (WorldCityResource.Data c : cities) {
      GeoLocation pos = GeoLocation.of(c.latitude, c.longitude);
      double dist = fromPos.findDistanceFrom(pos);
      double bearing = fromPos.findInitialBearingTo(pos);
      int d = GeoLocation.bearingToOrientation(bearing);

      if ((dist > 1.0) && (d == dir)) {
        foundCities.add(c);
      }
    }

    return foundCities;
  }

  private static void printCity(WorldCityResource.Data city, WorldCityResource.Data fromCity) {
    GeoLocation pos1 = city == null ? null : GeoLocation.of(city.latitude, city.longitude);
    GeoLocation fromPos = GeoLocation.of(fromCity.latitude, fromCity.longitude);
    int dist = (int) ((city == null) ? 0 : fromPos.findDistanceFrom(pos1));
    int bearing = (int) ((city == null) ? 0 : fromPos.findInitialBearingTo(pos1));
    System.out.println("  " + city + " " + dist + " km" + " " + bearing + " deg");
  }
}
