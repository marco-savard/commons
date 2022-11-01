package com.marcosavard.domain.model;

import java.time.LocalDate;
import com.marcosavard.commons.lang.reflect.meta.annotations.Immutable;
import com.marcosavard.commons.meta.annotations.NotNull;

public class Model6 {
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

  public static abstract @Immutable class Address {
    public String civicNumber;
    public String streetName;
    public String noApartment;
    public Country country;
  }

  public static @Immutable class CanadianAddress extends Address {
    public String provinceCode;
    public String postalCode;
  }

  public static @Immutable class USAddress extends Address {
    public String stateCode;
    public String zipCode;
  }

  public static class Company {
    public @NotNull String name;
    public @NotNull Address headquarter;
  }
}
