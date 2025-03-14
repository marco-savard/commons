package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.io.*;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.ling.Gender;
import com.marcosavard.commons.ling.Language;
import com.marcosavard.commons.math.RessourceEnum;
import com.marcosavard.commons.util.LocaleUtil;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public enum Country {
  ANDORRA("AD"),
  UNITED_ARAB_EMIRATES("AE"),
  AFGHANISTAN("AF"),
  ANTIGUA_DEPS("AG"),
  ANGUILLA("AI"),
  ALBANIA("AL"),
  ARMENIA("AM"),
  ANGOLA("AO"),
  ANTARCTICA("AQ"),
  ARGENTINA("AR"),
  AMERICAN_SAMOAS("AS"),
  AUSTRIA("AT"),
  AUSTRALIA("AU"),
  ARUBA("AW"),
  ALAND_ISLANDS("AX"),
  AZERBAIJAN("AZ"),
  BOSNIA_HERZEGOVINA("BA"),
  BARBADOS("BB"),
  BANGLADESH("BD"),
  BELGIUM("BE"),
  BURKINA("BF"),
  BULGARIA("BG"),
  BAHRAIN("BH"),
  BURUNDI("BI"),
  BENIN("BJ"),
  SAINT_BARTHELEMY("BL"),
  BERMUDA("BM"),
  BRUNEI("BN"),
  BOLIVIA("BO"),
  DUTCH_CARABEANS("BQ"),
  BRAZIL("BR"),
  BAHAMAS("BS"),
  BHUTAN("BT"),
  BOUVET_ISLAND("BV"),
  BOTSWANA("BW"),
  BELARUS("BY"),
  BELIZE("BZ"),
  CANADA("CA"),
  COCOS_ISLANDS("CC"),
  CONGO_KINSHASA("CD"),
  CENTRAL_AFRICA("CF"),
  CONGO_BRAZZAVILLE("CG"),
  SWITZERLAND("CH"),
  IVORY_COAST("CI"),
  COOK_ISLANDS("CK"),
  CHILE("CL"),
  CAMEROON("CM"),
  CHINA("CN"),
  COLOMBIA("CO"),
  COSTA_RICA("CR"),
  CUBA("CU"),
  CAPE_VERDE("CV"),
  CURACAO("CW"),
  CHRISTMAS_ISLAND("CX"),
  CYPRUS("CY"),
  CZECH_REPUBLIC("CZ"),
  GERMANY("DE"),
  DJIBOUTI("DJ"),
  DENMARK("DK"),
  DOMINICA("DM"),
  DOMINICAN_REPUBLIC("DO"),
  ALGERIA("DZ"),
  ECUADOR("EC"),
  ESTONIA("EE"),
  EGYPT("EG"),
  WESTERN_SAHARA("EH"),
  ERITREA("ER"),
  SPAIN("ES"),
  ETHIOPIA("ET"),
  FINLAND("FI"),
  FIJI("FJ"),
  FALKLAND_ISLANDS("FK"),
  MICRONESIA("FM"),
  FAROE_ISLANDS("FO"),
  FRANCE("FR"),
  GABON("GA"),
  UNITED_KINGDOM("GB"),
  GRENADA("GD"),
  GEORGIA("GE"),
  FRENCH_GUIANA("GF"),
  GUERNSEY("GG"),
  GHANA("GH"),
  GIBRALTAR("GI"),
  GREENLAND("GL"),
  GAMBIA("GM"),
  GUINEA("GN"),
  GUADELOUPE("GP"),
  EQUATORIAL_GUINEA("GQ"),
  GREECE("GR"),
  SOUTH_GEORGIA("GS"),
  GUATEMALA("GT"),
  GUAM("GU"),
  GUINEA_BISSAU("GW"),
  GUYANA("GY"),
  HONK_KONG("HK"),
  HEARD_ISLAND_AND_MCDONALD_ISLANDS("HM"),
  HONDURAS("HN"),
  CROATIA("HR"),
  HAITI("HT"),
  HUNGARY("HU"),
  INDONESIA("ID"),
  IRELAND("IE"),
  ISRAEL("IL"),
  ISLE_OF_MAN("IM"),
  INDIA("IN"),
  BRITISH_INDIAN_OCEAN_TERRITORY("IO"),
  IRAQ("IQ"),
  IRAN("IR"),
  ICELAND("IS"),
  ITALY("IT"),
  JERSEY("JE"),
  JAMAICA("JM"),
  JORDAN("JO"),
  JAPAN("JP"),
  KENYA("KE"),
  KYRGYZSTAN("KG"),
  CAMBODIA("KH"),
  KIRIBATI("KI"),
  COMOROS("KM"),
  SAINT_KITTS_AND_NEVIS("KN"),
  NORTH_KOREA("KP"),
  SOUTH_KOREA("KR"),
  KUWAIT("KW"),
  CAYMAN_ISLANDS("KY"),
  KAZAKHSTAN("KZ"),
  LAOS("LA"),
  LEBANON("LB"),
  SAINT_LUCIA("LC"),
  LIECHTENSTEIN("LI"),
  SRI_LANKA("LK"),
  LIBERIA("LR"),
  LESOTHO("LS"),
  LITHUANIA("LT"),
  LUXEMBOURG("LU"),
  LATVIA("LV"),
  LIBYA("LY"),
  MOROCCO("MA"),
  MONACO("MC"),
  MOLDOVA("MD"),
  MONTENEGRO("ME"),
  SAINT_MARTIN("MF"),
  MADAGASCAR("MG"),
  MARSHALL_ISLANDS("MH"),
  MACEDONIA("MK"),
  MALI("ML"),
  MYANMAR("MM"),
  MONGOLIA("MN"),
  MACAO("MO"),
  NORTHERN_MARIANA_ISLANDS("MP"),
  MARTINIQUE("MQ"),
  MAURITANIA("MR"),
  MONTSERRAT("MS"),
  MALTA("MT"),
  MAURITIUS("MU"),
  MALDIVES("MV"),
  MALAWI("MW"),
  MEXICO("MX"),
  MALAYSIA("MY"),
  MOZAMBIQUE("MZ"),
  NAMIBIA("NA"),
  NEW_CALEDONIA("NC"),
  NIGER("NE"),
  NORFOLK_ISLAND("NF"),
  NIGERIA("NG"),
  NICARAGUA("NI"),
  NETHERLANDS("NL"),
  NORWAY("NO"),
  NEPAL("NP"),
  NAURU("NR"),
  NIUE("NU"),
  NEW_ZEALAND("NZ"),
  OMAN("OM"),
  PANAMA("PA"),
  PERU("PE"),
  FRENCH_POLYNESIA("PF"),
  PAPUA_NEW_GUINEA("PG"),
  PHILIPPINES("PH"),
  PAKISTAN("PK"),
  POLAND("PL"),
  SAINT_PIERRE_AND_MIQUELON("PM"),
  PITCAIRN("PN"),
  PUERTO_RICO("PR"),
  PALESTINE("PS"),
  PORTUGAL("PT"),
  PALAU("PW"),
  PARAGUAY("PY"),
  QATAR("QA"),
  REUNION("RE"),
  ROMANIA("RO"),
  SERBIA("RS"),
  RUSSIA("RU"),
  RWANDA("RW"),
  SAUDI_ARABIA("SA"),
  SOLOMON_ISLANDS("SB"),
  SEYCHELLES("SC"),
  SUDAN("SD"),
  SWEDEN("SE"),
  SINGAPORE("SG"),
  SAINT_HELEN("SH"),
  SLOVENIA("SI"),
  SVALBARD_AND_SAN_MAYEN("SJ"),
  SLOVAKIA("SK"),
  SIERRA_LEONE("SL"),
  SAN_MARINO("SM"),
  SENEGAL("SN"),
  SOMALIA("SO"),
  SURINAME("SR"),
  SOUTH_SUDAN("SS"),
  SAO_TOME_PRINCIPE("ST"),
  EL_SALVADOR("SV"),
  SINT_MAARTEN("SX"),
  SYRIA("SY"),
  ESWATINI("SZ"),
  TURKS_AND_CAICOS_ISLANDS("TC"),
  CHAD("TD"),
  FRENCH_SOUTHERN_TERRITORIES("TF"),
  TOGO("TG"),
  THAILAND("TH"),
  TAJIKISTAN("TJ"),
  TOKELAU("TK"),
  EAST_TIMOR("TL"),
  TURKMENISTAN("TM"),
  TUNISIA("TN"),
  TONGA("TO"),
  TURKEY("TR"),
  TRINIDAD_TOBAGO("TT"),
  TUVALU("TV"),
  TAIWAN("TW"),
  TANZANIA("TZ"),
  UKRAINE("UA"),
  UGANDA("UG"),
  US_MINOR_OUTLYING_ISLANDS("UM"),
  USA("US"),
  URUGUAY("UY"),
  UZBEKISTAN("UZ"),
  VATICAN_CITY("VA"),
  SAINT_VINCENT_AND_GRENADINES("VC"),
  VENEZUELA("VE"),
  BRITISH_VIRGIN_ISLANDS("VG"),
  US_VIRGIN_ISLANDS("VI"),
  VIETNAM("VN"),
  VANUATU("VU"),
  WALLIS_AND_FUTUNA("WF"),
  SAMOA("WS"),
  YEMEN("YE"),
  MAYOTTE("YT"),
  SOUTH_AFRICA("ZA"),
  ZAMBIA("ZM"),
  ZIMBABWE("ZW"),
  SERBIA_AND_MONTENEGRO("SCG", 2006, Country.SERBIA, Country.MONTENEGRO),
  YUGOSLAVA("YUG", 2003, Country.SERBIA_AND_MONTENEGRO),
  ZAIRE("ZAR", 1997, Country.CONGO_KINSHASA),
  CZECHOSLOVAKIA("CSK", 1993, Country.CZECH_REPUBLIC, Country.SLOVAKIA),
  SOVIET_UNION(
      "SUN",
      1992,
      Country.RUSSIA,
      Country.UKRAINE,
      Country.BELARUS,
      Country.MOLDOVA,
      Country.LATVIA,
      Country.LITHUANIA,
      Country.ESTONIA,
      Country.GEORGIA,
      Country.ARMENIA,
      Country.AZERBAIJAN,
      Country.KAZAKHSTAN,
      Country.KYRGYZSTAN,
      Country.TAJIKISTAN,
      Country.TURKMENISTAN,
      Country.UZBEKISTAN),
  GERMAN_DEMOCRATIC_REPUBLIC("DDR", 1990, Country.GERMANY),
  BURMA("BUR", 1989, Country.MYANMAR),
  UPPER_VOLTA("HVO", 1984, Country.BURKINA),
  SOUTHERN_RHODESIA("RHO", 1980, Country.ZIMBABWE),
  DAHOMEY("DHY", 1977, Country.BENIN),
  VIETNAM_DEMOCRATIC_REPUBLIC("VDR", 1977, Country.VIETNAM);

    public enum Style {
    ALONE,
    WITH_ARTICLE,
    GENITIVE,
    LOCATIVE
  }

  private static CountryCapitalReader countryCapitalReader = new CountryCapitalReader();
  private static TimezoneByCountryReader timezoneByCountryReader = new TimezoneByCountryReader();
  private static NationalHolidaysReader nationalHolidaysReader = new NationalHolidaysReader();
  private static LanguageByCountryReader languageByCountryReader = new LanguageByCountryReader();
  private static DevisesNationalesReader devisesNationalesReader = new DevisesNationalesReader();

  private String code;
  private List<Locale> locales;
  private List<Character.UnicodeScript> scripts;
  private Currency currency;

  Country(String code) {
    this(code, -1, null);
  }

  Country(String code, int endYear, Country... successors) {
    this.code = code;
    this.locales = LocaleUtil.localesOf(code);
    this.scripts = findScripts(locales);
    this.currency = findCurrency(locales);
  }

  public static Country of(String code) {
    List<Country> countries = List.of(Country.values());
    Country country =
        countries.stream().filter(c -> code.equals(c.getCode())).findFirst().orElse(null);
    return country;
  }

  public Locale getLocale() {
    return LocaleUtil.forCountryTag(code);
  }

  @Override
  public String toString() {
    return getDisplayName();
  }

  public String getCode() {
    return code;
  }

  public String getDisplayName() {
    return getDisplayName(Locale.getDefault());
  }

  public String getDisplayName(Locale display) {
    return getDisplayName(display, TextStyle.NARROW);
  }

  public String getDisplayName(Locale display, TextStyle textStyle) {
    return countryCapitalReader.getDisplayCountry(code, display, textStyle);
  }

  public List<Capital> getCapitalCities() {
    return countryCapitalReader.getCapitalCities(code);
  }

  public List<TimeZone> getTimeZones() {
    return timezoneByCountryReader.readTimezones(code);
  }

  public List<String> getNationalMottos() {
    return devisesNationalesReader.getMottos(getLocale());
  }

  public LocalDate getNationalHolidayDate(int year) {
    NationalHolidaysReader.NationalHoliday holiday = nationalHolidaysReader.read(code);
    if (holiday != null) {
      Month month = holiday.getMonth();
      int dayOfMonth = holiday.getDayOfMonth();
      return LocalDate.of(year, month, dayOfMonth);
    } else {
      return null;
    }

  }

  public Locale[] getOfficialLanguages() {
    Locale[][] allLanguages = languageByCountryReader.read(code);
    return allLanguages[0];
  }

  public Locale[] getRegionalLanguages() {
    Locale[][] allLanguages = languageByCountryReader.read(code);
    return allLanguages[1];
  }

  public Currency getCurrency() {
    return currency;
  }











  /// //////////////////
  /// OLD



  public String toTitleCase(String s) {
    String title = Character.toTitleCase(s.charAt(0)) + s.toLowerCase().substring(1);
    return title;
  }

  public List<Locale> getLocales() {
    return locales;
  }

  public List<Character.UnicodeScript> getScripts() {
    return scripts;
  }

  public List<String> getScriptNames() {
    List<String> scriptNames = new ArrayList<>();

    for (Character.UnicodeScript script : scripts) {
      String name = script.name();
      if (!scriptNames.contains(name)) {
        scriptNames.add(name);
      }
    }

    return scriptNames;
  }



  public List<String> getLanguages() {
    List<String> languages = new ArrayList<>();

    for (Locale locale : locales) {
      String language = locale.getLanguage();
      if (!languages.contains(language)) {
        languages.add(language);
      }
    }

    return languages;
  }

  private static Currency findCurrency(List<Locale> locales) {
    Currency foundCurrency = Currency.getInstance("USD"), currency = null;

    for (Locale locale : locales) {
      try {
        currency = Currency.getInstance(locale);
      } catch (IllegalArgumentException ex) {
        currency = null;
      }

      if (currency != null) {
        foundCurrency = currency;
        break;
      }
    }

    return foundCurrency;
  }

  private static List<Character.UnicodeScript> findScripts(List<Locale> locales) {
    List<Character.UnicodeScript> scripts = new ArrayList<>();

    for (Locale locale : locales) {
      String lang = locale.getLanguage();
      Character.UnicodeScript script = getScript(locale);

      if (!scripts.contains(script)) {
        scripts.add(script);
      }
    }

    if (scripts.isEmpty()) {
      scripts.add(Character.UnicodeScript.LATIN);
    }

    return scripts;
  }

  private static Character.UnicodeScript getScript(Locale locale) {
    Character.UnicodeScript script = null;
    String scriptName = locale.getScript();
    String lang = locale.getLanguage();

    if (scriptName.equals("Adlm")) {
      script = Character.UnicodeScript.ADLAM;
    } else if (scriptName.equals("Arab")) {
      script = Character.UnicodeScript.ARABIC;
    } else if (scriptName.equals("Armn")) {
      script = Character.UnicodeScript.ARMENIAN;
    } else if (scriptName.equals("Beng")) {
      script = Character.UnicodeScript.BENGALI;
    } else if (scriptName.equals("Cakm")) {
      script = Character.UnicodeScript.CHAKMA;
    } else if (scriptName.equals("Cher")) {
      script = Character.UnicodeScript.CHEROKEE;
    } else if (scriptName.equals("Cyrl")) {
      script = Character.UnicodeScript.CYRILLIC;
    } else if (scriptName.equals("Deva")) {
      script = Character.UnicodeScript.DEVANAGARI;
    } else if (scriptName.equals("Ethi")) {
      script = Character.UnicodeScript.ETHIOPIC;
    } else if (scriptName.equals("Geor")) {
      script = Character.UnicodeScript.GEORGIAN;
    } else if (scriptName.equals("Grek")) {
      script = Character.UnicodeScript.GREEK;
    } else if (scriptName.equals("Gujr")) {
      script = Character.UnicodeScript.GUJARATI;
    } else if (scriptName.equals("Guru")) {
      script = Character.UnicodeScript.GURMUKHI;
    } else if (scriptName.equals("Hans")) {
      script = Character.UnicodeScript.HAN;
    } else if (scriptName.equals("Hant")) {
      script = Character.UnicodeScript.HAN;
    } else if (scriptName.equals("Hebr")) {
      script = Character.UnicodeScript.HEBREW;
    } else if (scriptName.equals("Jpan")) {
      script = Character.UnicodeScript.HIRAGANA;
    } else if (scriptName.equals("Khmr")) {
      script = Character.UnicodeScript.KHMER;
    } else if (scriptName.equals("Knda")) {
      script = Character.UnicodeScript.KANNADA;
    } else if (scriptName.equals("Kore")) {
      script = Character.UnicodeScript.HANGUL; // ?
    } else if (scriptName.equals("Laoo")) {
      script = Character.UnicodeScript.LAO;
    } else if (scriptName.equals("Latn")) {
      script = Character.UnicodeScript.LATIN;
    } else if (scriptName.equals("Mlym")) {
      script = Character.UnicodeScript.MALAYALAM;
    } else if (scriptName.equals("Mymr")) {
      script = Character.UnicodeScript.MYANMAR;
    } else if (scriptName.equals("Olck")) {
      script = Character.UnicodeScript.OL_CHIKI;
    } else if (scriptName.equals("Orya")) {
      script = Character.UnicodeScript.ORIYA;
    } else if (scriptName.equals("Sinh")) {
      script = Character.UnicodeScript.SINHALA;
    } else if (scriptName.equals("Taml")) {
      script = Character.UnicodeScript.TAMIL;
    } else if (scriptName.equals("Telu")) {
      script = Character.UnicodeScript.TELUGU;
    } else if (scriptName.equals("Tfng")) {
      script = Character.UnicodeScript.TIFINAGH;
    } else if (scriptName.equals("Thai")) {
      script = Character.UnicodeScript.THAI;
    } else if (scriptName.equals("Tibt")) {
      script = Character.UnicodeScript.TIBETAN;
    } else if (scriptName.equals("Vaii")) {
      script = Character.UnicodeScript.VAI;
    } else if (scriptName.equals("Yiii")) {
      script = Character.UnicodeScript.YI;
    } else if (scriptName.isEmpty() && "ko".equals(lang)) {
      script = Character.UnicodeScript.HANGUL; // /?
    } else if (scriptName.isEmpty() && "ko".equals(lang)) {
      script = Character.UnicodeScript.ARABIC;
    } else if (scriptName.isEmpty()) {
      script = Character.UnicodeScript.LATIN;
    }

    if (script == null) {
      script = Character.UnicodeScript.LATIN;
    }

    return script;
  }

  public CountryName getCountryName(Locale display) {
    String name = getDisplayName(display);
    return CountryName.of(code, name, display);
  }

  public List<Locale> getLanguageLocales() {
    List<String> languages = getLanguages();
    List<Locale> locales = new ArrayList<>();

    for (String language : languages) {
      Locale locale = Locale.forLanguageTag(language);
      locales.add(locale);
    }

    return locales;
  }

  private static final List<String> ISLANDS =
      List.of( //
          "AG", "AI", "AW", "BL", "CU", "CW", "CY", //
          "FJ", "GD", "GG", "GU", "JE", "LC", "MF", "MT", "NR", "NU", "OM", "PM", "PR", "PW", //
          "RE", "SG", "SH", "SJ", "ST", "SX", "TT", "TW", "VC", "VU", "WF", "YT");

  private static final List<String> LOCALITIES = List.of("AD", "DJ", "GI", "MC", "OM", "PW", "SM");

  public boolean isIsland() {
    boolean island = ISLANDS.contains(this.code);
    return island;
  }

  public boolean isLocality() {
    boolean locality = LOCALITIES.contains(this.code);
    return locality;
  }

  public static class CountryName {
    private String code, countryName;
    private Locale display;

    public static CountryName of(String code, String name, Locale display) {
      CountryName countryName = new CountryName(code, name, display);
      return countryName;
    }

    private CountryName(String code, String countryName, Locale display) {
      this.code = code;
      this.countryName = countryName;
      this.display = display;
    }

    public char getGrammaticalNumber() {
      boolean plural = countryName.endsWith("s");
      plural = plural || countryName.toLowerCase().contains("îles");
      plural = List.of("CX", "HN", "LA").contains(code) ? false : plural;
      char number = plural ? 'P' : 'S';
      return number;
    }

    public char getGrammaticalGender() {
      char gender = 'N';

      if (display.getLanguage().equals("fr")) {
        gender = countryName.endsWith("e") ? 'F' : 'M';
        gender = List.of("BZ", "CG", "KH", "MX", "MZ", "ZW").contains(code) ? 'M' : gender;
        gender = List.of("KP", "KR", "MK").contains(code) ? 'F' : gender;
      }

      return gender;
    }
  }

  private abstract static class Article {
    private static Map<String, Article> articles = null;

    public static Article of(Locale locale) {
      String lang = locale.getLanguage();
      Map<String, Article> articles = getArticles();
      Article article = articles.containsKey(lang) ? articles.get(lang) : articles.get("en");
      return article;
    }

    private static Map<String, Article> getArticles() {
      if (articles == null) {
        articles = new HashMap<>();
        articles.put("fr", new FrArticle());
        articles.put("en", new EnArticle());
      }

      return articles;
    }

    public abstract String getArticle(char number, char gender, String preposition);
  }

  private static class FrArticle extends Article {
    public String getArticle(char number, char gender, String preposition) {
      String article = (gender == 'F') ? "la" : (gender == 'M') ? "le" : "";
      article = (number == 'P') ? "les" : article;
      article = article.isEmpty() ? preposition : preposition + " " + article;
      return article;
    }
  }

  private static class EnArticle extends Article {
    public String getArticle(char number, char gender, String preposition) {
      String article = "";
      article = article.isEmpty() ? preposition : preposition + " " + article;
      return article;
    }
  }

  public static record Capital(String name, GeoLocation location) {}

}
