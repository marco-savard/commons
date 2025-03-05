package com.marcosavard.commons.astro.planet;

import java.time.DayOfWeek;
import java.util.Locale;
import java.util.ResourceBundle;

public enum AstroObject {
    SUN(Category.STAR, 0x1f31e),
    MOON(Category.SATELLITE, 0x1f31d),
    MERCURY(Category.PLANET, 0x263f),
    VENUS(Category.PLANET, 0x2640),
    EARTH(Category.PLANET, 0x2641),
    MARS(Category.PLANET, 0x2642),
    JUPITER(Category.PLANET, 0x2643),
    SATURN(Category.PLANET, 0x2644),
    URANUS(Category.PLANET, 0x2645),
    NEPTUNE(Category.PLANET, 0x2646),
    PLUTO(Category.TRANS_NEPTUNIAN, 0x2647),
    CERES(Category.ASTEROID, 0x26b3),
    PALLAS(Category.ASTEROID, 0x26b4),
    JUNO(Category.ASTEROID, 0x26b5),
    VESTA(Category.ASTEROID, 0x26b6),
    CHIRON(Category.TRANS_NEPTUNIAN, 0x26b7),
    ERIS(Category.TRANS_NEPTUNIAN, 0x2bf2),
    SEDNA(Category.TRANS_NEPTUNIAN, 0x2bf2);

    public static AstroObject ofDayOfWeek(DayOfWeek dayOfWeek) {
        AstroObject displayName = switch (dayOfWeek) {
            case MONDAY -> MOON;
            case TUESDAY -> MARS;
            case WEDNESDAY -> MERCURY;
            case THURSDAY -> JUPITER;
            case FRIDAY -> VENUS;
            case SATURDAY -> SATURN;
            case SUNDAY -> SUN;
        };

        return displayName;
    }

    public enum Category {
        STAR,
        PLANET,
        ASTEROID,
        TRANS_NEPTUNIAN,
        SATELLITE
    };

    private Category category;
    private int symbol;

    AstroObject(Category category, int symbol) {
        this.category = category;
        this.symbol = symbol;
    }

    public Category getCategory() {
        return category;
    }
    public String getSymbol() {
        return Character.toString(symbol);
    }

    public String getDisplayName(Locale display) {
        Class claz = AstroObject.class;
        String basename = claz.getName().replace('.', '/');
        ResourceBundle bundle = ResourceBundle.getBundle(basename, display);
        String displayName = bundle.getString(this.name());
        return displayName;
    }
}
