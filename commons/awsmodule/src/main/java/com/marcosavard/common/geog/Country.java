package com.marcosavard.common.geog;

import com.marcosavard.common.lang.StringUtil;
import com.marcosavard.common.ling.Language;

import java.util.*;

public enum Country {
    ANDORRA("AD", "ca"),
    UNITED_ARAB_EMIRATES("AE", "ar"),
    AFGHANISTAN("AF", "ps"),
    ANTIGUA_DEPS("AG", "en"),
    ANGUILLA("AI", "en"),
    ALBANIA("AL", "sq"),
    ARMENIA("AM", "hy"),
    ANGOLA("AO", "pt"),
    ANTARCTICA("AQ"),
    ARGENTINA("AR", "es"),
    AMERICAN_SAMOAS("AS", "es"),
    AUSTRIA("AT", "de"),
    AUSTRALIA("AU", "en"),
    ARUBA("AW", "nl"),
    ALAND_ISLANDS("AX", "sv"),
    AZERBAIJAN("AZ", "az"),
    BOSNIA_HERZEGOVINA("BA", "bs"),
    BARBADOS("BB", "en"),
    BANGLADESH("BD", "bn"),
    BELGIUM_FRENCH("BE", "fr"),
    BELGIUM_DUTCH("BE", "nl"),
    BURKINA("BF", "fr"),
    BULGARIA("BG", "bg"),
    BAHRAIN("BH", "ar"),
    BURUNDI("BI", "rn"),
    BENIN("BJ", "fr"),
    SAINT_BARTHELEMY("BL", "fr"),
    BERMUDA("BM", "en"),
    BRUNEI("BN", "ms"),
    BOLIVIA("BO", "es"),
    DUTCH_CARABEANS("BQ", "nl"),
    BRAZIL("BR", "pt"),
    BAHAMAS("BS", "en"),
    BHUTAN("BT", "dz"),
    BOUVET_ISLAND("BV", "en"),
    BOTSWANA("BW", "en"),
    BELARUS("BY", "be"),
    BELIZE("BZ", "en"),
    CANADA_ENGLISH("CA", "en"),
    CANADA_FRENCH("CA", "fr"),
    COCOS_ISLANDS("CC", "en"),
    CONGO_KINSHASA("CD", "fr"),
    CENTRAL_AFRICA("CF", "fr"),
    CONGO_BRAZZAVILLE("CG", "fr"),
    SWITZERLAND_GERMAN("CH", "de"),
    SWITZERLAND_FRENCH("CH", "fr"),
    SWITZERLAND_ITALIAN("CH", "it"),
    IVORY_COAST("CI", "fr"),
    COOK_ISLANDS("CK", "en"),
    CHILE("CL", "es"),
    CAMEROON("CM", "fr"),
    CHINA("CN", "zh"),
    COLOMBIA("CO", "es"),
    COSTA_RICA("CR", "es"),
    CUBA("CU", "es"),
    CAPE_VERDE("CV", "pt"),
    CURACAO("CW", "nl"),
    CHRISTMAS_ISLAND("CX", "en"),
    CYPRUS_GREEK("CY", "el"),
    CYPRUS_TURKISH("CY", "tr"),
    CZECH_REPUBLIC("CZ", "cs"),
    GERMANY("DE", "de"),
    DJIBOUTI("DJ", "fr"),
    DENMARK("DK", "da"),
    DOMINICA("DM", "en"),
    DOMINICAN_REPUBLIC("DO", "es"),
    ALGERIA("DZ", "ar"),
    ECUADOR("EC", "es"),
    ESTONIA("EE", "et"),
    EGYPT("EG", "ar"),
    WESTERN_SAHARA("EH", "ar"),
    ERITREA("ER", "ti"),
    SPAIN("ES", "es"),
    ETHIOPIA("ET", "am"),
    FINLAND("FI", "fi"),
    FIJI("FJ", "en"),
    FALKLAND_ISLANDS("FK", "en"),
    MICRONESIA("FM", "en"),
    FAROE_ISLANDS("FO", "fo"),
    FRANCE("FR", "fr"),
    GABON("GA", "fr"),
    UNITED_KINGDOM("GB", "en"),
    GRENADA("GD", "en"),
    GEORGIA("GE", "ka"),
    FRENCH_GUIANA("GF", "fr"),
    GUERNSEY("GG", "en"),
    GHANA("GH", "en"),
    GIBRALTAR("GI", "en"),
    GREENLAND("GL", "kl"),
    GAMBIA("GM", "en"),
    GUINEA("GN", "fr"),
    GUADELOUPE("GP", "fr"),
    EQUATORIAL_GUINEA("GQ", "pt"),
    GREECE("GR", "el"),
    SOUTH_GEORGIA("GS"),
    GUATEMALA("GT", "es"),
    GUAM("GU", "en"),
    GUINEA_BISSAU("GW", "pt"),
    GUYANA("GY", "en"),
    HONK_KONG("HK", "zh"),
    HEARD_ISLAND_AND_MCDONALD_ISLANDS("HM"),
    HONDURAS("HN", "es"),
    CROATIA("HR", "hr"),
    HAITI("HT", "fr"),
    HUNGARY("HU", "hu"),
    INDONESIA("ID", "id"),
    IRELAND("IE", "en"),
    ISRAEL("IL", "he"),
    ISLE_OF_MAN("IM", "en"),
    INDIA("IN", "hi"),
    BRITISH_INDIAN_OCEAN_TERRITORY("IO", "en"),
    IRAQ("IQ", "ar"),
    IRAN("IR", "fa"),
    ICELAND("IS", "is"),
    ITALY("IT", "it"),
    JERSEY("JE", "en"),
    JAMAICA("JM", "en"),
    JORDAN("JO", "ar"),
    JAPAN("JP", "ja"),
    KENYA("KE", "sw"),
    KYRGYZSTAN("KG", "ky"),
    CAMBODIA("KH", "km"),
    KIRIBATI("KI", "en"),
    COMOROS("KM", "ar"),
    SAINT_KITTS_AND_NEVIS("KN", "en"),
    NORTH_KOREA("KP", "ko"),
    SOUTH_KOREA("KR", "ko"),
    KUWAIT("KW", "ar"),
    CAYMAN_ISLANDS("KY", "en"),
    KAZAKHSTAN("KZ", "kk"),
    LAOS("LA", "lo"),
    LEBANON("LB", "ar"),
    SAINT_LUCIA("LC", "en"),
    LIECHTENSTEIN("LI", "de"),
    SRI_LANKA("LK", "ta"),
    LIBERIA("LR", "en"),
    LESOTHO("LS", "en"),
    LITHUANIA("LT", "lt"),
    LUXEMBOURG_GERMAN("LU", "de"),
    LUXEMBOURG_FRENCH("LU", "fr"),
    LATVIA("LV", "lv"),
    LIBYA("LY", "ar"),
    MOROCCO("MA", "ar"),
    MONACO("MC", "fr"),
    MOLDOVA("MD", "mo"),
    MONTENEGRO("ME", "sr"),
    SAINT_MARTIN("MF", "fr"),
    MADAGASCAR("MG", "mg"),
    MARSHALL_ISLANDS("MH", "en"),
    MACEDONIA("MK", "mk"),
    MALI("ML", "fr"),
    MYANMAR("MM", "my"),
    MONGOLIA("MN", "mn"),
    MACAO("MO", "zh"),
    NORTHERN_MARIANA_ISLANDS("MP", "en"),
    MARTINIQUE("MQ", "fr"),
    MAURITANIA("MR", "ar"),
    MONTSERRAT("MS", "en"),
    MALTA("MT", "mt"),
    MAURITIUS("MU", "fr"),
    MALDIVES("MV", "en"),
    MALAWI("MW", "en"),
    MEXICO("MX", "es"),
    MALAYSIA("MY", "ms"),
    MOZAMBIQUE("MZ", "pt"),
    NAMIBIA("NA", "en"),
    NEW_CALEDONIA("NC", "fr"),
    NIGER("NE", "fr"),
    NORFOLK_ISLAND("NF", "en"),
    NIGERIA("NG", "en"),
    NICARAGUA("NI", "es"),
    NETHERLANDS("NL", "nl"),
    NORWAY("NO", "no"),
    NEPAL("NP", "ne"),
    NAURU("NR", "en"),
    NIUE("NU", "en"),
    NEW_ZEALAND("NZ", "en"),
    OMAN("OM", "ar"),
    PANAMA("PA", "es"),
    PERU("PE", "es"),
    FRENCH_POLYNESIA("PF", "fr"),
    PAPUA_NEW_GUINEA("PG", "en"),
    PHILIPPINES("PH", "en"),
    PAKISTAN("PK", "pa"),
    POLAND("PL", "pl"),
    SAINT_PIERRE_AND_MIQUELON("PM", "fr"),
    PITCAIRN("PN", "en"),
    PUERTO_RICO("PR", "es"),
    PALESTINE("PS", "ar"),
    PORTUGAL("PT", "pt"),
    PALAU("PW", "en"),
    PARAGUAY("PY", "es"),
    QATAR("QA", "ar"),
    REUNION("RE", "fr"),
    ROMANIA("RO", "ro"),
    SERBIA("RS", "sr"),
    RUSSIA("RU", "ru"),
    RWANDA("RW", "rw"),
    SAUDI_ARABIA("SA", "ar"),
    SOLOMON_ISLANDS("SB", "en"),
    SEYCHELLES("SC", "fr"),
    SUDAN("SD", "ar"),
    SWEDEN("SE", "sv"),
    SINGAPORE("SG", "zh"),
    SAINT_HELEN("SH", "en"),
    SLOVENIA("SI", "si"),
    SVALBARD_AND_SAN_MAYEN("SJ", "nb"),
    SLOVAKIA("SK", "sk"),
    SIERRA_LEONE("SL", "en"),
    SAN_MARINO("SM", "it"),
    SENEGAL("SN", "fr"),
    SOMALIA("SO", "ar"),
    SURINAME("SR", "nl"),
    SOUTH_SUDAN("SS", "ar"),
    SAO_TOME_PRINCIPE("ST", "pt"),
    EL_SALVADOR("SV", "es"),
    SINT_MAARTEN("SX", "nl"),
    SYRIA("SY", "ar"),
    ESWATINI("SZ", "en"),
    TURKS_AND_CAICOS_ISLANDS("TC", "en"),
    CHAD("TD", "fr"),
    FRENCH_SOUTHERN_TERRITORIES("TF", "fr"),
    TOGO("TG", "fr"),
    THAILAND("TH", "th"),
    TAJIKISTAN("TJ", "tg"),
    TOKELAU("TK", "en"),
    EAST_TIMOR("TL", "pt"),
    TURKMENISTAN("TM", "tk"),
    TUNISIA("TN", "ar"),
    TONGA("TO", "to"),
    TURKEY("TR", "tr"),
    TRINIDAD_TOBAGO("TT", "en"),
    TUVALU("TV", "en"),
    TAIWAN("TW", "zh"),
    TANZANIA("TZ", "sw"),
    UKRAINE("UA", "uk"),
    UGANDA("UG", "sw"),
    US_MINOR_OUTLYING_ISLANDS("UM", "en"),
    USA("US", "en"),
    URUGUAY("UY", "es"),
    UZBEKISTAN("UZ", "uz"),
    VATICAN_CITY("VA", "it"),
    SAINT_VINCENT_AND_GRENADINES("VC", "en"),
    VENEZUELA("VE", "es"),
    BRITISH_VIRGIN_ISLANDS("VG", "en"),
    US_VIRGIN_ISLANDS("VI", "en"),
    VIETNAM("VN", "vi"),
    VANUATU("VU", "fr"),
    WALLIS_AND_FUTUNA("WF", "fr"),
    SAMOA("WS", "en"),
    YEMEN("YE", "ar"),
    MAYOTTE("YT", "fr"),
    SOUTH_AFRICA("ZA", "en"),
    ZAMBIA("ZM", "en"),
    ZIMBABWE("ZW", "en");

    private static List<String> alternateNameList = List.of( //
            "by:belarus;bielorussie",
            "cd:congo-kinshasa;republique democratique du congo;democratic republic of the congo;zaïre",
            "cg:congo-brazzaville;republique du congo;republic of the congo",
            "ci:cote d'ivoire;ivory coast",
            "cv:cabo verde;cape verde;cap vert",
            "cz:tchequie;republique tcheque;czechia;czech republic",
            "eh:western sahara;sahara ocidental;sahrawi arab democratic republic",
            "fm:micronesie;micronesia;etats federes de micronesie",
            "gb:great britain;united kingdom;grande-bretagne;royaume-uni",
            "iq:irak;iraq",
            "kp:coree du nord;republique populaire democratique de coree;democratic people's republic of korea",
            "kr:coree du sud;republique de coree;republic of korea",
            "kg:kirghizie;kirghizstan;kirghizistan;kyrgyzstan;kyrgyz republic",
            "md:moldavie;moldova",
            "mk:macedoine;macedonia;north macedonia;republic of north macedonia",
            "mm:birmanie;myanmar;burma",
            "pr:puerto rico;porto rico",
            "sr:suriname;surinam",
            "sz:swaziland;eswatini",
            "tl:timor-leste;east timor;timor oriental",
            "tr:turkey;türkiye;turquie",
            "va:vatican;saint siege;holy see",
            "vn:vietnam;viet nam"
    );

    private static Map<String, Locale> localeByCountryCode = new HashMap<>();
    private static Map<String, String> alternateNameMap;
    private final String code;
    private final String mainLanguage;
    private final List<Locale> locales;
    private final List<Character.UnicodeScript> scripts;

    Country(String code) {
        this(code, null);
    }

    Country(String code, String mainLanguage) {
        this.code = code;
        this.mainLanguage = mainLanguage;
        this.locales = localesOf(code);
        this.scripts = findScripts(locales);
    }

    public static Locale forCountryTag(String countryCode) {
        Country country = Arrays.stream(values())
                .filter(c -> countryCode.equals(c.getCode()))
                .findFirst()
                .orElse(null);

        return (country == null) ? null : country.toLocale();
    }

    public static Locale forCountryName(String displayName, Locale display) {
        displayName = normalize(displayName).toLowerCase();
        Locale foundCountry = forAlternateCountryName(displayName);

        if (foundCountry == null) {
            String[] countries = Locale.getISOCountries();

            for (String country : countries) {
                Locale locale = forCountryTag(country);

                if (locale != null) {
                    String countryName = normalize(locale.getDisplayCountry(display)).toLowerCase();

                    if (displayName.equals(countryName)) {
                        foundCountry = locale;
                        break;
                    }
                } else {
                    //System.err.println("not found : " + country);
                }
            }
        }

        return foundCountry;
    }

    private static String normalize(String name) {
        name = name.replace('\'', '’');
        name = name.replace('-', ' ');
        name = StringUtil.stripAccents(name);
        return name;
    }

    private static Locale forAlternateCountryName(String name) {
        Locale foundLocale = null;

        if (alternateNameMap == null) {
            alternateNameMap = new HashMap<>();
            for (String item : alternateNameList) {
                String[] values = item.split(":");
                alternateNameMap.put(values[0], values[1]);
            }
        }
        for (String country : alternateNameMap.keySet()) {
            List<String> names = Arrays.asList(alternateNameMap.get(country).split(";"));

            if (names.contains(name)) {
                foundLocale = forCountryTag(country);
                break;
            }
        }

        return foundLocale;
    }


    public String getCode() {
        return code;
    }

    public Locale toLocale() {
        Locale locale;

        if (mainLanguage == null) {
            locale = Arrays.stream(Locale.getAvailableLocales())
                    .filter(l -> l.getCountry().equals(code))
                    .findFirst()
                    .orElse(null);
        } else {
            String languageTag = this.mainLanguage + "-" + this.code;
            locale = Locale.forLanguageTag(languageTag);
        }

        return locale;
    }

    public List<Locale> localesOf() {
        return locales;
    }

    public static List<Locale> localesOf(String country) {
        List<Locale> locales = new ArrayList<>();
        List<Locale> allLocales = List.of(Locale.getAvailableLocales());
        locales.addAll(allLocales.stream()
                .filter(l -> country.equals(l.getCountry()))
                .toList());
        return locales;
    }

    public List<Language> langagesOf() {
        List<Language> languages = new ArrayList<>();
        List<Locale> locales = localesOf();

        for (Locale locale : locales) {
            String languageCode = locale.getLanguage();
            Language language = Language.ofCode(languageCode);

            if ((language != null) && ! languages.contains(language)) {
                languages.add(language);
            }
        }

        return languages;
    }

    public List<Character.UnicodeScript> getScripts() {
        return scripts;
    }

    public Currency getCurrency() {
        Currency foundCurrency = Currency.getInstance("USD"), currency = null;
        List<Locale> locales = localesOf();

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


}
