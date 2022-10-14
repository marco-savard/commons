package com.marcosavard.domain.model;

import java.time.LocalDate;
import com.marcosavard.commons.meta.annotations.Immutable;
import com.marcosavard.commons.meta.annotations.RequiredArgsConstructor;

public class Model5 {
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
  public static abstract @Immutable class Address {
    public String civicNumber;
    public String streetName;
    public String noAppartment;
    public Country country;
  }

  @RequiredArgsConstructor
  public static @Immutable class CanadianAddress extends Address {
    public String provinceCode;
    public String postalCode;
  }

  @RequiredArgsConstructor
  public static @Immutable class USAddress extends Address {
    public String stateCode;
    public String zipCode;
  }
}
