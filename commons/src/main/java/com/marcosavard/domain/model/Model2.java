package com.marcosavard.domain.model;

import java.time.LocalDate;

public class Model2 {

  public static class Person {
    public static final String CONST = "99";
    public String firstName;
    public String lastName;
    public LocalDate birthDate;
    public Address home;
  }

  public static class Address {
    public String civicNumber;
    public String streetName;
    public String noApartment;
    public String provinceCode;
    public String postalCode;
    public String country;
  }
}
