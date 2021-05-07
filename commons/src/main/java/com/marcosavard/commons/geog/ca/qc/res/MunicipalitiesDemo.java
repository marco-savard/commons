package com.marcosavard.commons.geog.ca.qc.res;

import java.util.List;
import java.util.stream.Collectors;

import com.marcosavard.commons.geog.ca.qc.res.Municipalite.Designation;

public class MunicipalitiesDemo {

  public static void main(String[] args) {
    // get data
    Municipalities municipalities = Municipalities.ofType(Municipalite.class);
    List<Municipalite> allCities = municipalities.readAll(); 
    municipalities.close();
    
    // print top 10
    List<Municipalite> top10 = allCities.stream() //
        .limit(10) //
        .collect(Collectors.toList());
    top10.forEach(System.out::println);
    System.out.println();
    
    // print Montreal infos
    Municipalite montreal = allCities.stream()
        .filter(m -> m.getSearchName().equalsIgnoreCase("montreal")).findFirst().orElse(null);
    System.out.println(montreal);
    System.out.println();
    
    // print cities starting by W
    List<Municipalite> cities = allCities.stream().filter(m -> m.getName().charAt(0) == 'W')
        .collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();
    
    // print cities starting by St
    cities = allCities.stream().filter(m -> m.getName().substring(0, 2).equalsIgnoreCase("st"))
        .collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();
    
    // print Stoneham
    Municipalite stoneham =
    		allCities.stream().filter(m -> m.getName().equalsIgnoreCase("stoneham-et-tewkesbury"))
            .findFirst().orElse(null);
    Designation des = stoneham.getDesignation();
    System.out.println("Stoneham : " + des);
    System.out.println();
    
    // print cities whose postal code starts by G3
    cities = allCities.stream().filter(m -> m.getPostalCode().startsWith("G3"))
        .collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();
    
    // print cities in region 06
    cities =
    		allCities.stream().filter(m -> m.getRegionAdmin() == 6).collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();
    
    // print cities in division regionale 93
    cities = allCities.stream().filter(m -> m.getDivisionRegionale().equals("93"))
        .collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();
    
    // print cities in region 04 whose area code is 418
    cities = allCities.stream()
        .filter(m -> m.getNoTelephone().startsWith("418") && m.getRegionAdmin() == 4)
        .collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();
    
    // print rural cities in division regionale 22
    cities = allCities.stream()
        .filter(m -> m.getPostalCode().charAt(1) == '0' && m.getDivisionRegionale().equals("22"))
        .collect(Collectors.toList());
    cities.forEach(System.out::println);
    System.out.println();
    
    System.out.println();
  }

}
