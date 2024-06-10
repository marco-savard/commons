package com.marcosavard.commons.geog.ca;

import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.math.RessourceEnum;

import java.util.List;
import java.util.Locale;

/**
 * An enumeration for Canadian provinces and territories. Codes follow naming convention of Canada
 * Post.
 *
 * @author Marco
 */
public enum CanadianProvince implements RessourceEnum {
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

  public enum Style {
    SIMPLE,
    WITH_ARTICLE,
    GENITIVE,
    LOCATIVE
  }

  public String getDisplayName(Locale display, Style style) {
    String name = getDisplayName(display);

    if (display.getLanguage().equals(Locale.FRENCH.getLanguage())) {
      return getFrDisplayName(style);
    }

    return name;
  }

  private String getFrDisplayName(Style style) {
    String name = getDisplayName(Locale.FRENCH);

    if (style == Style.WITH_ARTICLE) {
      boolean withArticle = (this != NL);
      if (withArticle) {
        boolean plural = List.of(NT).contains(this);
        boolean feminine = List.of(NS, SK, BC).contains(this);
        String article = plural ? "les" : feminine ? "la" : "le";
        name = StringUtil.startWithVowel(name) ? "l'" + name : article + " " + name;
      }
    } else if (style == Style.GENITIVE) {
      name = "de " + getFrDisplayName(Style.WITH_ARTICLE);
      name = name.replace("de les", "des");
      name = name.replace("de le", "du");
    } else if (style == Style.LOCATIVE) {
      name = "à " + getFrDisplayName(Style.WITH_ARTICLE);
      name = name.replace("à les", "aux");
      name = name.replace("à le", "au");
      name = name.replace("à la", "en");
      name = name.replace("à l’", "en ");
    }

    return name;
  }
}
