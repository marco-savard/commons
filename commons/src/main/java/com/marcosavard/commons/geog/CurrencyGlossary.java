package com.marcosavard.commons.geog;

import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.ling.Language;
import com.marcosavard.commons.util.collection.FrequencyMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrencyGlossary {
  private static Map<Locale, CurrencyGlossary> glossaries = new HashMap<>();
  private static final List<String> DOLLARS = List.of("AUD", "CAD", "NZD", "USD");
  private static final List<String> FRANCS = List.of("BEF", "LUF"); // not CHF"

  private static final List<String> FRANKEN = List.of("CHF", "CHW");

  private static final List<String> FLORINS = List.of("SRG", "NLG");

  private static final List<String> KRONES = List.of("DKK", "NOK"); // not "SEK", isl

  private static final List<String> MARKS = List.of("DEM", "BAM");
  private static final List<String> POUNDS = List.of("GBP", "IEP", "GIP"); // not: "FKP" cyp, tur
  private static final List<String> PESOS = List.of("ARS", "CLP", "COP", "MXN", "GWP");
  private static final List<String> RUBLES = List.of("RUB", "BYN"); // not "SEK", isl

  private Locale locale;

  Map<String, AdjectiveFinder> finderByCountry = null;

  public static CurrencyGlossary of(Locale locale) {
    CurrencyGlossary glossary = glossaries.get(locale);

    if (glossary == null) {
      glossary = new CurrencyGlossary(locale);
      glossaries.put(locale, glossary);
    }

    return glossary;
  }

  private CurrencyGlossary(Locale locale) {
    this.locale = locale;
  }

  public static String getNewWord(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String[] args = new String[4];
    List<String> allWords = new ArrayList();

    args[0] = findCurrency(currencies, "BYB").getDisplayName(locale).toLowerCase();
    args[1] = findCurrency(currencies, "ILS").getDisplayName(locale).toLowerCase();
    args[2] = findCurrency(currencies, "TMT").getDisplayName(locale).toLowerCase();
    args[3] = findCurrency(currencies, "TWD").getDisplayName(locale).toLowerCase();

    for (String arg : args) {
      List words = List.of(arg.split("\\s+"));
      allWords.addAll(words);
    }

    FrequencyMap<String> map = FrequencyMap.of(allWords);
    String mostFrequent = map.getMostFrequent();
    return mostFrequent;
  }

  public String getAdjective(String country, Locale locale) {
    if (finderByCountry == null) {
      finderByCountry = createFinderByCountry();
    }

    AdjectiveFinder finder = finderByCountry.get(country);
    String word = (finder == null) ? null : finder.getAdjective(locale);
    word = toMasculine(word, locale);
    return word;
  }

  private String toMasculine(String word, Locale locale) {
    String masculine = word;

    if (locale.getLanguage().equals("fr")) {
      masculine = toMasculineFrench(word);
    }

    return masculine;
  }

  private String toMasculineFrench(String word) {
    String masculine = word;

    if ((word != null) && word.endsWith("cque")) {
      masculine = word.substring(0, word.length() - 3);
    } else if ((word != null) && word.endsWith("rque")) {
      masculine = word.substring(0, word.length() - 3) + "c";
    } else if ((word != null) && word.endsWith("nne")) {
      masculine = word.substring(0, word.length() - 2);
    } else if ((word != null) && word.endsWith("aise")) {
      masculine = word.substring(0, word.length() - 1);
    } else if ((word != null) && word.endsWith("oise")) {
      masculine = word.substring(0, word.length() - 1);
    } else if ((word != null) && word.endsWith("ane")) {
      masculine = word.substring(0, word.length() - 1);
    } else if ((word != null) && word.endsWith("ole")) {
      masculine = word.substring(0, word.length() - 1);
    }

    return masculine;
  }

  private Map<String, AdjectiveFinder> createFinderByCountry() {
    Map<String, AdjectiveFinder> finderByCountry = new HashMap<>();
    finderByCountry.put("AD", new AndorranFinder());
    finderByCountry.put("AF", new AfghaniFinder());
    finderByCountry.put("AL", new AlbanianFinder());
    finderByCountry.put("AM", new ArmenianFinder());
    finderByCountry.put("AO", new AngolanFinder());
    finderByCountry.put("AR", new ArgentineFinder());
    finderByCountry.put("AT", new AustrianFinder());
    finderByCountry.put("AU", new AustralianFinder());
    finderByCountry.put("AW", new ArubianFinder());
    finderByCountry.put("AZ", new AzeriFinder());

    finderByCountry.put("BB", new BarbadianFinder());
    finderByCountry.put("BD", new BangladeshiFinder());
    finderByCountry.put("BE", new BelgianFinder());
    finderByCountry.put("BG", new BulgarianFinder());
    finderByCountry.put("BH", new BahrainiFinder());
    finderByCountry.put("BI", new BurundianFinder());
    finderByCountry.put("BM", new BermudianFinder());

    finderByCountry.put("BO", new BolivianFinder());
    finderByCountry.put("BR", new BrazilianFinder());
    finderByCountry.put("BS", new BahamianFinder());
    finderByCountry.put("BT", new BhutaneseFinder());
    finderByCountry.put("BW", new BotswananFinder());
    finderByCountry.put("BY", new BielorussianFinder());
    finderByCountry.put("BZ", new BelizeanFinder());

    finderByCountry.put("CA", new CanadianFinder());
    finderByCountry.put("CD", new CongoleseFinder());
    finderByCountry.put("CH", new SwissFinder());
    finderByCountry.put("CL", new ChilianFinder());
    finderByCountry.put("CN", new ChineseFinder());
    finderByCountry.put("CO", new ColombianFinder());
    finderByCountry.put("CR", new CostaRicanFinder());
    finderByCountry.put("CU", new CubanFinder());
    finderByCountry.put("CV", new CapeVerdeanFinder());
    finderByCountry.put("CY", new CypriotFinder());
    finderByCountry.put("CZ", new CzechFinder());

    finderByCountry.put("DE", new GermanFinder());
    finderByCountry.put("DJ", new DjiboutianFinder());
    finderByCountry.put("DK", new DanishFinder());
    finderByCountry.put("DM", new DominicanFinder());
    finderByCountry.put("DZ", new AlgerianFinder());

    finderByCountry.put("EE", new EstonianFinder());
    finderByCountry.put("EG", new EgyptianFinder());
    finderByCountry.put("ER", new EritreanFinder());
    finderByCountry.put("ES", new SpanishFinder());
    finderByCountry.put("ET", new EthiopianFinder());

    finderByCountry.put("FI", new FinnishFinder());
    finderByCountry.put("FJ", new FijianFinder());
    finderByCountry.put("FR", new FrenchFinder());

    finderByCountry.put("GB", new BritishFinder());
    finderByCountry.put("GE", new GeorgianFinder());
    finderByCountry.put("GH", new GhanaianFinder());
    finderByCountry.put("GM", new GambianFinder());
    finderByCountry.put("GN", new GuineanFinder());
    finderByCountry.put("GR", new GreekFinder());
    finderByCountry.put("GT", new GuatemalanFinder());

    finderByCountry.put("HN", new HondurianFinder());
    finderByCountry.put("HR", new CroatianFinder());
    finderByCountry.put("HT", new HaitianFinder());
    finderByCountry.put("HU", new HungarianFinder());

    finderByCountry.put("ID", new IndonesianFinder());
    finderByCountry.put("IE", new IrishFinder());
    finderByCountry.put("IL", new IsraeliFinder());
    finderByCountry.put("IN", new IndianFinder());
    finderByCountry.put("IQ", new IraqiFinder());
    finderByCountry.put("IR", new IranianFinder());
    finderByCountry.put("IS", new IcelandicFinder());
    finderByCountry.put("IT", new ItalianFinder());

    finderByCountry.put("JM", new JamaicanFinder());
    finderByCountry.put("JO", new JordanianFinder());
    finderByCountry.put("JP", new JaponeseFinder());

    finderByCountry.put("KE", new KenyanFinder());
    finderByCountry.put("KG", new KyrgystaniFinder());
    finderByCountry.put("KH", new CambodianFinder());
    finderByCountry.put("KP", new NorthKoreanFinder());
    finderByCountry.put("KR", new SouthKoreanFinder());
    finderByCountry.put("KW", new KuwaitiFinder());
    finderByCountry.put("KZ", new KazakhstaniFinder());

    finderByCountry.put("LA", new LaotianFinder());
    finderByCountry.put("LB", new LibaneseFinder());
    finderByCountry.put("LK", new SriLankanFinder());
    finderByCountry.put("LR", new LiberianFinder());
    finderByCountry.put("LS", new LesothanFinder());
    finderByCountry.put("LT", new LithuanianFinder());
    finderByCountry.put("LU", new LuxembourgianFinder());
    finderByCountry.put("LV", new LatvianFinder());
    finderByCountry.put("LY", new LibyanFinder());

    finderByCountry.put("MA", new MoroccanFinder());
    finderByCountry.put("MD", new MoldovanFinder());
    finderByCountry.put("MG", new MalagasyFinder());
    finderByCountry.put("MK", new MacedonianFinder());
    finderByCountry.put("MM", new MyanmarFinder());
    finderByCountry.put("MN", new MongolianFinder());
    finderByCountry.put("MO", new MacaneseFinder());
    finderByCountry.put("MR", new MauritanianFinder());
    finderByCountry.put("MT", new MalteseFinder());
    finderByCountry.put("MW", new MalawianFinder());
    finderByCountry.put("MX", new MexicanFinder());
    finderByCountry.put("MY", new MalaisianFinder());

    finderByCountry.put("NA", new NamibianFinder());
    finderByCountry.put("NG", new NigerianFinder());
    finderByCountry.put("NI", new NicaraguanFinder());
    finderByCountry.put("NL", new DutchFinder());
    finderByCountry.put("NO", new NorvegianFinder());
    finderByCountry.put("NP", new NepaleseFinder());
    finderByCountry.put("NZ", new NewZelanderFinder());

    finderByCountry.put("OM", new OmaniFinder());

    finderByCountry.put("PA", new PanamanianFinder());
    finderByCountry.put("PE", new PeruvianFinder());
    finderByCountry.put("PH", new PhilipinoFinder());
    finderByCountry.put("PK", new PakistaniFinder());
    finderByCountry.put("PL", new PolishFinder());
    finderByCountry.put("PT", new PortugueseFinder());
    finderByCountry.put("PY", new ParaguayanFinder());

    finderByCountry.put("RO", new RomanianFinder());
    finderByCountry.put("RS", new SerbianFinder());
    finderByCountry.put("RU", new RussianFinder());
    finderByCountry.put("RW", new RwandanFinder());

    finderByCountry.put("SA", new SaudiFinder());
    finderByCountry.put("SE", new SwedishFinder());
    finderByCountry.put("SI", new SloveneFinder());
    finderByCountry.put("SK", new SlovakFinder());
    finderByCountry.put("SO", new SomalianFinder());
    finderByCountry.put("SR", new SurinameseFinder());
    finderByCountry.put("SY", new SyrianFinder());
    finderByCountry.put("SZ", new SwaziFinder());

    finderByCountry.put("TH", new ThaiFinder());
    finderByCountry.put("TJ", new Tajikistani());
    finderByCountry.put("TM", new TurkemnistaniFinder());
    finderByCountry.put("TN", new TunisianFinder());
    finderByCountry.put("TO", new TonganFinder());
    finderByCountry.put("TR", new TurkishFinder());
    finderByCountry.put("TW", new TawaineseFinder());
    finderByCountry.put("TZ", new TanzanianFinder());

    finderByCountry.put("UK", new UkranianFinder());
    finderByCountry.put("US", new AmericanFinder());
    finderByCountry.put("UY", new UruguayanFinder());
    finderByCountry.put("UZ", new UzbekiFinder());

    finderByCountry.put("VE", new VenezualianFinder());
    finderByCountry.put("VN", new VietnameseFinder());
    finderByCountry.put("VU", new VanuatuanFinder());

    finderByCountry.put("YE", new YemeniFinder());

    finderByCountry.put("ZA", new SouthAfricanFinder());
    finderByCountry.put("ZM", new ZambianFinder());
    finderByCountry.put("ZW", new ZimbabweanFinder());

    return finderByCountry;
  }

  private static List<Currency> getAvailableCurrencies() {
    List<Currency> currencies =
        Currency.getAvailableCurrencies().stream()
            .sorted(Comparator.comparing(Currency::getCurrencyCode))
            .collect(Collectors.toList());
    return currencies;
  }

  private static class AndorranFinder extends AdjectiveFinder {
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "ADP").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("peseta", "").trim();
      return result;
    }
  }

  private static class AfghaniFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "AFN").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("afghani", "").trim();
      result = result.replaceFirst("afgani", "").trim();
      result = result.replaceFirst("afgane", "").trim();
      result = WordUtil.removeShortWords(result, 2);

      if (oneOf(locale, "it")) {
        result = getAdjective(Language.SPANISH.toLocale());
      }

      return result;
    }
  }

  private static class AlbanianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "ALL").getDisplayName(locale).toLowerCase();
      String result = nlg.replaceFirst("lek", "").trim();
      result = WordUtil.removeShortWords(result, 2);

      if (oneOf(locale, "es", "ro")) {
        result = Locale.forLanguageTag("sq").getDisplayLanguage(locale);
      }

      return result;
    }
  }

  private static class ArmenianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "AMD").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("dram", "").trim();
      result = WordUtil.removeShortWords(result, 2);

      if (oneOf(locale, "es")) {
        result = Locale.forLanguageTag("hy").getDisplayLanguage(locale);
      }

      return result;
    }
  }

  private static class AngolanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "AOA").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "kwanza", "").trim();
      result = result.replace('-', ' ');
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.PORTUGUESE.toLocale());
      }

      return result;
    }
  }

  private static class ArgentineFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "ARS").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "peso", "").trim();
      return result;
    }
  }

  private static class AustrianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "ATS").getDisplayName(locale).toLowerCase();
      String pesata = findSchilling(locale);
      String remaining = replaceFirstIgnoreAccents(esp, pesata, "").trim();
      return remaining;
    }
  }

  private static class AustralianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String aud = findCurrency(currencies, "AUD").getDisplayName(locale).toLowerCase();
      String cad = findCurrency(currencies, "CAD").getDisplayName(locale).toLowerCase();
      String remaining = extractDelta(aud, cad);
      return remaining;
    }
  }

  private static class ArubianFinder extends AdjectiveFinder {

    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "AWG").getDisplayName(locale).toLowerCase();
      String florin = findFlorin(locale);
      result = replaceFirstIgnoreAccents(result, florin, "").trim();
      return result;
    }
  }

  private static class AzeriFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "AZN").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("manat", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class BarbadianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String aud = findCurrency(currencies, "BBD").getDisplayName(locale).toLowerCase();
      String cad = findCurrency(currencies, "AUD").getDisplayName(locale).toLowerCase();
      String remaining = extractDelta(aud, cad);
      return remaining;
    }
  }

  private static class BangladeshiFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "BDT").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("taka", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = Locale.forLanguageTag("bn").getDisplayLanguage(locale);
      }

      return result;
    }
  }

  private static class BelgianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String aud = findCurrency(currencies, "BEF").getDisplayName(locale).toLowerCase();
      String cad = findCurrency(currencies, "LUF").getDisplayName(locale).toLowerCase();
      String remaining = extractDelta(aud, cad);
      return remaining;
    }
  }

  private static class BulgarianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "BGN").getDisplayName(locale).toLowerCase();
      String result = nlg.replaceFirst("lev", "").trim();
      result = result.replaceFirst("lew", "").trim();
      result = WordUtil.removeShortWords(result, 2);

      if (oneOf(locale, "ro")) {
        result = Locale.forLanguageTag("bg").getDisplayLanguage(locale);
      }

      return result;
    }
  }

  private static class BahrainiFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "BHD").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "dinar", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class BurundianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "BIF").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "fran", "").trim();
      result = result.replace('-', ' ');
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class BermudianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String aud = findCurrency(currencies, "BMD").getDisplayName(locale).toLowerCase();
      String cad = findCurrency(currencies, "AUD").getDisplayName(locale).toLowerCase();
      String remaining = extractDelta(aud, cad);
      return remaining;
    }
  }

  private static class BolivianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      String language = locale.getLanguage();
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "BOB").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "boliviano", "").trim();
      result = WordUtil.removeShortWords(result, 2);

      if (oneOf(locale, "es", "it")) {
        result = result.trim();
      } else if (oneOf(locale, "ro")) {
        result = getAdjective(Locale.ENGLISH);
      }

      return result;
    }
  }

  private static class BrazilianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "BRL").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "real", "").trim();

      if (oneOf(locale, "ro")) {
        result = getAdjective(Locale.ENGLISH);
      }

      return result;
    }
  }

  private static class BahamianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String aud = findCurrency(currencies, "BSD").getDisplayName(locale).toLowerCase();
      String cad = findCurrency(currencies, "AUD").getDisplayName(locale).toLowerCase();
      String remaining = extractDelta(aud, cad);
      return remaining;
    }
  }

  private static class BhutaneseFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "BTN").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "ngultrum", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.PORTUGUESE.toLocale()); // butanes?
      }
      return result;
    }
  }

  private static class BotswananFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "BWP").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "pula", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.PORTUGUESE.toLocale());
      }

      return result;
    }
  }

  private static class BielorussianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "BYN").getDisplayName(locale).toLowerCase();
      String result = nlg.replaceFirst(findRubles(locale), "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class BelizeanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String aud = findCurrency(currencies, "BZD").getDisplayName(locale).toLowerCase();
      String cad = findCurrency(currencies, "AUD").getDisplayName(locale).toLowerCase();
      String remaining = extractDelta(aud, cad);
      return remaining;
    }
  }

  private static class CanadianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String aud = findCurrency(currencies, "CAD").getDisplayName(locale).toLowerCase();
      String cad = findCurrency(currencies, "AUD").getDisplayName(locale).toLowerCase();
      String remaining = extractDelta(aud, cad);
      return remaining;
    }
  }

  private static class CongoleseFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "CDF").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "fran", "").trim();
      result = result.replace('-', ' ');
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class SwissFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String chf = findCurrency(currencies, "CHF").getDisplayName(locale).toLowerCase();
      String franc = locale.getLanguage().equals("de") ? findFranken(locale) : findFranc(locale);
      String remaining = chf.replaceFirst(franc, "").trim();
      return remaining;
    }
  }

  private static class ChilianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "CLP").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "peso", "").trim();
      return result;
    }
  }

  private static class ChineseFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String str = findCurrency(currencies, "CNY").getDisplayName(locale).toLowerCase();
      str = str.replaceFirst("yuan", "").trim();
      str = str.replaceFirst("renminbi", "").trim();
      str = WordUtil.removeShortWords(str, 2);

      if (oneOf(locale, "es", "de")) {
        str = Locale.forLanguageTag("zh").getDisplayLanguage(locale);
      }

      return str;
    }
  }

  private static class ColombianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "COP").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "peso", "").trim();
      return result;
    }
  }

  private static class CostaRicanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "CRC").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "colon", "").trim();
      return result;
    }
  }

  private static class CubanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "CUP").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "peso", "").trim();
      return result;
    }
  }

  private static class CapeVerdeanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "CVE").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "escudo", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class CypriotFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "CYP").getDisplayName(locale).toLowerCase();
      String pound = findPound(locale);
      String remaining = replaceFirstIgnoreAccents(esp, pound, "").trim();
      return remaining;
    }
  }

  private static class CzechFinder extends AdjectiveFinder {

    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "CZK").getDisplayName(locale).toLowerCase();
      String florin = findKrones(locale);
      String remaining = nlg.replaceFirst(florin, "").trim();
      remaining = remaining.replaceFirst("koruna", "").trim();
      return remaining;
    }
  }

  private static class GermanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String dem = findCurrency(currencies, "DEM").getDisplayName(locale).toLowerCase();
      String mark = findMark(locale);
      String remaining = dem.replaceFirst(mark, "").trim();
      return remaining;
    }
  }

  private static class DjiboutianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "DJF").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "fran", "").trim();
      result = result.replace('-', ' ');
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class DanishFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "DKK").getDisplayName(locale).toLowerCase();
      String florin = findKrones(locale);
      String remaining = nlg.replaceFirst(florin, "").trim();
      return remaining;
    }
  }

  private static class DominicanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "DOP").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "peso", "").trim();
      return result;
    }
  }

  private static class AlgerianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "DZD").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "dinar", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class EstonianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "EEK").getDisplayName(locale).toLowerCase();
      String florin = findKrones(locale);
      String remaining = nlg.replaceFirst(florin, "").trim();
      remaining = remaining.replaceFirst("kroon", "").trim();
      return remaining;
    }
  }

  private static class EgyptianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "EGP").getDisplayName(locale).toLowerCase();
      String currency = findPound(locale);
      result = replaceFirstIgnoreAccents(result, currency, "").trim();
      return result;
    }
  }

  private static class EritreanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "ERN").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "nafka", "").trim();
      result = replaceFirstIgnoreAccents(result, "nakfa", "").trim();

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.ITALIAN.toLocale());
      }
      return result;
    }
  }

  private static class SpanishFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "ESP").getDisplayName(locale).toLowerCase();
      String remaining = replaceFirstIgnoreAccents(esp, "peseta", "").trim();
      return remaining;
    }
  }

  private static class EthiopianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "ETB").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "birr", "").trim();
      result = result.replace('-', ' ');
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.ITALIAN.toLocale());
      }

      return result;
    }
  }

  private static class FinnishFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      String language = locale.getLanguage();
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "FIM").getDisplayName(locale).toLowerCase();
      String mark;

      if (List.of("en", "it", "nl").contains(language)) {
        mark = "markka";
      } else if (language.equals("pt")) {
        mark = "marca";
      } else {
        mark = findMark(locale);
      }
      String remaining = nlg.replaceFirst(mark, "").trim();
      return remaining;
    }
  }

  private static class FijianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "FJD").getDisplayName(locale).toLowerCase();
      String dollar = findDollar(locale);
      result = replaceFirstIgnoreAccents(result, dollar, "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class FrenchFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String aud = findCurrency(currencies, "FRF").getDisplayName(locale).toLowerCase();
      String cad = findCurrency(currencies, "MGF").getDisplayName(locale).toLowerCase();
      String remaining = extractDelta(aud, cad);
      return remaining;
    }
  }

  private static class BritishFinder extends AdjectiveFinder {
    private CountryGlossary countryGlossary = new CountryGlossary();

    @Override
    public String getAdjective(Locale locale) {
      return countryGlossary.getBritishWord(locale);
    }
  }

  private static class GeorgianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      String language = locale.getLanguage();
      String result;

      if (List.of("es").contains(language)) {
        result = Locale.forLanguageTag("ka").getDisplayLanguage(locale);
      } else {
        List<Currency> currencies = getAvailableCurrencies();
        result = findCurrency(currencies, "GEL").getDisplayName(locale).toLowerCase();
        result = result.replaceFirst("lari", "").trim();
        result = WordUtil.removeShortWords(result, 2);
      }

      return result;
    }
  }

  private static class LesothanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "LSL").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "loti", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "de")) {
        result = getAdjective(Language.DUTCH.toLocale()); // Ganes?
      } else if (oneOf(locale, "es")) {
        result = getAdjective(Language.PORTUGUESE.toLocale()); // Ganes?
      }

      return result;
    }
  }

  private static class GhanaianFinder extends AdjectiveFinder {

    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "GHS").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "cedi", "").trim();
      return result;
    }
  }

  private static class GambianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "GMD").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "dalasi", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.PORTUGUESE.toLocale());
      }

      return result;
    }
  }

  private static class GuineanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "GNF").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "fran", "").trim();
      result = result.replace('-', ' ');
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class GreekFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "GRD").getDisplayName(locale).toLowerCase();
      String result = nlg.replaceFirst("drachm", "").trim();
      result = result.replaceFirst("dracm", "").trim();
      result = result.replaceFirst("drahm", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class GuatemalanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "GTQ").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "quetzal", "").trim();
      return result;
    }
  }

  private static class HaitianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "HTG").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "gourde", "").trim();
      return result;
    }
  }

  private static class HondurianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "HNL").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "lempira", "").trim();
      return result;
    }
  }

  private static class CroatianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "HRK").getDisplayName(locale).toLowerCase();
      String result = nlg.replaceFirst("kuna", "").trim();

      if (oneOf(locale, "es", "ro")) {
        result = Locale.forLanguageTag("hr").getDisplayLanguage(locale);
      }

      return result;
    }
  }

  private static class HungarianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String hungarian = findCurrency(currencies, "HUF").getDisplayName(locale).toLowerCase();
      for (String replaced : List.of("forint", "fiorino", "florim")) {
        hungarian = hungarian.replaceFirst(replaced, "").trim();
      }

      if (oneOf(locale, "ro")) {
        hungarian = Locale.forLanguageTag("hu").getDisplayLanguage(locale);
      }

      hungarian = WordUtil.removeShortWords(hungarian, 2);
      return hungarian;
    }
  }

  private static class IndonesianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "IDR").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "roupi", "").trim();
      result = replaceFirstIgnoreAccents(result, "rupi", "").trim();
      result = replaceFirstIgnoreAccents(result, "roepia", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class IrishFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "IEP").getDisplayName(locale).toLowerCase();
      String pound = findPound(locale);
      String remaining = replaceFirstIgnoreAccents(esp, pound, "").trim();
      return remaining;
    }
  }

  private static class IsraeliFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "ILS").getDisplayName(locale).toLowerCase();
      String newWord = getNewWord(locale);
      result = replaceFirstIgnoreAccents(result, newWord, "").trim();
      result = replaceFirstIgnoreAccents(result, "schekel", "").trim();
      result = replaceFirstIgnoreAccents(result, "shekel", "").trim();
      result = replaceFirstIgnoreAccents(result, "sequel", "").trim();
      result = replaceFirstIgnoreAccents(result, "sechel", "").trim();
      result = replaceFirstIgnoreAccents(result, "siclo", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class IndianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "INR").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("roep", "").trim();
      result = result.replaceFirst("rup", "").trim();
      result = result.replaceFirst("roup", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class IraqiFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "IQD").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("dinar", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class IranianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "IRR").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("rial", "").trim();
      result = result.replaceFirst("riyal", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class IcelandicFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "ISK").getDisplayName(locale).toLowerCase();
      String remaining = nlg.replaceFirst(findKrones(locale), "").trim();
      remaining = replaceFirstIgnoreAccents(remaining, findKrona(locale), "").trim();
      return remaining;
    }
  }

  private static class ItalianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String itl = findCurrency(currencies, "ITL").getDisplayName(locale).toLowerCase();
      String lira = findLira(locale);
      String remaining = replaceFirstIgnoreAccents(itl, lira, "").trim();
      return remaining;
    }
  }

  private static class JamaicanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String aud = findCurrency(currencies, "JMD").getDisplayName(locale).toLowerCase();
      String cad = findCurrency(currencies, "AUD").getDisplayName(locale).toLowerCase();
      String remaining = extractDelta(aud, cad);
      return remaining;
    }
  }

  private static class JordanianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "JOD").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("dinar", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class JaponeseFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "JPY").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("yen", "").trim();
      result = result.replaceFirst("iene", "").trim();
      result = WordUtil.removeShortWords(result, 2);

      if (oneOf(locale, "es")) {
        result = Locale.forLanguageTag("ja").getDisplayLanguage(locale);
      }

      return result;
    }
  }

  private static class KenyanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "KES").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "shilling", "").trim();
      result = replaceFirstIgnoreAccents(result, "siling", "").trim();
      result = replaceFirstIgnoreAccents(result, "xelim", "").trim();
      result = replaceFirstIgnoreAccents(result, "chelin", "").trim();
      result = replaceFirstIgnoreAccents(result, "scellino", "").trim();
      result = replaceFirstIgnoreAccents(result, "schilling", "").trim();
      result = result.replace('-', ' ');
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class KyrgystaniFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "KGS").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("som", "").trim();
      result = WordUtil.removeShortWords(result, 2);

      if (oneOf(locale, "es")) {
        result = getAdjective(Locale.ITALIAN);
      }

      return result;
    }
  }

  private static class CambodianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "KHR").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("riel", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = "camboyano";
      }

      return result;
    }
  }

  private static class NorthKoreanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String kpw = extractName(findCurrency(currencies, "KPW"), locale, "won");
      return kpw;
    }
  }

  private static class SouthKoreanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String krw = extractName(findCurrency(currencies, "KRW"), locale, "won");
      return krw;
    }
  }

  private static class KuwaitiFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "KWD").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("dinar", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class KazakhstaniFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "KZT").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("tenge", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class LaotianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "LAK").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("kip", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = Locale.forLanguageTag("lo").getDisplayLanguage(locale);
      }

      return result;
    }
  }

  private static class LibaneseFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "LBP").getDisplayName(locale).toLowerCase();
      String pound = findPound(locale);
      result = result.replaceFirst(pound, "").trim();
      result = result.replaceFirst("lira", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class SriLankanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "LKR").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("roep", "").trim();
      result = result.replaceFirst("rup", "").trim();
      result = result.replaceFirst("roup", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class LiberianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "LRD").getDisplayName(locale).toLowerCase();
      String dollar = findDollar(locale);
      String remaining = replaceFirstIgnoreAccents(esp, dollar, "").trim();
      return remaining;
    }
  }

  private static class LithuanianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "LTL").getDisplayName(locale).toLowerCase();
      String remaining = nlg.replaceFirst("litas", "").trim();
      remaining = remaining.replaceFirst("litu", "").trim();
      return remaining;
    }
  }

  private static class LatvianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "LVL").getDisplayName(locale).toLowerCase();
      String florin = "lats";
      String remaining = nlg.replaceFirst(florin, "").trim();
      return remaining;
    }
  }

  private static class LuxembourgianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "LUF").getDisplayName(locale).toLowerCase();
      String pound = findFranc(locale);
      String remaining = replaceFirstIgnoreAccents(esp, pound, "").trim();
      return remaining;
    }
  }

  private static class LibyanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "LYD").getDisplayName(locale).toLowerCase();
      String result = replaceFirstIgnoreAccents(esp, "dinar", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class MoroccanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "MAD").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "dirham", "").trim();
      return result;
    }
  }

  private static class MoldovanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "MDL").getDisplayName(locale).toLowerCase();
      String result = replaceFirstIgnoreAccents(esp, "leu", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class MalagasyFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "MGF").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "fran", "").trim();
      result = result.replace('-', ' ');
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class MacedonianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "MKD").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "denar", "").trim();
      result = replaceFirstIgnoreAccents(result, "dinar", "").trim();
      result = WordUtil.removeShortWords(result, 2);

      if (oneOf(locale, "ro")) {
        result = getAdjective(Locale.ENGLISH);
      }

      return result;
    }
  }

  private static class MyanmarFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "MMK").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("kyat", "").trim();
      result = result.replaceFirst("quiate", "").trim();
      result = WordUtil.removeShortWords(result, 2);

      if (oneOf(locale, "es")) {
        result = Locale.forLanguageTag("my").getDisplayLanguage(locale);
      }

      return result;
    }
  }

  private static class MongolianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "MNT").getDisplayName(locale).toLowerCase();
      String result = replaceFirstIgnoreAccents(esp, "tugrik", "").trim();
      result = replaceFirstIgnoreAccents(result, "togrog", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.PORTUGUESE.toLocale());
      }

      return result;
    }
  }

  private static class MacaneseFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "MOP").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "pataca", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class MauritanianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "MRU").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "ouguiya", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es", "it")) {
        result = getAdjective(Language.PORTUGUESE.toLocale()); // Ganes?
      }
      return result;
    }
  }

  private static class MalteseFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "MTL").getDisplayName(locale).toLowerCase();
      String result = replaceFirstIgnoreAccents(esp, "lir", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class MalawianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "MWK").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "kwacha", "").trim();
      result = replaceFirstIgnoreAccents(result, "kuacha", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class MexicanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "MXN").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "peso", "").trim();
      return result;
    }
  }

  private static class MalaisianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "MYR").getDisplayName(locale).toLowerCase();
      String result = replaceFirstIgnoreAccents(esp, "ringgit", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.ITALIAN.toLocale());
      }

      return result;
    }
  }

  private static class NamibianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "NAD").getDisplayName(locale).toLowerCase();
      String dollar = findDollar(locale);
      String remaining = replaceFirstIgnoreAccents(esp, dollar, "").trim();
      return remaining;
    }
  }

  private static class NigerianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "NGN").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "naira", "").trim();

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.PORTUGUESE.toLocale());
      }

      return result;
    }
  }

  private static class NicaraguanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "NIO").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "cordob", "").trim();
      result = replaceFirstIgnoreAccents(result, "or", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.ITALIAN.toLocale());
      } else if (oneOf(locale, "ro")) {
        result = getAdjective(Language.ENGLISH.toLocale());
      }

      return result;
    }
  }

  private static class DutchFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "NLG").getDisplayName(locale).toLowerCase();
      String florin = findFlorin(locale);
      String remaining = nlg.replaceFirst(florin, "").trim();
      return remaining;
    }
  }

  private static class NorvegianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "NOK").getDisplayName(locale).toLowerCase();
      String florin = findKrones(locale);
      String remaining = nlg.replaceFirst(florin, "").trim();
      return remaining;
    }
  }

  private static class NepaleseFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "NPR").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "roupi", "").trim();
      result = replaceFirstIgnoreAccents(result, "roep", "").trim();
      result = replaceFirstIgnoreAccents(result, "rup", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class NewZelanderFinder extends AdjectiveFinder {

    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String aud = findCurrency(currencies, "NZD").getDisplayName(locale).toLowerCase();
      String cad = findCurrency(currencies, "CAD").getDisplayName(locale).toLowerCase();
      String remaining = extractDelta(aud, cad);
      remaining = remaining.replace("-", "").trim();
      return remaining;
    }
  }

  private static class OmaniFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "OMR").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "riyal", "").trim();
      result = replaceFirstIgnoreAccents(result, "rial", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class PanamanianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "PAB").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "balboa", "").trim();
      return result;
    }
  }

  private static class PeruvianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "PEN").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "sol", "").trim();
      result = replaceFirstIgnoreAccents(result, "novo", "").trim();

      if (oneOf(locale, "ro")) {
        result = getAdjective(Locale.ENGLISH);
      }

      return result;
    }
  }

  private static class PhilipinoFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "PHP").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("peso", "").trim();
      result = result.replaceFirst("piso", "").trim();
      result = WordUtil.removeShortWords(result, 2);

      if (oneOf(locale, "")) {
        result = Locale.forLanguageTag("fil").getDisplayLanguage(locale);
      }

      return result;
    }
  }

  private static class PakistaniFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "PKR").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("roep", "").trim();
      result = result.replaceFirst("rup", "").trim();
      result = result.replaceFirst("roup", "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class PolishFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "PLN").getDisplayName(locale).toLowerCase();
      String zloty = findZloty(locale);
      String polish = replaceFirstIgnoreAccents(esp, zloty, "").trim();

      if (oneOf(locale, "es", "ro")) {
        polish = Locale.forLanguageTag("pl").getDisplayLanguage(locale);
      }

      return polish;
    }
  }

  private static class PortugueseFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "PTE").getDisplayName(locale).toLowerCase();
      String remaining = replaceFirstIgnoreAccents(esp, "escudo", "").trim();
      return remaining;
    }
  }

  private static class ParaguayanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "PYG").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "guarani", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "ro")) {
        result = getAdjective(Language.ENGLISH.toLocale());
      }
      return result;
    }
  }

  private static class RomanianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "RON").getDisplayName(locale).toLowerCase();
      String result = nlg.replaceFirst("leu", "").trim();
      return result;
    }
  }

  private static class SerbianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "RSD").getDisplayName(locale).toLowerCase();
      String result = nlg.replaceFirst("dinar", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class RussianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "RUB").getDisplayName(locale).toLowerCase();
      String result = nlg.replaceFirst(findRubles(locale), "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class RwandanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "RWF").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "fran", "").trim();
      result = result.replace('-', ' ');
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class SaudiFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "SAR").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("riyal", "").trim();
      result = result.replaceFirst("rial", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class SwedishFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      String language = locale.getLanguage();
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "SEK").getDisplayName(locale).toLowerCase();
      String krone = language.equals("en") ? "krona" : findKrones(locale);
      String remaining = nlg.replaceFirst(krone, "").trim();
      return remaining;
    }
  }

  private static class SloveneFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "SIT").getDisplayName(locale).toLowerCase();
      String result = replaceFirstIgnoreAccents(esp, "tolar", "").trim();
      result = replaceFirstIgnoreAccents(result, "tallero", "").trim();
      result = replaceFirstIgnoreAccents(result, "bons", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class SlovakFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "SKK").getDisplayName(locale).toLowerCase();
      String florin = findKrones(locale);
      String remaining = nlg.replaceFirst(florin, "").trim();
      remaining = remaining.replaceFirst("koruna", "").trim();
      return remaining;
    }
  }

  private static class SomalianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "SOS").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "shilling", "").trim();
      result = replaceFirstIgnoreAccents(result, "siling", "").trim();
      result = replaceFirstIgnoreAccents(result, "xelim", "").trim();
      result = replaceFirstIgnoreAccents(result, "chelin", "").trim();
      result = replaceFirstIgnoreAccents(result, "scellino", "").trim();
      result = replaceFirstIgnoreAccents(result, "schilling", "").trim();
      result = result.replace('-', ' ');
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class SurinameseFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "SRD").getDisplayName(locale).toLowerCase();
      String dollar = findDollar(locale);
      result = replaceFirstIgnoreAccents(result, dollar, "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class SyrianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "SYP").getDisplayName(locale).toLowerCase();
      String pound = findPound(locale);
      result = result.replaceFirst(pound, "").trim();
      result = result.replaceFirst("lira", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class SwaziFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "SZL").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "lilangeni", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.PORTUGUESE.toLocale());
      }
      return result;
    }
  }

  private static class ThaiFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "THB").getDisplayName(locale).toLowerCase();
      String result = nlg.replaceFirst("baht", "").trim();

      if (oneOf(locale, "es")) {
        result = Locale.forLanguageTag("th").getDisplayLanguage(locale);
      }

      return result;
    }
  }

  private static class Tajikistani extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "TJS").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("somoni", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class TurkemnistaniFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "TMM").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("manat", "").trim();
      result = removeYears(result);

      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class TunisianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "TND").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "dinar", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class TonganFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "TOP").getDisplayName(locale).toLowerCase();
      result = result.replace("’", "");
      result = result.replace("ʻ", "");
      result = replaceFirstIgnoreAccents(result, "paanga", "").trim();

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.PORTUGUESE.toLocale());
      }

      return result;
    }
  }

  private static class TurkishFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "TRY").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("lir", "").trim();
      result = result.replaceFirst("livr", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class TawaineseFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "TWD").getDisplayName(locale).toLowerCase();
      String dollar = findDollar(locale);
      String newWord = getNewWord(locale);
      result = replaceFirstIgnoreAccents(result, dollar, "").trim();
      result = replaceFirstIgnoreAccents(result, newWord, "").trim();
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class TanzanianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "TZS").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "shilling", "").trim();
      result = replaceFirstIgnoreAccents(result, "siling", "").trim();
      result = replaceFirstIgnoreAccents(result, "xelim", "").trim();
      result = replaceFirstIgnoreAccents(result, "chelin", "").trim();
      result = replaceFirstIgnoreAccents(result, "scellino", "").trim();
      result = replaceFirstIgnoreAccents(result, "schilling", "").trim();
      result = result.replace('-', ' ');
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static class AmericanFinder extends AdjectiveFinder {
    private CountryGlossary countryGlossary = new CountryGlossary();

    @Override
    public String getAdjective(Locale locale) {
      return countryGlossary.getAmericanWord(locale);
    }
  }

  private static class UkranianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "UAH").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("hryvnia", "").trim();
      result = result.replaceFirst("hrywnja", "").trim();
      result = result.replaceFirst("grivn", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es", "ro")) {
        result = Locale.forLanguageTag("uk").getDisplayLanguage(locale);
      }

      return result;
    }
  }

  private static class UruguayanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "UYU").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "peso", "").trim();
      return result;
    }
  }

  private static class UzbekiFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "UZS").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("sum", "").trim();
      result = result.replaceFirst("som", "").trim();
      result = WordUtil.removeShortWords(result, 2);

      if (oneOf(locale, "es")) {
        result = getAdjective(Locale.ITALIAN);
      }

      return result;
    }
  }

  private static class VenezualianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "VES").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "bolivar", "").trim();

      if (oneOf(locale, "ro")) {
        result = getAdjective(Locale.ENGLISH);
      }

      return result;
    }
  }

  private static class VietnameseFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "VND").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "dong", "").trim();
      result = WordUtil.removeShortWords(result, 2);

      if (oneOf(locale, "es")) {
        result = Locale.forLanguageTag("vi").getDisplayLanguage(locale);
      }

      return result;
    }
  }

  private static class VanuatuanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "VUV").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "vatu", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.ITALIAN.toLocale()); // Ganes?
      }
      return result;
    }
  }

  private static class YemeniFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "YER").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("riyal", "").trim();
      result = result.replaceFirst("rial", "").trim();
      result = WordUtil.removeShortWords(result, 2);
      return result;
    }
  }

  private static class SouthAfricanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "ZAR").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "rand", "").trim();

      if (oneOf(locale, "es")) {
        result = getAdjective(Language.ITALIAN.toLocale());
      }

      return result;
    }
  }

  private static class ZambianFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "ZMW").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "kuacha", "").trim();
      result = replaceFirstIgnoreAccents(result, "kwacha", "").trim();
      result = WordUtil.removeShortWords(result, 3);

      if (oneOf(locale, "de")) {
        result = getAdjective(Language.DUTCH.toLocale()); // Ganes?
      }
      return result;
    }
  }

  private static class ZimbabweanFinder extends AdjectiveFinder {
    @Override
    public String getAdjective(Locale locale) {
      List<Currency> currencies = getAvailableCurrencies();
      String result = findCurrency(currencies, "ZWD").getDisplayName(locale).toLowerCase();
      String dollar = findDollar(locale);
      result = replaceFirstIgnoreAccents(result, dollar, "");
      result = removeYears(result);
      result = WordUtil.removeShortWords(result, 3);
      return result;
    }
  }

  private static String removeYears(String result) {
    result = result.replace('(', ' ');
    result = result.replace('–', ' ');
    result = result.replace(')', ' ');
    result = result.replaceAll("\\d", "").trim();
    return result;
  }

  private static String extractName(Currency currency, Locale locale, String... extras) {
    String extracted = currency.getDisplayName(locale).toLowerCase();
    for (String extra : extras) {
      extracted = extracted.replace(extra, "").trim();
    }

    return extracted;
  }

  private static String extractDelta(String original, String delta) {
    String dollar = WordUtil.findLonguestCommonWord(original, delta).trim();
    String remaining = WordUtil.removeSubstring(original, dollar).trim().toLowerCase();
    return remaining;
  }

  private static String findZloty(Locale locale) {
    String language = locale.getLanguage();
    String zloty;

    if ("es".equals(language)) {
      zloty = "esloti";
    } else if ("ro".equals(language)) {
      zloty = "zlot";
    } else {
      zloty = "zloty";
    }

    return zloty;
  }

  private static String findLira(Locale locale) {
    String language = locale.getLanguage();
    String lira = "lira";

    if (List.of("fr", "nl", "sv").contains(language)) {
      lira = "lire";
    }

    return lira;
  }

  private static String findSchilling(Locale locale) {
    String language = locale.getLanguage();
    String schilling;

    if (language.equals("it")) {
      schilling = "scellino";
    } else if (language.equals("es")) {
      schilling = "chelín";
    } else if (language.equals("pt")) {
      schilling = "xelim";
    } else if (language.equals("ro")) {
      schilling = "șiling";
    } else {
      schilling = "schilling";
    }

    return schilling;
  }

  private static String findDollar(Locale locale) {
    return findCurrency(locale, DOLLARS);
  }

  private static String findPound(Locale locale) {
    return findCurrency(locale, POUNDS);
  }

  private static String findPeso(Locale locale) {
    return findCurrency(locale, PESOS);
  }

  private static String findFranc(Locale locale) {
    return findCurrency(locale, FRANCS);
  }

  private static String findFranken(Locale locale) {
    return findCurrency(locale, FRANKEN);
  }

  private static String findFlorin(Locale locale) {
    return findCurrency(locale, FLORINS);
  }

  private static String findKrona(Locale locale) {
    return "krona";
  }

  private static String findKrones(Locale locale) {
    return findCurrency(locale, KRONES);
  }

  private static String findRubles(Locale locale) {
    return findCurrency(locale, RUBLES);
  }

  private static String findMark(Locale locale) {
    return findCurrency(locale, MARKS);
  }

  private static String findCurrency(Locale locale, List<String> codes) {
    List<Currency> currencies = getAvailableCurrencies();
    List<String> previous = null;

    for (String code : codes) {
      Currency currency = findCurrency(currencies, code);
      String currencyName = currency.getDisplayName(locale).toLowerCase();
      List<String> current = List.of(currencyName.replace("-", " ").split("\\s+"));
      previous = findCommon(previous, current);
    }

    return WordUtil.findLongestWord(previous);
  }

  private static List<String> findCommon(List<String> previous, List<String> words) {
    if (words.size() == 1) {
      String word = words.get(0);
      word = (previous == null) ? word : findLonguestCommonSequence(previous.get(0), word);
      return List.of(word);
    } else {
      return findCommonWords(previous, words);
    }
  }

  private static String findLonguestCommonSequence(String s1, String s2) {
    String common = WordUtil.findLonguestCommonSequence(s1, s2).trim();
    common = WordUtil.removeShortWords(common, 3);
    return common;
  }

  private static List<String> findCommonWords(List<String> previous, List<String> words) {
    List<String> commonWords = new ArrayList<>();
    if (previous == null) {
      commonWords.addAll(words);
    } else {
      for (String word : words) {
        if (previous.contains(word)) {
          commonWords.add(word);
        }
      }
    }

    return commonWords;
  }

  private static String replaceFirstIgnoreAccents(
      String original, String replaced, String replacement) {
    String asciiOrignal = StringUtil.stripAccents(original);
    String asciiReplaced = StringUtil.stripAccents(replaced);
    int idx = asciiOrignal.indexOf(asciiReplaced);

    if (idx != -1) {
      StringBuilder builder = new StringBuilder();
      builder.append(original, 0, idx);
      builder.append(replacement);
      builder.append(original, idx + replaced.length(), original.length());
      original = builder.toString();
    }

    return original;
  }

  private static Currency findCurrency(List<Currency> currencies, String code) {
    return currencies.stream().filter(c -> c.getCurrencyCode().equals(code)).findAny().orElse(null);
  }

  private static boolean oneOf(Locale locale, String... languages) {
    String language = locale.getLanguage();
    return List.of(languages).contains(language);
  }

  private abstract static class AdjectiveFinder {
    public abstract String getAdjective(Locale locale);
  }
}
