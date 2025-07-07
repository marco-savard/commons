package com.marcosavard.common.time;

import com.marcosavard.common.debug.Console;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.util.Locale;

public class StandardZoneIdDemo {

    public static void main(String[] args) {
        Locale display = Locale.FRENCH;
        demoCharset(display);
        demoZoneId(display);
    }

    private static void demoCharset(Locale display) {
        Charset charset = Charset.forName("ISO_8859_1");
        System.out.println(charset.displayName(display));

        charset = StandardCharsets.ISO_8859_1;
        System.out.println(charset.displayName(display));
        System.out.println();
    }

    public static void demoZoneId(Locale display) {
        //Standard Java
        ZoneId zoneId = ZoneId.systemDefault();
        printZoneOffset(zoneId, display);

        zoneId = ZoneId.of("America/New_York");
        printZoneOffset(zoneId, display);

        //Extended Java
        zoneId = StandardZoneId.AMERICA_NEW_YORK.getZoneId();
        printZoneOffset(zoneId, display);
        System.out.println();

        StandardZoneId[] values = StandardZoneId.values();

        for (StandardZoneId value : values) {
            printZoneOffset(value.getZoneId(), display);
        }
    }

    private static void printZoneOffset(ZoneId zoneId, Locale display) {
        ZoneOffset offset = zoneId.getRules().getOffset(Instant.now());
        String code = zoneId.getDisplayName(TextStyle.SHORT, display);
        String displayName = zoneId.getDisplayName(TextStyle.FULL, display);
        Console.println("{0} : [{1}] {2} {3}", zoneId.getId(), offset, code, displayName);
    }


}
