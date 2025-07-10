package com.marcosavard.common.time.temporal;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static java.util.Map.entry;

public class ChronoInfo {
    private static final Map<ChronoUnit, ChronoInfo> chronoInfoByUnit = new HashMap<>();
    private static final Map<ChronoUnit, ChronoField> chronoFieldByUnit = Map.ofEntries(
            entry(ChronoUnit.SECONDS, ChronoField.SECOND_OF_MINUTE),
            entry(ChronoUnit.MINUTES, ChronoField.MINUTE_OF_HOUR),
            entry(ChronoUnit.HOURS, ChronoField.HOUR_OF_DAY),
            entry(ChronoUnit.YEARS, ChronoField.YEAR),
            entry(ChronoUnit.ERAS, ChronoField.ERA)
    );

    private final ChronoUnit chronoUnit;

    public static ChronoInfo of(ChronoUnit chronoUnit) {
        ChronoInfo chronoInfo = chronoInfoByUnit.get(chronoUnit);

        if (chronoInfo == null) {
            chronoInfo = new ChronoInfo(chronoUnit);
            chronoInfoByUnit.put(chronoUnit, chronoInfo);
        }

        return chronoInfo;
    }

    private ChronoInfo(ChronoUnit chronoUnit) {
        this.chronoUnit = chronoUnit;
    }

    public String getDisplayName(Locale display) {
        ChronoField field = chronoFieldByUnit.get(chronoUnit);
        String displayName = (field != null) ? field.getDisplayName(display) : getDisplayNameFromResource(display);
        return displayName;
    }

    private String getDisplayNameFromResource(Locale display) {
        Class claz = ChronoInfo.class;
        String basename = claz.getName().replace('.', '/');
        ResourceBundle bundle = ResourceBundle.getBundle(basename, display);
        String name = chronoUnit.name().toLowerCase();
        String displayName = bundle.containsKey(name) ? bundle.getString(name) : name;
        return displayName;
    }

}
