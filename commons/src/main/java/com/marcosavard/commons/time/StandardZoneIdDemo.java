package com.marcosavard.commons.time;

import com.marcosavard.commons.debug.Console;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.util.Locale;

public class StandardZoneIdDemo {

    public static void main(String[] args) {
        StandardZoneId[] values = StandardZoneId.values();
        Locale display = Locale.FRENCH;

        for (StandardZoneId value : values) {
            ZoneId zoneId = value.getZoneId();
            String code = zoneId.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            String displayName = zoneId.getDisplayName(TextStyle.FULL, display);
            ZoneOffset offset = zoneId.getRules().getOffset(Instant.now());
            Console.println("{0} : [{1}] {2} {3}", zoneId, offset, code, displayName);
        }
    }
}
