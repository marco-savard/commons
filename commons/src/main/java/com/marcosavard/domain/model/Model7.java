package com.marcosavard.domain.model;

import java.time.LocalDate;
import java.util.List;

import com.marcosavard.commons.lang.reflect.meta.annotations.Immutable;
import com.marcosavard.commons.lang.reflect.meta.annotations.Readonly;
import com.marcosavard.commons.meta.annotations.NotNull;

public class Model7 {
  public enum Country {
    CA, US
  };

  public enum PhoneQualifier {
    HOME, CELL, WORK, FAX
  };

  public static class Person {
    public static final String CONST = "99";
    public String firstName;
    public String lastName;
    public LocalDate birthDate;
    public Address home;
    public Company employer;
    public List<Phone> phoneNumbers;
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
    public List<Phone> phoneNumbers;
    public List<Person> employees;
  }

  public static @Immutable class Phone {
    public @NotNull String number;
    public @Readonly String extension;
    public PhoneQualifier qualifier;
    public int countryCode;
  }
}
