package com.marcosavard.commons.ling.fr;

import java.util.Arrays;

public class PluralFinder {

  public String findPlural(String singular) {
    String plural = singular;
    PluralRule[] rules = new PluralRule[] { //
        new EndindAilRule(), //
        new EndindAlRule(), //
        new EndindAuRule(), //
        new EndindEuRule(), //
        new EndindOuRule(), //
        new GeneralRule() //
    };

    for (PluralRule rule : rules) {
      if (rule.applies(singular)) {
        plural = rule.findPlural(singular);
        break;
      }
    }

    return plural;
  }

  private static abstract class PluralRule {

    protected boolean applies(String singular) {
      return true;
    }

    protected abstract String findPlural(String singular);
  }

  private static class GeneralRule extends PluralRule {
    @Override
    protected String findPlural(String singular) {
      boolean endSX = singular.endsWith("s") || singular.endsWith("x");
      String plural = endSX ? singular : singular + "s";
      return plural;
    }
  }

  private static class EndindAilRule extends PluralRule {
    private static final String exceptions[] = new String[] { //
        "bail", "corail", "fermail", "gemmail", "soupirail", "travail", "télétravail", "vantail",
        "ventail", "émail"};

    @Override
    protected boolean applies(String singular) {
      return singular.endsWith("ail");
    }

    @Override
    protected String findPlural(String singular) {
      boolean exception = Arrays.asList(exceptions).contains(singular);
      int length = singular.length();
      String plural = exception ? singular.substring(0, length - 3) + "aux" : singular + "s";
      return plural;
    }
  }

  private static class EndindAlRule extends PluralRule {
    private static final String exceptions[] = new String[] { //
        "acétal", "amenokal", "aménokal", "ammonal", "autogoal", "aval", "bal", "barbital",
        "baribal", "butanal", "cal", "captal", "cantal", "caracal", "carnaval", "cérémonial",
        "chacal", "chloral", "choral", "citral", "contre-pal", "contrepal", "copal", "corral",
        "deal", "dispersal", "drop-goal", "emmental", "emmenthal", "eurosignal", "festival",
        "final", "floréal", "furfural", "futal", "futsal", "gal", "galgal", "gardénal", "gavial",
        "gayal", "germinal", "goal", "marshal", "mescal", "minerval", "mistral", "morfal",
        "méthanal", "narval", "natal", "kraal", "negro-spiritual", "nopal", "négrospiritual", "pal",
        "penthiobarbital", "penthotal", "phénobarbital", "pipéronal", "prairial", "propanal",
        "pyridoxal", "quetzal", "récital", "revival", "rial", "rital", "roseval", "régal",
        "rétinal", "rorqual", "serval", "salicional", "santal", "saroual", "sex-appeal",
        "sexappeal", "sial", "sisal", "sonal", "spiritual", "tagal", "tergal", "tincal", "trial",
        "éthanal"};

    @Override
    protected boolean applies(String singular) {
      return singular.endsWith("al");
    }

    @Override
    protected String findPlural(String singular) {
      boolean exception = Arrays.asList(exceptions).contains(singular);
      int length = singular.length();
      String plural = exception ? singular + "s" : singular.substring(0, length - 2) + "aux";
      return plural;
    }
  }

  private static class EndindAuRule extends PluralRule {
    private static final String exceptions[] = new String[] { //
        "berimbau", "karbau", "kérabau", "landau", "nilgau", "restau", "sarrau", "senau", "tau",
        "unau"};

    @Override
    protected boolean applies(String singular) {
      return singular.endsWith("au");
    }

    @Override
    protected String findPlural(String singular) {
      boolean exception = Arrays.asList(exceptions).contains(singular);
      String plural = exception ? singular + "s" : singular + "x";;
      return plural;
    }
  }

  private static class EndindEuRule extends PluralRule {
    private static final String exceptions[] = new String[] { //
        "bleu", "bas-bleu", "chleu", "démonte-pneu", "enfeu", "neuneu", "pneu", "richelieu",
        "schleu", "émeu"};

    @Override
    protected boolean applies(String singular) {
      return singular.endsWith("eu");
    }

    @Override
    protected String findPlural(String singular) {
      boolean exception = Arrays.asList(exceptions).contains(singular);
      String plural = exception ? singular + "s" : singular + "x";;
      return plural;
    }
  }

  private static class EndindOuRule extends PluralRule {
    private static final String exceptions[] = new String[] { //
        "bijou", "caillou", "chou", "coupe-chou", "genou", "hibou", "joujou", "pou",
        "pousse-caillou", "protège-genou", "ripou"};

    @Override
    protected boolean applies(String singular) {
      return singular.endsWith("ou");
    }

    @Override
    protected String findPlural(String singular) {
      boolean exception = Arrays.asList(exceptions).contains(singular);
      String plural = exception ? singular + "x" : singular + "s";;
      return plural;
    }
  }

}
