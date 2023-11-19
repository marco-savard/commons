package com.marcosavard.commons.geog.us;

import java.util.Locale;
import java.util.ResourceBundle;

public enum State {
  AL(Category.STATE),
  AK(Category.STATE),
  AS(Category.TERRITORY),
  AZ(Category.STATE),
  AR(Category.STATE),
  CA(Category.STATE),
  CO(Category.STATE),
  CT(Category.STATE),
  DE(Category.STATE),
  DC(Category.DISTRICT),
  FL(Category.STATE),
  GA(Category.STATE),
  GU(Category.TERRITORY),
  HI(Category.STATE),
  ID(Category.STATE),
  IL(Category.STATE),
  IN(Category.STATE),
  IA(Category.STATE),
  KS(Category.STATE),
  KY(Category.STATE),
  LA(Category.STATE),
  ME(Category.STATE),
  MD(Category.STATE),
  MA(Category.STATE),
  MI(Category.STATE),
  MN(Category.STATE),
  MS(Category.STATE),
  MO(Category.STATE),
  MT(Category.STATE),
  NE(Category.STATE),
  NV(Category.STATE),
  NH(Category.STATE),
  NJ(Category.STATE),
  NM(Category.STATE),
  MY(Category.STATE),
  NC(Category.STATE),
  ND(Category.STATE),
  MP(Category.TERRITORY),
  OH(Category.STATE),
  OK(Category.STATE),
  OR(Category.STATE),
  PA(Category.STATE),
  PR(Category.TERRITORY),
  RI(Category.STATE),
  SC(Category.STATE),
  SD(Category.STATE),
  TN(Category.STATE),
  TX(Category.STATE),
  UT(Category.STATE),
  VT(Category.STATE),
  VA(Category.STATE),
  VI(Category.STATE),
  WA(Category.STATE),
  WV(Category.STATE),
  WI(Category.STATE),
  WY(Category.STATE);

  public Category getCategory() {
    return category;
  }

  public enum Category {
    STATE,
    TERRITORY,
    DISTRICT
  };

  private Category category;
  private int symbol;

  State(Category category) {
    this.category = category;
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
