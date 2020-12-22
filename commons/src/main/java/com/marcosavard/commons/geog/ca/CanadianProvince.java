package com.marcosavard.commons.geog.ca;

import java.util.Locale;
import com.marcosavard.commons.geog.ca.res.ProvinceName;

/**
 * An enumeration for Canadian provinces and territories. Codes follow naming convention of Canada
 * Post.
 * 
 * @author Marco
 *
 */
public enum CanadianProvince {
  NL, //
  NS, //
  PE, //
  NB, //
  QC, //
  ON, //
  MB, //
  SK, //
  AB, //
  BC, //
  NU, //
  NT, //
  YK;

  public String getDisplayName() {
    return getDisplayName(Locale.getDefault());
  }

  public String getDisplayName(Locale locale) {
    String provinceCode = this.toString();
    String displayName = ProvinceName.getString(provinceCode, locale);
    return displayName;
  }

}
