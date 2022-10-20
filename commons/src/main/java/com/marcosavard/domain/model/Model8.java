package com.marcosavard.domain.model;

import com.marcosavard.commons.meta.annotations.Component;
import com.marcosavard.commons.meta.annotations.Description;
import com.marcosavard.commons.meta.annotations.Immutable;
import com.marcosavard.commons.meta.annotations.NotNull;
import com.marcosavard.commons.meta.annotations.Readonly;

import java.time.LocalDate;
import java.util.List;

public class Model8 {
  public enum Country {
    CA,
    US
  };

  public enum PhoneQualifier {
    HOME,
    CELL,
    WORK,
    FAX
  };

  @Description("represents a person who has a name and a birthdate")
  public static class Person {
    public static final String CONST = "99";
    public @NotNull String firstName;
    public String middleName;
    public @NotNull String lastName;
    public @NotNull LocalDate birthDate;
    public Address homeAddress;
    public List<Phone> phoneNumbers;
  }

  @Description("represents a person who can be hired by a company")
  public static class Worker extends Person {
    public @Readonly long workerId;
    public Company employer;
    public Address officeAddress;
  }

  @Description("represents an international address")
  public abstract static @Immutable class Address {
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
    public @Readonly String name;
    public @Readonly Address headquarter;
    public List<Phone> phoneNumbers;
    public @Component List<Branch> divisions;
  }

  public static class Branch {
    public @Readonly String name;
    public @Readonly Address location;
    public @Component List<Branch> subdivisions;
    public @Component List<Team> teams;
  }

  public static class Team {
    public @Readonly String name;
    public List<Worker> employees;
  }

  public static class Phone {
    public @Readonly int countryCode;
    public @Readonly @NotNull String number;
    public @Readonly String extension;
    public PhoneQualifier qualifier;
    public boolean active;
  }
}
