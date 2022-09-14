package com.marcosavard.commons.geog;

import java.util.*;

public enum Continent {
    AFRICA,
    AMERICA,
    ANTARCTICA,
    ASIA,
    AUSTRALIA,
    EUROPE;

    private static Map<Continent, List<TimeZone>> timezonesByContinent;

    public TimeZone[] getTimeZones() {
        init();
        List<TimeZone> timeZones = timezonesByContinent.get(this);
        return timeZones.toArray(new TimeZone[0]);
    }

    private void init() {
        if (timezonesByContinent == null) {
            timezonesByContinent = new HashMap<>();
            String[] ids = TimeZone.getAvailableIDs();

            for (String id : ids) {
                initTimezone(id);
            }
        }
    }

    private void initTimezone(String id) {
        int idx = id.indexOf('/');
        String prefix = (idx < 1) ? "" : id.substring(0, idx);
        Continent foundContinent = Arrays.stream(Continent.values())
                .filter(c -> c.name().equalsIgnoreCase(prefix))
                .findFirst()
                .orElse(null);

        if (foundContinent != null) {
            if (! timezonesByContinent.containsKey(foundContinent)) {
                timezonesByContinent.put(foundContinent, new ArrayList<>());
            }

            List<TimeZone> timeZones = timezonesByContinent.get(foundContinent);
            TimeZone timezone = TimeZone.getTimeZone(id);
            timeZones.add(timezone);
        }
    }
}
