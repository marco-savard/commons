package com.marcosavard.commons.ling;

public class LanguageFamily {
    private static final String[] WesternRomance = new String[] {"an", "ca", "es", "fr", "gl", "oc", "pt", "rm", "wa"};
    private static final String[] CentralRomance = new String[] {"co", "it", "sc"};
    private static final String[] EasternRomance = new String[] {"mo", "ro"};
    private static final String[] WesternGermanic = new String[] {"de", "en", "fy", "lb", "li", "nl" };
    private static final String[] Scandinavian = new String[] {"da", "fo", "is", "nb", "nn", "no", "sv"};
    private static final String[] SouthSlavic = new String[] {"bs", "hr", "sl"};
    private static final String[] WestSlavic = new String[] {"cz", "pl", "sk"};
    private static final String[] EastSlavic = new String[] {"ru", "ua"};
    private static final String[] Celtic = new String[] {"br", "cy", "ga", "gd", "gv", "kw" };
    private static final String[] Baltic = new String[] {"lt", "lv" };
    private static final Object[] Romance = new Object[] {WesternRomance, CentralRomance, EasternRomance};
    private static final Object[] Germanic = new Object[] {WesternGermanic, Scandinavian};
    private static final Object[] Slavic = new Object[] {SouthSlavic, WestSlavic, EastSlavic};

    //the tree root
    public static final Object[] IndoEuropean = new Object[] {Celtic, Romance, Germanic, Baltic, Slavic};

}
