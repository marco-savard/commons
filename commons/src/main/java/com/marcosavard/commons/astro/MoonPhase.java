package com.marcosavard.commons.astro;

import com.marcosavard.commons.util.ToStringBuilder;

public class MoonPhase {
  public static final double MOON_PERIOD = 29.5; // Moon's period in days
  private double percent;

  public enum Tendency {
    INCREASING, DECREASING
  };
  public enum PhaseName {
    NEW, WAXING_CRESCENT, FIRST_QUARTER, WAXING_GIBBOUS, FULL, WANING_GIBBOUS, THIRST_QUARTER, WANING_CRESCENT
  };

  public static MoonPhase of(double percent) {
    MoonPhase phase = new MoonPhase(percent);
    return phase;
  }

  public double getAgeInDays() {
    return (percent / 100.0) * MOON_PERIOD;
  }

  public int getIllumationPercent() {
    double angle = percent * Math.PI / 100.0;
    int illumination = (int) (100 * Math.sin(angle));
    return illumination;
  }

  public PhaseName getPhaseName() {
    int idx = (int) Math.round((percent / 100.0) * 8.0);
    idx = (idx + 8) & 7; // & 7 equivalent to % 8
    PhaseName phaseName = PhaseName.values()[idx];
    return phaseName;
  }

  public Tendency getTendency() {
    Tendency tendency = (percent < 50) ? Tendency.INCREASING : Tendency.DECREASING;
    return tendency;
  }

  private MoonPhase(double percent) {
    this.percent = percent;
  }

  @Override
  public String toString() {
    return ToStringBuilder.build(this);
  }

}
