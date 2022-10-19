package com.marcosavard.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.lang.reflect.Field;

import com.marcosavard.commons.meta.annotations.*;
import com.marcosavard.domain.Branch;

public class Model8 {
  public enum Country {
    CA, US
  };

  public enum PhoneQualifier {
    HOME, CELL, WORK, FAX
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
    public Company employer;
    public Address officeAddress;
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
    public @NotNull String name;
    public @NotNull Address headquarter;
    public List<Worker> employees;
    public List<Phone> phoneNumbers;
    public List<Branch> divisions;
  }

  public static class Branch {
    public Company ownerCompany;
    public Address location;
    public String name;

    public Branch() throws NoSuchFieldException {
    }
  }

  public static class Phone {
    public @Readonly int countryCode;
    public @Readonly @NotNull String number;
    public @Readonly String extension;
    public PhoneQualifier qualifier;
    public boolean active;
  }
}