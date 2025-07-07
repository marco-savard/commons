package com.marcosavard.common.geog;

import com.marcosavard.common.geog.io.CountryCapitalReader;
import com.marcosavard.common.geog.io.DevisesNationalesReader;
import com.marcosavard.common.geog.io.NationalHolidaysReader;
import com.marcosavard.common.geog.io.TimezoneByCountryReader;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

//TODO  public Locale[] getOfficialLanguages()
public class CountryInfo {
    private static Map<Country, CountryInfo> infoByCountry = new HashMap<>();
    private static TimezoneByCountryReader timezoneByCountryReader = new TimezoneByCountryReader();
    private static CountryCapitalReader countryCapitalReader = new CountryCapitalReader();
    private static DevisesNationalesReader devisesNationalesReader = new DevisesNationalesReader();
    private static NationalHolidaysReader nationalHolidaysReader = new NationalHolidaysReader();
    private final String code;

    public static CountryInfo of(Country country) {
        CountryInfo info = infoByCountry.get(country);

        if (info == null) {
            info = new CountryInfo(country.getCode());
            infoByCountry.put(country, info);
        }

        return info;
    }

    public CountryInfo(String code) {
        this.code = code;
    }

    public String getDisplayName(Locale display, TextStyle textStyle) {
        return countryCapitalReader.getDisplayCountry(code, display, textStyle);
    }

    public List<TimeZone> getTimeZones() {
        return timezoneByCountryReader.readTimezones(code);
    }

    //TODO get continent/ocean
    //TODO former states
    //TODO dependencies

    public List<Capital> getCapitalCities() {
        return countryCapitalReader.getCapitalCities(code);
    }

    public List<String> getNationalMottos() {
        return devisesNationalesReader.getMottos(Country.forCountryTag(code));
    }

    public LocalDate getNationalHolidayDate(int year) {
        NationalHolidaysReader.NationalHoliday holiday = nationalHolidaysReader.read(code);
        if (holiday != null) {
            Month month = holiday.getMonth();
            int dayOfMonth = holiday.getDayOfMonth();
            return LocalDate.of(year, month, dayOfMonth);
        } else {
            return null;
        }
    }

    public static record Capital(String name, GeoLocation location) {}

}
