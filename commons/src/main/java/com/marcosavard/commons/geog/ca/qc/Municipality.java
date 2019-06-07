package com.marcosavard.commons.geog.ca.qc;

import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;

public class Municipality {
  public enum Designation {
    CANTON, CANTONS_UNIS, MUNICIPALITE, PAROISSE, VILLAGE, VILLE, UNKNOWN
  };

  private String mcode;
  private String munnom;
  private String mdes;
  private String regadm;
  private String divrec;
  private String mtel;
  private String mcodpos;

  public String getName() {
    return munnom;
  }

  public String getSearchName() {
    String searchName = toSearchText(munnom);
    return searchName;
  }

  public Designation getDesignation() {
    String searchName = toSearchText(mdes);
    List<Designation> designations = Arrays.asList(Designation.values());
    Designation designation =
        designations.stream().filter(d -> toSearchText(d.name()).equals(searchName)).findFirst()
            .orElse(Designation.UNKNOWN);
    return designation;
  }

  public String getNoTelephone() {
    return mtel;
  }

  public String getPostalCode() {
    return mcodpos;
  }

  public int getRegionAdmin() {
    int i1 = regadm.indexOf('(');
    int i2 = regadm.indexOf(')');
    String number = regadm.substring(i1 + 1, i2);
    int regionCode = Integer.parseInt(number);
    return regionCode;
  }

  public String getDivisionRegionale() {
    int idx = divrec.lastIndexOf("(");
    String regionCode = divrec.substring(idx + 1, idx + 3);
    return regionCode;
  }

  @Override
  public String toString() {
    String str = MessageFormat.format(
        "'{'mcode={0}, munnom={1} ({5}), region={2}, div={3}, tel={4}'}'", mcode, munnom,
        getRegionAdmin(), getDivisionRegionale(), getNoTelephone(), getDesignation());
    return str;
  }

  private String toSearchText(String original) {
    String searchText = Normalizer.normalize(original, Normalizer.Form.NFD);
    searchText = searchText.replaceAll("[^\\p{ASCII}]", "");
    searchText = searchText.replaceAll("_", "");
    searchText = searchText.replaceAll(" ", "");
    searchText = searchText.toLowerCase();
    return searchText;
  }

}
