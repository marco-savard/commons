package com.marcosavard.domain.model;

import java.time.LocalDate;
import java.util.List;
import com.marcosavard.commons.meta.annotations.AllArgsConstructor;
import com.marcosavard.commons.meta.annotations.Containment;
import com.marcosavard.commons.meta.annotations.Description;
import com.marcosavard.commons.meta.annotations.Immutable;
import com.marcosavard.commons.meta.annotations.Readonly;
import com.marcosavard.commons.meta.annotations.Required;
import com.marcosavard.commons.meta.annotations.RequiredArgsConstructor;

public class Model8 {
  public enum Country {
    CA, US
  };

  public enum PhoneQualifier {
    HOME, CELL, WORK, FAX
  };

  @Description("A person that has a name and a birth date")
  public static class Person {
    public static final String CONST = "99";
    public String firstName;
    public String lastName;
    public LocalDate birthDate;
    public Address home;
    public Company employer;
    public List<Phone> phoneNumbers;
  }

  @Description("An international address")
  @RequiredArgsConstructor
  public static abstract @Immutable class Address {
    public String civicNumber;
    public String streetName;
    public String noAppartment;
    public Country country;
  }

  @Description("A Canadian address")
  @RequiredArgsConstructor
  public static @Immutable class CanadianAddress extends Address {
    public String provinceCode;
    public String postalCode;
  }

  @Description("A US address")
  @RequiredArgsConstructor
  public static @Immutable class USAddress extends Address {
    public String stateCode;
    @Description("two-letter ZIP code")
    public String zipCode;
  }

  @Description("A Company")
  @RequiredArgsConstructor
  public static class Company {
    public @Required String name;
    public @Required Address headquarter;
    public List<Phone> phoneNumbers;
    public List<Person> employees;
    public @Containment List<Branch> divisions;
  }

  public static class Branch {
    public Address location;
    public String name;
  }

  @RequiredArgsConstructor
  @AllArgsConstructor
  public static @Immutable class Phone {
    public @Required String number;
    public @Readonly String extension;
    public PhoneQualifier qualifier;
    public int countryCode;
  }

}
