package com.marcosavard.domain.model;

import java.time.LocalDate;
import com.marcosavard.commons.meta.annotations.Readonly;

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

  public static class Address {
    public @Readonly String civicNumber;
    public @Readonly String streetName;
    public String noApartment;
    public String provinceCode;
    public String postalCode;
    public Country country;
  }
}
