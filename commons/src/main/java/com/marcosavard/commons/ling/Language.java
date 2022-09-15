package com.marcosavard.commons.ling;

import java.util.*;

import static com.marcosavard.commons.lang.CharUtil.isDiacritical;

public class Language {
    private static final String[]  LANGUAGES_WITH_DIACRITICS = new String[] {"nb", "vo"};

    public static final Language AFRIKANER = Language.of("af");

    public static final Language ALBANIAN = Language.of("sq");

    public static final Language AZERBAIJANI = Language.of("az");
    public static final Language CROATIAN = Language.of("hr");

    public static final Language CZECK = Language.of("cs");
    public static final Language DANISH = Language.of("da");
    public static final Language DUTCH = Language.of("nl");
    public static final Language ENGLISH = Language.of(Locale.ENGLISH.getLanguage());
    public static final Language ESPERANTO = Language.of("eo");
    public static final Language FRENCH = Language.of(Locale.FRENCH.getLanguage());
    public static final Language GERMAN = Language.of(Locale.GERMAN.getLanguage());
    public static final Language FINNISH = Language.of("fi");
    public static final Language HUNGARIAN = Language.of("hu");
    public static final Language ITALIAN = Language.of(Locale.ITALIAN.getLanguage());

    public static final Language ICELANDIC = Language.of("is");

    public static final Language LATIN = Language.of("la");

    public static final Language NORVEGIAN = Language.of("no");

    public static final Language POLISH = Language.of("pl");

    public static final Language PORTUGUESE = Language.of("pt");
    public static final Language ROMANIAN = Language.of("ro");

    public static final Language SLOVAK = Language.of("sk");

    public static final Language SLOVENIAN = Language.of("sl");
    public static final Language SPANISH = Language.of("es");
    public static final Language SWEDISH = Language.of("sv");

    public static final Language TURKISH = Language.of("tr");

    private static Map<String, Language> languageByCode;
    private static Map<Character.UnicodeScript, List<Language>> languagesByScript;

    //irregular language to country map
    private static String[][]  LANGUAGE_TO_COUNTRY_MAP = new String[][]{
            new String[]{"cs", "CZ"},
            new String[]{"da", "DK"},
            new String[]{"en", "GB"},
            new String[]{"in", "ID"},
            new String[]{"sl", "SI"},
            new String[]{"sq", "AL"},
            new String[]{"sr", "RS"},
            new String[]{"sv", "SE"},
            new String[]{"vi", "VN"}
    };



    private final Locale languageLocale;

    private final List<Locale> countryLocales = new ArrayList<>();

    private final Character.UnicodeScript script;

    private final List<Character> diacritics;

    private Language(Locale locale) {
        this.languageLocale = locale;
        String displayName = locale.getDisplayName(locale);
        char firstLetter = displayName.charAt(0);
        script = Character.UnicodeScript.of(firstLetter);
        diacritics = findDiacritics(locale);
        findCountryLocales();
    }

    public static Language[] getISOLanguages() {
        initMaps();
        return languageByCode.values().toArray(new Language[0]);
    }

    private static void initMaps() {
        if (languageByCode == null) {
            languageByCode = new LinkedHashMap<>();
            languagesByScript = new LinkedHashMap<>();

            String[] languageCodes = Locale.getISOLanguages();

            for (String languageCode : languageCodes) {
                Locale locale = Locale.forLanguageTag(languageCode);
                Language language = new Language(locale);
                languageByCode.put(languageCode, language);

                Character.UnicodeScript script = language.script;
                if (! languagesByScript.containsKey(script)) {
                    languagesByScript.put(script, new ArrayList<>());
                }

                List<Language> languages = languagesByScript.get(script);
                languages.add(language);
            }
        }
    }

    private static Language of(String languageCode) {
        initMaps();
        return languageByCode.get(languageCode);
    }

    public static Language[] ofScript(Character.UnicodeScript script) {
        initMaps();
        List<Language> languageList = languagesByScript.get(script);
        Language[] languages = (languageList == null) ? new Language[] {} : languageList.toArray(new Language[0]);
        return languages;
    }

    public Locale toLocale() {
        return languageLocale;
    }

    public Character.UnicodeScript getUnicodeScript() {
        return script;
    }

    @Override
    public String toString() {
        return languageLocale.getLanguage();
    }

    private List<Character> findDiacritics(Locale locale) {
        List<Character> diacritics = new ArrayList<>();
        String[] languages = Locale.getISOLanguages();

        for (String languageCode : languages) {
            if (! Arrays.asList(LANGUAGES_WITH_DIACRITICS).contains(languageCode)) {
                Locale l = Locale.forLanguageTag(languageCode);
                String displayName = l.getDisplayLanguage(locale);

                for (int i = 0; i < displayName.length(); i++) {
                    char c = displayName.charAt(i);

                    if (isDiacritical(c) && !diacritics.contains(c)) {
                        diacritics.add(c);
                    }
                }
            }
        }

        return diacritics;
    }

    private void findCountryLocales() {
        List<Locale> allLocales = getAllLocales();
        List<Locale> foundLocales = allLocales.stream().filter(l -> languageLocale.getLanguage().equals(l.getLanguage())).toList();
        countryLocales.addAll(foundLocales);
    }

    private List<Locale> getAllLocales() {
        List<Locale> allLocales = new ArrayList<>();
        allLocales.addAll(Arrays.asList(Locale.getAvailableLocales()));
        allLocales.addAll(getMissingLocales());
        return allLocales;
    }

    public List<Character> getDiacriticalCharacters() {
        return diacritics;
    }

    public boolean isLatinScript() {
        return script.equals(Character.UnicodeScript.LATIN);
    }

    public boolean isWorldLanguage() {
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && ! l.getCountry().equals("001") )
                .findFirst()
                .orElse(null);
        return (countryLocale == null);
    }

    public boolean isAmericanLanguage() {
        List<String> americanCountries = Arrays.asList(new String[] {"BO", "BR", "CA", "GL", "HT", "PE", "PY", "US"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && americanCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isEuropeanLanguage() {
        List<String> invalidCountries = Arrays.asList(new String[] {"EA", "IC"});
        Currency euro = Currency.getInstance("EUR");
        List<String> europeanCountry = Arrays.asList(new String[] {"AL", "BA", "CH",  "CZ", "DK", "GB", "HR", "HU", "IM", "IS", "MD", "NO", "PL", "RO", "RU"});

        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> l.getCountry().length() == 2)
                .filter(l -> ! invalidCountries.contains(l.getCountry()) && (euro.equals(Currency.getInstance(l)) || europeanCountry.contains(l.getCountry())))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isWestEuropeanLanguage() {
        List<String> westEuropeCountries = Arrays.asList(new String[] {"BE", "FR", "GB", "IR", "LU", "NL"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && westEuropeCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isSouthEuropeanLanguage() {
        List<String> southEuropeCountries = Arrays.asList(new String[] {"ES", "IT", "PT"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && southEuropeCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isNorthEuropeanLanguage() {
        List<String> northEuropeCountries = Arrays.asList(new String[] {"DK", "FI", "IS", "NO", "SE"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && northEuropeCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isCentralEuropeanLanguage() {
        List<String> centralEuropeCountries = Arrays.asList(new String[] {"AT", "CH", "CZ", "DE", "ET", "HR", "HU", "LT", "LV", "PL", "SK", "SL"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && centralEuropeCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isWesternRomanceLanguage() {
        List<String> romanceLanguages = Arrays.asList(new String[] {"an", "ca", "es", "fr", "gl", "oc", "pt", "rm", "wa"});
        return romanceLanguages.contains(languageLocale.getLanguage());
    }

    public boolean isCentralRomanceLanguage() {
        List<String> romanceLanguages = Arrays.asList(new String[] {"co", "it", "sc"});
        return romanceLanguages.contains(languageLocale.getLanguage());
    }

    public boolean isEasternRomanceLanguage() {
        List<String> romanceLanguages = Arrays.asList(new String[] {"mo", "ro"});
        return romanceLanguages.contains(languageLocale.getLanguage());
    }

    public boolean isRomanceLanguage() {
        return isWesternRomanceLanguage() || isCentralRomanceLanguage() || isEasternRomanceLanguage();
    }

    public boolean isCelticLanguage() {
        List<String> celticLanguages = Arrays.asList(new String[] {"br", "cy", "ga", "gd", "gv", "kw"});
        return celticLanguages.contains(languageLocale.getLanguage());
    }

    public boolean isScandinavianLanguage() {
        List<String> scandinavianLanguages = Arrays.asList(new String[] {"da", "fo", "is", "nb", "nn", "no", "sv"});
        return scandinavianLanguages.contains(languageLocale.getLanguage());
    }

    public boolean isWestGermanicLanguage() {
        List<String> scandinavianLanguages = Arrays.asList(new String[] {"de", "en", "fy", "lb", "li", "nl"});
        return scandinavianLanguages.contains(languageLocale.getLanguage());
    }

    public boolean isGermanicLanguage() {
        return isScandinavianLanguage() || isWestGermanicLanguage();
    }
    public boolean isSouthSlavicLanguage() {
        List<String> southSlavicLanguages = Arrays.asList(new String[] {"bs", "hr", "sl"});
        return southSlavicLanguages.contains(languageLocale.getLanguage());
    }

    public boolean isWestSlavicLanguage() {
        List<String> westSlavicLanguages = Arrays.asList(new String[] {"cs", "pl", "sk"});
        return westSlavicLanguages.contains(languageLocale.getLanguage());
    }

    public boolean isSlavicLanguage() {
        return isSouthSlavicLanguage() || isWestSlavicLanguage();
    }

    public boolean isBalticLanguage() {
        List<String> balticLanguages = Arrays.asList(new String[] {"lt", "lv"});
        return balticLanguages.contains(languageLocale.getLanguage());
    }

    public boolean isUralicLanguage() {
        List<String> uralicLanguages = Arrays.asList(new String[] {"et", "fi", "hu", "kv", "se"});
        return uralicLanguages.contains(languageLocale.getLanguage());
    }

    public boolean isTurkicLanguage() {
        List<String> turkicLanguages = Arrays.asList(new String[] {"ba", "cv", "tr"});
        return turkicLanguages.contains(languageLocale.getLanguage());
    }

    public boolean isOceanianLanguage() {
        List<String> oceanianCountries = Arrays.asList(new String[] {"FJ", "GU", "MH", "NR", "NZ", "PF", "PG", "TO", "VU", "WS"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && oceanianCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isIndianLanguage() {
        List<String> indianCountries = Arrays.asList(new String[] {"IN", "MV"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && indianCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isFarEastLanguage() {
        List<String> farEastCountries = Arrays.asList(new String[] {"CN", "ID", "MY", "PH", "VN"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && farEastCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isCentralAsiaLanguage() {
        List<String> centralAsiaCountries = Arrays.asList(new String[] {"AZ", "GE", "IR", "TM", "TR", "UZ"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && centralAsiaCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isSouthAfricaLanguage() {
        List<String> southAfricaCountries = Arrays.asList(new String[] {"AO", "MW", "NA", "ZA", "ZW"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && southAfricaCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isCentralAfricaLanguage() {
        List<String> centralAfricaCountries = Arrays.asList(new String[] {"BI", "CD", "CF", "RW", "UG"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && centralAfricaCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isEastAfricaLanguage() {
        List<String> centralAfricaCountries = Arrays.asList(new String[] {"ET", "KE", "MG", "TZ"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && centralAfricaCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }

    public boolean isWestAfricaLanguage() {
        List<String> eastAfricaCountries = Arrays.asList(new String[] {"GH", "ML", "NG", "SN"});
        Locale countryLocale = countryLocales
                .stream()
                .filter(l -> ! l.getCountry().isEmpty() && eastAfricaCountries.contains(l.getCountry()))
                .findFirst()
                .orElse(null);
        return (countryLocale != null);
    }


    private Collection<Locale> getMissingLocales() {
        List<Locale> missingLocales = new ArrayList<>();
        missingLocales.add(new Locale.Builder().setLanguage("aa").setRegion("DJ").build());
        missingLocales.add(new Locale.Builder().setLanguage("ab").setRegion("GE").build());
        missingLocales.add(new Locale.Builder().setLanguage("ae").setRegion("IR").build());
        missingLocales.add(new Locale.Builder().setLanguage("an").setRegion("ES").build());
        missingLocales.add(new Locale.Builder().setLanguage("av").setRegion("GE").build());
        missingLocales.add(new Locale.Builder().setLanguage("ay").setRegion("BO").build());
        missingLocales.add(new Locale.Builder().setLanguage("ba").setRegion("RU").build());
        missingLocales.add(new Locale.Builder().setLanguage("bh").setRegion("IN").build());
        missingLocales.add(new Locale.Builder().setLanguage("bi").setRegion("VU").build());
        missingLocales.add(new Locale.Builder().setLanguage("ch").setRegion("GU").build());
        missingLocales.add(new Locale.Builder().setLanguage("co").setRegion("FR").build());
        missingLocales.add(new Locale.Builder().setLanguage("cr").setRegion("CA").build());
        missingLocales.add(new Locale.Builder().setLanguage("cv").setRegion("RU").build());
        missingLocales.add(new Locale.Builder().setLanguage("dv").setRegion("MV").build());
        missingLocales.add(new Locale.Builder().setLanguage("fj").setRegion("FJ").build());
        missingLocales.add(new Locale.Builder().setLanguage("gn").setRegion("PY").build());
        missingLocales.add(new Locale.Builder().setLanguage("ho").setRegion("PG").build());
        missingLocales.add(new Locale.Builder().setLanguage("ht").setRegion("HT").build());
        missingLocales.add(new Locale.Builder().setLanguage("hz").setRegion("NA").build());
        missingLocales.add(new Locale.Builder().setLanguage("ik").setRegion("US").build());
        missingLocales.add(new Locale.Builder().setLanguage("iu").setRegion("CA").build());
        missingLocales.add(new Locale.Builder().setLanguage("kg").setRegion("CD").build());
        missingLocales.add(new Locale.Builder().setLanguage("kj").setRegion("NA").build());
        missingLocales.add(new Locale.Builder().setLanguage("kr").setRegion("NG").build());
        missingLocales.add(new Locale.Builder().setLanguage("kv").setRegion("RU").build());
        missingLocales.add(new Locale.Builder().setLanguage("li").setRegion("NL").build());
        missingLocales.add(new Locale.Builder().setLanguage("mh").setRegion("MH").build());
        missingLocales.add(new Locale.Builder().setLanguage("mo").setRegion("MD").build());
        missingLocales.add(new Locale.Builder().setLanguage("na").setRegion("NR").build());
        missingLocales.add(new Locale.Builder().setLanguage("ng").setRegion("NA").build());
        missingLocales.add(new Locale.Builder().setLanguage("nr").setRegion("ZA").build());
        missingLocales.add(new Locale.Builder().setLanguage("nv").setRegion("US").build());
        missingLocales.add(new Locale.Builder().setLanguage("ny").setRegion("MW").build());
        missingLocales.add(new Locale.Builder().setLanguage("oc").setRegion("FR").build());
        missingLocales.add(new Locale.Builder().setLanguage("oj").setRegion("CA").build());
        missingLocales.add(new Locale.Builder().setLanguage("pi").setRegion("IN").build());
        missingLocales.add(new Locale.Builder().setLanguage("sa").setRegion("IN").build());
        missingLocales.add(new Locale.Builder().setLanguage("sc").setRegion("IT").build());
        missingLocales.add(new Locale.Builder().setLanguage("sm").setRegion("WS").build());
        missingLocales.add(new Locale.Builder().setLanguage("ss").setRegion("ZA").build());
        missingLocales.add(new Locale.Builder().setLanguage("st").setRegion("ZA").build());
        missingLocales.add(new Locale.Builder().setLanguage("tl").setRegion("PH").build());
        missingLocales.add(new Locale.Builder().setLanguage("tn").setRegion("ZA").build());
        missingLocales.add(new Locale.Builder().setLanguage("ts").setRegion("ZA").build());
        missingLocales.add(new Locale.Builder().setLanguage("tw").setRegion("GH").build());
        missingLocales.add(new Locale.Builder().setLanguage("ty").setRegion("PF").build());
        missingLocales.add(new Locale.Builder().setLanguage("ve").setRegion("ZA").build());
        missingLocales.add(new Locale.Builder().setLanguage("wa").setRegion("BE").build());
        missingLocales.add(new Locale.Builder().setLanguage("za").setRegion("CN").build());
        return missingLocales;
    }



}
