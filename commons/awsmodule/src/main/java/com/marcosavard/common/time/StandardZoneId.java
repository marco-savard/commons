package com.marcosavard.common.time;

//StandardZoneId is to ZoneId what java.nio.charset.StandardCharsets is to java.nio.charset.Charset
//  Charset charset = StandardCharsets.UTF_8;
//  ZodeId etc = StandardZoneId.ETC;

import java.time.ZoneId;

public enum StandardZoneId {
    PACIFIC_NIUE("Pacific/Niue"),
    PACIFIC_HONOLULU("Pacific/Honolulu"),
    AMERICA_ADAK("America/Adak"),
    AMERICA_ANCHORAGE("America/Anchorage"),
    AMERICA_LOS_ANGELES("America/Los_Angeles"),
    AMERICA_DENVER("America/Denver"),
    AMERICA_CHICAGO("America/Chicago"),
    AMERICA_NEW_YORK("America/New_York"),
    AMERICA_PUERTO_RICO("America/Puerto_Rico"),
    AMERICA_SAO_PAULO("America/Sao_Paulo"),
    AMERICA_HALIFAX("America/Halifax"),
    AMERICA_ST_JOHNS("America/St_Johns"),
    AMERICA_ST_MIQUELON("America/Miquelon"),
    ATLANTIC_AZORES("Atlantic/Azores"),
    EUROPE_LONDON("Europe/London"),
    EUROPE_PARIS("Europe/Paris"),
    AFRICA_CAIRO("Africa/Cairo"),
    AFRICA_HARARE("Africa/Harare"),
    AFRICA_ADDIS_ABABA("Africa/Addis_Ababa"),
    ASIA_YEREVAN("Asia/Yerevan"),
    ASIA_KARACHI("Asia/Karachi"),
    ASIA_KOLKATA("Asia/Kolkata"),
    ASIA_DHAKA("Asia/Dhaka"),
    ASIA_HO_CHI_MINH("Asia/Ho_Chi_Minh"),
    ASIA_SHANGHAI("Asia/Shanghai"),
    ASIA_TOKYO("Asia/Tokyo"),
    ASIA_VLADIVOSTOK("Asia/Vladivostok"),
    AUSTRALIA_DARWIN("Australia/Darwin"),
    AUSTRALIA_ADELAIDE("Australia/Adelaide"),
    AUSTRALIA_SYDNEY("Australia/Sydney"),
    PACIFIC_GUADALCANAL("Pacific/Guadalcanal"),
    PACIFIC_FIJI("Pacific/Fiji"),
    PACIFIC_AUCKLAND("Pacific/Auckland"),
    PACIFIC_APIA("Pacific/Apia");

    private ZoneId zoneId;

    StandardZoneId(String timeZone) {
        zoneId = ZoneId.of(timeZone);
    }

    public ZoneId getZoneId() {
        return zoneId;
    }
}
