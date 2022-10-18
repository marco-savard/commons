package com.marcosavard.domain.model;

import java.time.LocalDate;
import com.marcosavard.commons.meta.annotations.Immutable;
import com.marcosavard.commons.meta.annotations.RequiredArgsConstructor;

public class Model4 {
  public enum Country {
    CA, US
  };

  public static class Person {
    public static final String CONST = "99";
    public String firstName;
    public String lastName;
    public LocalDate birthDate;
    public Address home;
  }

  @RequiredArgsConstructor
  public static @Immutable class Address {
    public String civicNumber;
    public String streetName;
    public String noApartment;
    public String provinceCode;
    public String postalCode;
    public Country country;
  }
}
