package com.marcosavard.domain.model;

import java.time.LocalDate;
import java.util.List;
import com.marcosavard.commons.meta.annotations.Containment;
import com.marcosavard.commons.meta.annotations.Description;
import com.marcosavard.commons.meta.annotations.Immutable;
import com.marcosavard.commons.meta.annotations.Readonly;
import com.marcosavard.commons.meta.annotations.Required;

public class Model8 {
  public enum Country {
    CA, US
  };

  public enum PhoneQualifier {
    HOME, CELL, WORK, FAX
  };

  @Description("represents a person that has a name and a birthdate")
  public static class Person {
    public static final String CONST = "99";
    public String firstName;
    public String lastName;
    public LocalDate birthDate;
    public Address home;
    public Company employer;
    public List<Phone> phoneNumbers;
  }

  @Description("represents an international address")
  public static abstract @Immutable class Address {
    public String civicNumber;
    public String streetName;
    public String noApartment;
    public Country country;
  }

  @Description("represents a Canadian address")
  public static @Immutable class CanadianAddress extends Address {
    public String provinceCode;
    public String postalCode;
  }

  @Description("represents a U.S. address")
  public static @Immutable class USAddress extends Address {
    public String stateCode;

    @Description("two-letter ZIP code")
    public String zipCode;
  }

  @Description("represents a company")
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

  public static @Immutable class Phone {
    public @Readonly int countryCode;
    public @Required String number;
    public @Readonly String extension;
    public PhoneQualifier qualifier;
  }
}