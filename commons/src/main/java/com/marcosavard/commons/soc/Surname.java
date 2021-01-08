package com.marcosavard.commons.soc;

import com.marcosavard.commons.lang.CharString;

public class Surname {
  public enum Gender {
    MALE, FEMALE
  };

  private String sanitizedName;
  private Gender gender;

  public Surname(String name, Gender gender) {
    sanitizedName = name.trim().toLowerCase();
    sanitizedName = CharString.of(sanitizedName).capitalize();
    this.gender = gender;
  }

  @Override
  public String toString() {
    return sanitizedName;
  }

  public Gender getGender() {
    return gender;
  }

}
