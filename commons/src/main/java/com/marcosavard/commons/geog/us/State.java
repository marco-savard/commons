package com.marcosavard.commons.geog.us;

import java.util.Locale;
import java.util.ResourceBundle;

public enum State {
  AL(Category.STATE, Region.SOUTH),
  AK(Category.STATE, Region.PACIFIC),
  AS(Category.TERRITORY, Region.PACIFIC),
  AZ(Category.STATE, Region.SOUTH_WEST),
  AR(Category.STATE, Region.SOUTH),
  CA(Category.STATE, Region.PACIFIC),
  CO(Category.STATE, Region.SOUTH_WEST),
  CT(Category.STATE, Region.EAST_COAST),
  DE(Category.STATE, Region.EAST_COAST),
  DC(Category.DISTRICT, Region.EAST_COAST),
  FL(Category.STATE, Region.SOUTH),
  GA(Category.STATE, Region.EAST_COAST),
  GU(Category.TERRITORY, Region.PACIFIC),
  HI(Category.STATE, Region.PACIFIC),
  ID(Category.STATE, Region.NORTH_WEST),
  IL(Category.STATE, Region.MIDWEST),
  IN(Category.STATE, Region.MIDWEST),
  IA(Category.STATE, Region.MIDWEST),
  KS(Category.STATE, Region.MIDWEST),
  KY(Category.STATE, Region.SOUTH),
  LA(Category.STATE, Region.SOUTH),
  ME(Category.STATE, Region.EAST_COAST),
  MD(Category.STATE, Region.EAST_COAST),
  MA(Category.STATE, Region.EAST_COAST),
  MI(Category.STATE, Region.MIDWEST),
  MN(Category.STATE, Region.MIDWEST),
  MS(Category.STATE, Region.SOUTH),
  MO(Category.STATE, Region.MIDWEST),
  MT(Category.STATE, Region.NORTH_WEST),
  NE(Category.STATE, Region.MIDWEST),
  NV(Category.STATE, Region.SOUTH_WEST),
  NH(Category.STATE, Region.EAST_COAST),
  NJ(Category.STATE, Region.EAST_COAST),
  NM(Category.STATE, Region.SOUTH_WEST),
  NY(Category.STATE, Region.EAST_COAST),
  NC(Category.STATE, Region.SOUTH),
  ND(Category.STATE, Region.MIDWEST),
  MP(Category.TERRITORY, Region.PACIFIC),
  OH(Category.STATE, Region.MIDWEST),
  OK(Category.STATE, Region.SOUTH),
  OR(Category.STATE, Region.PACIFIC),
  PA(Category.STATE, Region.EAST_COAST),
  PR(Category.TERRITORY, Region.ATLANTIC),
  RI(Category.STATE, Region.EAST_COAST),
  SC(Category.STATE, Region.SOUTH),
  SD(Category.STATE, Region.MIDWEST),
  TN(Category.STATE, Region.SOUTH),
  TX(Category.STATE, Region.SOUTH_WEST),
  UT(Category.STATE, Region.SOUTH_WEST),
  VT(Category.STATE, Region.EAST_COAST),
  VA(Category.STATE, Region.SOUTH),
  VI(Category.TERRITORY, Region.ATLANTIC),
  WA(Category.STATE, Region.PACIFIC),
  WV(Category.STATE, Region.EAST_COAST),
  WI(Category.STATE, Region.MIDWEST),
  WY(Category.STATE, Region.NORTH_WEST);

  public Category getCategory() {
    return category;
  }

  public Region getRegion() {
    return region;
  }

  public enum Category {
    STATE,
    TERRITORY,
    DISTRICT
  };

  public enum Region {
    ATLANTIC,
    EAST_COAST,
    MIDWEST,
    SOUTH,
    SOUTH_WEST,
    NORTH_WEST,
    PACIFIC
  };

  private Category category;
  private Region region;
  private int symbol;

  State(Category category, Region region) {
    this.category = category;
    this.region = region;
  }

  public String getDisplayName(Locale locale) {
    Class claz = State.class;
    String basename = claz.getName().replace('.', '/');
    ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
    String displayName = bundle.getString(this.name());
    return displayName;
  }

  public String getSymbol() {
    return Character.toString(symbol);
  }
}
