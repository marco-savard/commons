package com.marcosavard.commons.ling.processing;

public class Noun implements Comparable<Noun> {
  public enum Gender {
    MASC, FEM, UNKNOWN
  };
  public enum Number {
    SING, PLUR, UNKNOWN
  };

  private String text;
  private Gender gender;
  private Number number;

  public Noun(String text, Gender gender, Number number) {
    this.text = text;
    this.gender = gender;
    this.number = number;
  }

  @Override
  public String toString() {
    return text;
  }

  public boolean isSingular() {
    return (this.number == Number.SING);
  }

  public boolean isPlural() {
    return (this.number == Number.PLUR);
  }

  public boolean isMasculine() {
    return (this.gender == Gender.MASC);
  }

  public String getText() {
    return text;
  }

  @Override
  public int compareTo(Noun other) {
    int comparison = this.text.compareTo(other.getText());
    return comparison;
  }



}
