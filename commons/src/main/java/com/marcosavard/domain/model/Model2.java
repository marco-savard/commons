package com.marcosavard.domain.model;

import java.time.LocalDate;
import com.marcosavard.commons.meta.annotations.AllArgsConstructor;
import com.marcosavard.commons.meta.annotations.Required;
import com.marcosavard.commons.meta.annotations.RequiredArgsConstructor;

public class Model2 {

  public class Person {
    public static final String CONST = "99";
    public String firstName;
    public String lastName;
    public LocalDate birthDate;
    public Address home;
  }

  @RequiredArgsConstructor
  @AllArgsConstructor
  public class Address {
    public @Required String civicNumber;
    public @Required String streetName;
    public String noAppartment;
    public @Required String provinceCode;
    public @Required String postalCode;
    public @Required String country;
  }
}